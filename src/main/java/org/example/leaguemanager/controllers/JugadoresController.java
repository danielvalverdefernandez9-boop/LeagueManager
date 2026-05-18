package org.example.leaguemanager.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import leaguemanager.DAO.JugadorDAO;
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
    @FXML private Label lblNombreEquipo;

    private String nombreEquipo;
    private final JugadorDAO jugadorDAO = new JugadorDAO();

    /**
     * Inicializa la pantalla con los datos del equipo seleccionado.
     */
    public void init(String nombreEquipo) {
        this.nombreEquipo = nombreEquipo;
        lblNombreEquipo.setText("EQUIPO: " + nombreEquipo.toUpperCase());

        // Configuración de columnas
        colDorsal.setCellValueFactory(new PropertyValueFactory<>("dorsal"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colPosicion.setCellValueFactory(new PropertyValueFactory<>("posicion"));

        cargarDatos();
    }

    private void cargarDatos() {
        List<Jugador> lista = jugadorDAO.buscarPorEquipo(nombreEquipo);
        tablaJugadores.setItems(FXCollections.observableArrayList(lista));
    }

    @FXML
    private void nuevoJugador(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/leaguemanager/añadir-jugadores.fxml"));
            Parent root = loader.load();

            // Pasamos el contexto al formulario
            AnadirJugadoresController cont = loader.getController();
            cont.init(this.nombreEquipo);

            Stage stage = new Stage();
            stage.setTitle("Inscribir Jugador - " + nombreEquipo);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait(); // Esperar a que se cierre para refrescar

            cargarDatos();

        } catch (IOException e) {
            e.printStackTrace();
            Utils.mostrarAlerta("Error", "No se pudo abrir el formulario de inscripción.");
        }
    }

    @FXML
    private void eliminarSeleccionado(ActionEvent event) {
        Jugador seleccionado = tablaJugadores.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            Utils.mostrarAlerta("Atención", "Selecciona un jugador de la tabla para eliminarlo.");
            return;
        }

        // Ventana de Confirmación
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

    @FXML
    private void volver(ActionEvent event) {
        // Cerramos esta capa para volver a la Ficha de Equipo
        Stage stage = (Stage) tablaJugadores.getScene().getWindow();
        stage.close();
    }
}