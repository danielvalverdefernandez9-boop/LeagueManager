package org.example.leaguemanager.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class PrincipalController {

    @FXML
    private void crearCompeticion(ActionEvent event) {
        try {
            // Cargamos la vista del formulario (Pantalla 2 de tu dibujo)
            // Prueba con esta ruta completa desde la raíz de resources
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/leaguemanager/crearCompeticion.fxml"));
            Parent root = loader.load();

            // Obtenemos la ventana actual
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Creamos la escena y aplicamos Bootstrap si lo deseas
            Scene scene = new Scene(root);
            scene.getStylesheets().add(org.kordamp.bootstrapfx.BootstrapFX.bootstrapFXStylesheet());

            stage.setTitle("Nueva Competición");
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.err.println("Error: No se encuentra el archivo crear-competicion.fxml");
            e.printStackTrace();
        }
    }

    @FXML
    private void cargarCompeticion(ActionEvent event) {
        System.out.println("Abriendo explorador para cargar competición...");
    }
}