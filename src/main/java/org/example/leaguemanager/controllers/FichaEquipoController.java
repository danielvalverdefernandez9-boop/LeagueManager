package org.example.leaguemanager.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import leaguemanager.model.Competicion;
import leaguemanager.utils.Utils;
import java.io.IOException;

public class FichaEquipoController {

    @FXML private Label lblNombreEquipo;
    @FXML private ImageView imgEscudo;

    private String equipo;
    private Competicion competicionActual; // Guardamos la liga para no perderla al volver

    /**
     * Inicializa la ficha con el nombre del equipo y la competición activa
     */
    public void init(String nombre, Competicion comp) {
        this.equipo = nombre;
        this.competicionActual = comp;

        if (lblNombreEquipo != null && nombre != null) {
            lblNombreEquipo.setText(nombre.toUpperCase());
        }
        // Aquí podrías cargar el escudo si tuvieras la lógica implementada
    }

    /**
     * Abre la gestión de jugadores del equipo seleccionado
     */
    @FXML
    private void irAJugadores(ActionEvent event) {
        if (this.equipo == null) {
            Utils.mostrarAlerta("Error", "No se ha cargado la información del equipo.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/leaguemanager/gestion-jugadores.fxml"));
            Parent root = loader.load();

            // Pasamos el nombre del equipo al controlador de jugadores
            JugadoresController controller = loader.getController();
            controller.init(this.equipo);

            Stage stage = new Stage();
            stage.setTitle("Gestión de Jugadores - " + this.equipo);
            stage.initModality(Modality.APPLICATION_MODAL); // Ventana emergente
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            Utils.mostrarAlerta("Error", "No se pudo cargar la pantalla de jugadores.");
        }
    }

    /**
     * Abre la gestión del entrenador del equipo seleccionado
     */
    @FXML
    private void irAEntrenador(ActionEvent event) {
        if (this.equipo == null) {
            Utils.mostrarAlerta("Error", "No hay un equipo seleccionado.");
            return;
        }

        // Ejemplo de navegación similar a jugadores
        System.out.println("Navegando a entrenador del equipo: " + this.equipo);
        // Implementar lógica similar a irAJugadores cuando tengas el FXML listo
    }

    /**
     * Regresa a la pantalla de gestión de equipos devolviendo la competición
     */
    @FXML
    private void volver(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/leaguemanager/equipos.fxml"));
            Parent root = loader.load();

            EquiposController controller = loader.getController();

            if (this.competicionActual != null) {
                controller.setCompeticion(this.competicionActual);
            }

            // Cambiamos la escena en la ventana actual
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Gestión de Equipos");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            Utils.mostrarAlerta("Error", "No se pudo regresar a la pantalla de equipos.");
        }
    }
}