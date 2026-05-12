package org.example.leaguemanager.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import leaguemanager.DAO.CompeticionDAO;
import leaguemanager.model.Competicion;

import java.io.IOException;

public class CrearCompeticionController {

    @FXML
    private TextField nombre;
    @FXML
    private TextField temporada;
    @FXML
    private TextField numero_equipos;

    // Instanciamos el DAO que ya tienes creado
    private CompeticionDAO competicionDAO = new CompeticionDAO();

    @FXML
    private void volverMenu(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/leaguemanager/principal.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo regresar al menú principal.");
            e.printStackTrace();
        }
    }

    @FXML
    private void continuarEquipos(ActionEvent event) {
        try {
            // 1. Validamos que no haya campos vacíos
            if (nombre.getText().isEmpty() || temporada.getText().isEmpty() || numero_equipos.getText().isEmpty()) {
                mostrarAlerta("Campos incompletos", "Por favor, rellena todos los campos.");
                return;
            }

            // 2. Recogemos los datos
            String nombre = this.nombre.getText();
            String temporada = this.temporada.getText();
            int numEquipos = Integer.parseInt(numero_equipos.getText());


            Competicion nuevaComp = new Competicion(nombre, numEquipos, temporada);

            boolean insertado = competicionDAO.insertar(nuevaComp);

            if (insertado) {
                System.out.println("Guardado con éxito: " + nombre);

                // --- AQUÍ IRÁ EL SALTO A LA PANTALLA DE EQUIPOS ---
                // Por ahora, solo confirmamos con una alerta
                Alert confirmacion = new Alert(Alert.AlertType.INFORMATION);
                confirmacion.setTitle("Éxito");
                confirmacion.setHeaderText(null);
                confirmacion.setContentText("Competición '" + nombre + "' guardada. ¡Vamos a añadir los equipos!");
                confirmacion.showAndWait();

            } else {
                mostrarAlerta("Error BD", "No se pudo guardar la competición. Revisa la conexión.");
            }

        } catch (NumberFormatException e) {
            mostrarAlerta("Error de datos", "En 'Nº equipos' debes introducir un número entero.");
        }
    }

    // Método auxiliar para mostrar errores de forma visual
    private void mostrarAlerta(String titulo, String contenido) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}
