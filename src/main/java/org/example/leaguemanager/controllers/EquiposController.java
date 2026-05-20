package org.example.leaguemanager.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import leaguemanager.DAO.ParticipaDAO;
import leaguemanager.model.Competicion;
import leaguemanager.model.Equipo;
import leaguemanager.utils.Utils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EquiposController {

    @FXML private TableView<Equipo> tablaEquiposDisponibles;
    @FXML private TableColumn<Equipo, String> colNombre;

    private Competicion competicionActiva;
    private final ParticipaDAO participaDAO = new ParticipaDAO();

    /**
     * Recibe la liga seleccionada o creada en la pantalla anterior.
     * Configura la columna de la tabla para que muestre el nombre de los clubes
     * y manda a cargar los datos desde la base de datos.
     *
     * @param c El objeto de la competición activa con la que vamos a trabajar.
     */
    public void setCompeticion(Competicion c) {
        this.competicionActiva = c;
        if (colNombre != null) {
            colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        }
        cargarDatosTabla();
    }

    /**
     * Busca en la base de datos qué equipos están participando en la competición activa
     * y actualiza la tabla de la pantalla para que el usuario vea la lista al momento.
     */
    private void cargarDatosTabla() {
        if (competicionActiva != null && tablaEquiposDisponibles != null) {
            List<Equipo> listaActualizada = participaDAO.obtenerEquiposPorCompeticion(competicionActiva.getNombre());
            tablaEquiposDisponibles.setItems(FXCollections.observableArrayList(listaActualizada));
        }
    }

    /**
     * Método genérico para abrir subventanas flotantes (modales).
     * Dependiendo del número que le pasemos en el 'modo', inicializa el controlador
     * de la ventana emergente que toque (1 para buscar, 2 para crear, 3 para borrar)
     * y se espera a que se cierre para refrescar la tabla principal.
     *
     * @param fxml   La ruta del archivo de vista FXML que queremos abrir.
     * @param titulo El título de texto que se le pondrá arriba a la ventana.
     * @param modo   El número entero que indica qué pantalla estamos gestionando.
     */
    private void abrirVentanaEmergente(String fxml, String titulo, int modo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();

            if (modo == 1) ((SelectorEquiposController) loader.getController()).initData(competicionActiva.getNombre());
            if (modo == 2) ((CrearEquipoController) loader.getController()).init(competicionActiva.getNombre());
            if (modo == 3) ((EliminarEquiposController) loader.getController()).initData(competicionActiva.getNombre());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            cargarDatosTabla();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Abre la ventana flotante del selector de equipos del sistema para meterlos en la liga.
     *
     * @param event El clic en el botón de insertar equipos.
     */
    @FXML
    private void insertarEquipos(ActionEvent event) {
        abrirVentanaEmergente("/leaguemanager/selector_equipos.fxml", "Seleccionar", 1);
    }

    /**
     * Abre la ventana flotante con el formulario para fundar un nuevo club desde cero.
     *
     * @param event El clic en el botón de crear equipo.
     */
    @FXML
    private void crearEquipo(ActionEvent event) {
        abrirVentanaEmergente("/leaguemanager/crear-equipo.fxml", "Nuevo", 2);
    }

    /**
     * Abre la pantalla emergente que sirve para desvincular o retirar equipos de la liga activa.
     *
     * @param event El clic en el botón de quitar equipos.
     */
    @FXML
    private void eliminarEquipos(ActionEvent event) {
        abrirVentanaEmergente("/leaguemanager/eliminar-equipos.fxml", "Eliminar", 3);
    }

    /**
     * Nos saca de la gestión de equipos y nos manda de vuelta directo al menú de inicio de la aplicación.
     *
     * @param event El clic en el botón de regresar al menú.
     */
    @FXML
    private void volverMenu(ActionEvent event) {
        Utils.cambiarEscena(event, "/leaguemanager/principal.fxml", "Menú");
    }

    /**
     * Se activa al pulsar el botón para avanzar. Comprueba que la liga tenga equipos inscritos
     * para no romper el programa y, si todo está bien, le inyecta la lista de equipos a la
     * competición y nos cambia de pantalla hacia el panel de la clasificación de la liga.
     *
     * @param event El clic en el botón de ir a la clasificación.
     */
    @FXML
    private void irAClasificacion(ActionEvent event) {
        if (this.competicionActiva == null) {
            Utils.mostrarAlerta("Error", "No hay ninguna competición activa seleccionada.");
            return;
        }

        List<Equipo> equiposEnTabla = new ArrayList<>(tablaEquiposDisponibles.getItems());

        if (equiposEnTabla.isEmpty()) {
            Utils.mostrarAlerta(
                    "Competición Vacía",
                    "No puedes ver la clasificación porque esta competición aún no tiene equipos inscritos. Inserte equipos antes de continuar."
            );
            return;
        }

        try {
            this.competicionActiva.setEquipos(equiposEnTabla);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/leaguemanager/clasificacion.fxml"));
            Parent root = loader.load();

            ClasificacionController controller = loader.getController();
            controller.init(this.competicionActiva);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Panel de Control - " + this.competicionActiva.getNombre());
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            Utils.mostrarAlerta("Error", "No se pudo abrir la pantalla de clasificación.");
        }
    }
}