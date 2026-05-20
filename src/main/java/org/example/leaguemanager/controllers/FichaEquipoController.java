package org.example.leaguemanager.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import leaguemanager.model.Competicion;
import leaguemanager.utils.Utils;
import java.io.IOException;

public class FichaEquipoController {

    @FXML private Label NombreEquipo;

    private String equipo;
    private Competicion competicionActual;
    private Scene escenaClasificacion;

    /**
     * Se encarga de recibir los datos del equipo seleccionado, la liga activa y la escena
     * de origen de la clasificación. Además, pone el nombre del club arriba en mayúsculas
     * y ajusta su ancho en la interfaz.
     *
     * @param nombre El nombre del equipo cuya ficha estamos abriendo.
     * @param comp Objeto con la información de la liga que se está jugando.
     * @param escenaOrigen La escena de la clasificación para poder volver atrás después.
     */
    public void init(String nombre, Competicion comp, Scene escenaOrigen) {
        this.equipo = nombre;
        this.competicionActual = comp;
        this.escenaClasificacion = escenaOrigen;

        if (nombre != null && NombreEquipo != null) {
            NombreEquipo.setText(nombre.toUpperCase());
            NombreEquipo.setMinWidth(Double.NEGATIVE_INFINITY);
            NombreEquipo.setPrefWidth(Double.MAX_VALUE);
        }
    }

    /**
     * Se activa al pulsar el botón de jugadores. Carga la pantalla de gestión de la plantilla
     * y le pasa el nombre del equipo, la liga y la escena de la clasificación para mantener
     * la cadena de datos sin perder nada por el camino.
     *
     * @param event El clic en el botón para ir a la sección de jugadores.
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

            JugadoresController controller = loader.getController();
            controller.init(this.equipo, this.competicionActual, this.escenaClasificacion);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Gestión de Jugadores - " + this.equipo);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            Utils.mostrarAlerta("Error", "No se pudo cargar la pantalla de jugadores.");
        }
    }

    /**
     * Se activa al pulsar el botón de entrenadores. Funciona igual que el de jugadores:
     * abre el panel del cuerpo técnico inyectándole todas las variables necesarias
     * para que la navegación siga funcionando al volver.
     *
     * @param event El clic en el botón para ir a la sección del entrenador.
     */
    @FXML
    private void irAEntrenador(ActionEvent event) {
        if (this.equipo == null) {
            Utils.mostrarAlerta("Error", "No hay un equipo seleccionado.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/leaguemanager/entrenador.fxml"));
            Parent root = loader.load();

            EntrenadorController controller = loader.getController();
            controller.init(this.equipo, this.competicionActual, this.escenaClasificacion);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Gestión de Entrenadores - " + this.equipo);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            Utils.mostrarAlerta("Error", "No se pudo cargar la pantalla de entrenadores.");
        }
    }

    /**
     * Nos devuelve a la pantalla de la clasificación. Si tenemos la escena guardada en memoria,
     * vuelve a ella directamente para ahorrar recursos; si no, recarga el FXML de la
     * clasificación desde cero inicializando su controlador.
     *
     * @param event El clic en el botón de regresar.
     */
    @FXML
    private void volver(ActionEvent event) {
        try {
            if (this.escenaClasificacion != null) {
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(this.escenaClasificacion);
                stage.setTitle("Panel de Control - " + this.competicionActual.getNombre());
                stage.show();
            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/leaguemanager/clasificacion.fxml"));
                Parent root = loader.load();

                ClasificacionController controller = loader.getController();
                controller.init(this.competicionActual);

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Panel de Control - " + this.competicionActual.getNombre());
                stage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Utils.mostrarAlerta("Error", "No se pudo regresar a la clasificación.");
        }
    }
}