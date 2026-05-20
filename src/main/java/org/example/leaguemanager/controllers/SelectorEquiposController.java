package org.example.leaguemanager.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import leaguemanager.DAO.CompeticionDAO;
import leaguemanager.DAO.EquipoDAO;
import leaguemanager.DAO.ParticipaDAO;
import leaguemanager.model.Equipo;
import leaguemanager.utils.Utils;
import java.util.List;

public class SelectorEquiposController {

    @FXML private TableView<Equipo> tablaEquiposDisponibles;
    @FXML private TableColumn<Equipo, String> colNombre;

    private String nombreCompeticion;
    private final EquipoDAO equipoDAO = new EquipoDAO();
    private final ParticipaDAO participaDAO = new ParticipaDAO();

    /**
     * Se ejecuta al abrir la ventana emergente. Guarda el nombre de la liga activa,
     * enlaza el campo del nombre con la columna de la tabla y activa la selección múltiple
     * para que podamos marcar varios equipos a la vez antes de rellenar la lista.
     *
     * @param nombreComp El nombre de la competición en la que queremos meter los equipos.
     */
    public void initData(String nombreComp) {
        this.nombreCompeticion = nombreComp;
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tablaEquiposDisponibles.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        cargarEquiposDesdeBD();
    }

    /**
     * Llama al DAO de equipos para traerse absolutamente todos los clubes registrados
     * en el sistema y los vuelca en la tabla de selección.
     */
    private void cargarEquiposDesdeBD() {
        List<Equipo> lista = equipoDAO.listarTodos();
        if (lista != null) {
            tablaEquiposDisponibles.setItems(FXCollections.observableArrayList(lista));
        }
    }

    /**
     * Se activa al pulsar el botón de insertar. Coge los equipos seleccionados por el usuario,
     * calcula que la suma de los equipos actuales más los nuevos no pase el límite máximo de la liga
     * y, si todo cuadra, los va vinculando uno a uno en la tabla 'Participa' antes de cerrar la ventana.
     *
     * @param event El clic en el botón de insertar seleccionados.
     */
    @FXML
    private void insertarSeleccionados(ActionEvent event) {
        ObservableList<Equipo> seleccionados = tablaEquiposDisponibles.getSelectionModel().getSelectedItems();

        if (seleccionados.isEmpty()) {
            Utils.mostrarAlerta("Sin selección", "Selecciona al menos un equipo.");
            return;
        }

        leaguemanager.model.Competicion comp = CompeticionDAO.buscarPorNombre(nombreCompeticion);
        if (comp != null) {
            int limiteMaximo = comp.getNumero_equipos();
            int actuales = comp.getEquipos().size();
            int intentandoInsertar = seleccionados.size();

            if (actuales + intentandoInsertar > limiteMaximo) {
                Utils.mostrarAlerta("Límite Alcanzado",
                        "No puedes inscribir estos equipos. La competición ya tiene " + actuales +
                                " equipos de un máximo de " + limiteMaximo + " (intentas meter " + intentandoInsertar + " más).");
                return;
            }
        }

        boolean huboError = false;
        int insertados = 0;

        for (Equipo equipo : seleccionados) {
            boolean exito = participaDAO.vincularEquipoACompeticion(equipo.getNombre(), nombreCompeticion);
            if (exito) {
                insertados++;
            } else {
                huboError = true;
                Utils.mostrarAlerta("Error de Vinculación",
                        "No se pudo añadir el equipo: " + equipo.getNombre() +
                                ". Verifique que la competición '" + nombreCompeticion + "' existe.");
                break;
            }
        }

        if (insertados > 0 && !huboError) {
            Utils.mostrarMensaje("Éxito", "Todos los equipos se han inscrito correctamente.");
            cerrarVentana(event);
        }
    }

    /**
     * Se activa con el botón cancelar. Simplemente cierra la ventana emergente actual
     * sin alterar nada ni guardar ninguna selección.
     *
     * @param event El clic en el botón de cancelar.
     */
    @FXML
    private void cancelar(ActionEvent event) {
        cerrarVentana(event);
    }

    /**
     * Se activa con el botón de volver. Hace la misma función que cancelar, cerrando
     * el diálogo de forma segura para no romper la ventana principal que se quedó esperando por detrás.
     *
     * @param event El clic en el botón de volver.
     */
    @FXML
    private void volver(ActionEvent event) {
        cerrarVentana(event);
    }

    /**
     * Método auxiliar privado que localiza el escenario actual a través del botón
     * que lanzó el evento y efectúa el cierre de la ventana flotante.
     *
     * @param event El evento de acción que solicita el cierre.
     */
    private void cerrarVentana(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}