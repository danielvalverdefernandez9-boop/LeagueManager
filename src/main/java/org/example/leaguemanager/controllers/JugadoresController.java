package org.example.leaguemanager.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import leaguemanager.DAO.JugadorDAO;
import leaguemanager.model.Competicion;
import leaguemanager.model.Jugador;
import leaguemanager.utils.Utils;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class JugadoresController {

    @FXML private TableView<Jugador> tablaJugadores;
    @FXML private TableColumn<Jugador, Integer> colDorsal;
    @FXML private TableColumn<Jugador, String> colNombre;
    @FXML private TableColumn<Jugador, String> colPosicion;
    @FXML private Label NombreEquipo;

    private String nombreEquipo;
    private Competicion competicionActual;
    private Scene escenaClasificacion;
    private final JugadorDAO jugadorDAO = new JugadorDAO();

    /**
     * Prepara la pantalla al cargar. Guarda el equipo, la liga y la ventana de la clasificación
     * en memoria. También pone el nombre del club en la parte de arriba, configura qué variable
     * va en cada columna de la tabla y manda a rellenarla con los futbolistas.
     *
     * @param nombreEquipo El nombre del club del que estamos viendo la plantilla.
     * @param competicionActual Objeto con la información de la liga activa.
     * @param escenaClasificacion La ventana de la clasificación para no perder el hilo al regresar.
     */
    public void init(String nombreEquipo, Competicion competicionActual, Scene escenaClasificacion) {
        this.nombreEquipo = nombreEquipo;
        this.competicionActual = competicionActual;
        this.escenaClasificacion = escenaClasificacion;

        if (NombreEquipo != null && nombreEquipo != null) {
            NombreEquipo.setText("EQUIPO: " + nombreEquipo.toUpperCase());
        }

        colDorsal.setCellValueFactory(new PropertyValueFactory<>("dorsal"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colPosicion.setCellValueFactory(new PropertyValueFactory<>("posicion"));

        cargarDatos();
    }

    /**
     * Conecta con la base de datos a través del DAO para buscar todos los jugadores
     * que pertenecen a este equipo en concreto y los mete en la tabla.
     */
    private void cargarDatos() {
        List<Jugador> lista = jugadorDAO.buscarPorEquipo(nombreEquipo);
        tablaJugadores.setItems(FXCollections.observableArrayList(lista));
    }

    /**
     * Se activa al pulsar el botón de añadir. Abre el formulario FXML para meter un nuevo
     * jugador y le pasa los datos del club y las escenas para que luego pueda volver
     * hacia atrás sin romper la navegación.
     *
     * @param event El clic en el botón de nuevo jugador.
     */
    @FXML
    private void nuevoJugador(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/leaguemanager/añadir-jugadores.fxml"));
            Parent root = loader.load();

            AnadirJugadoresController cont = loader.getController();
            cont.init(this.nombreEquipo, this.competicionActual, this.escenaClasificacion);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Inscribir Jugador - " + nombreEquipo);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            Utils.mostrarAlerta("Error", "No se pudo abrir el formulario de inscripción.");
        }
    }

    /**
     * Se activa al pulsar el botón de eliminar. Comprueba que hayamos elegido a alguien en
     * la tabla, pide confirmación con un cuadro de diálogo y, si aceptamos, borra al jugador
     * de la base de datos usando su DNI y actualiza la lista en pantalla.
     *
     * @param event El clic en el botón de eliminar seleccionado.
     */
    @FXML
    private void eliminarSeleccionado(ActionEvent event) {
        Jugador seleccionado = tablaJugadores.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            Utils.mostrarAlerta("Atención", "Selecciona un jugador de la tabla para eliminarlo.");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar baja");
        confirmacion.setHeaderText("¿Seguro que quieres eliminar al jugador?");
        confirmacion.setContentText("Se eliminará a: " + seleccionado.getNombre() + " (Dorsal " + seleccionado.getDorsal() + ")");

        Optional<ButtonType> resultado = confirmacion.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            if (jugadorDAO.eliminar(seleccionado.getDni())) {
                Utils.mostrarMensaje("Éxito", "Jugador eliminado correctamente.");
                cargarDatos();
            } else {
                Utils.mostrarAlerta("Error", "No se pudo eliminar el registro de la base de datos.");
            }
        }
    }

    /**
     * Nos saca de la lista de jugadores y nos devuelve a la ficha del equipo, volviendo
     * a pasarle los datos del club, de la liga y de la clasificación para que todo siga conectado.
     *
     * @param event El clic en el botón de volver.
     */
    @FXML
    private void volver(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/leaguemanager/ficha-equipo.fxml"));
            Parent root = loader.load();

            FichaEquipoController controller = loader.getController();
            controller.init(this.nombreEquipo, this.competicionActual, this.escenaClasificacion);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Ficha del Equipo");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Utils.mostrarAlerta("Error", "No se pudo regresar a la ficha del equipo.");
        }
    }
}