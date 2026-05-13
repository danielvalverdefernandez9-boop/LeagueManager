package org.example.leaguemanager.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import leaguemanager.DAO.EquipoDAO;
import leaguemanager.DAO.ParticipaDAO;
import leaguemanager.model.Competicion;
import leaguemanager.model.Equipo;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EquiposController {

    private Competicion competicionActiva;
    private EquipoDAO equipoDAO = new EquipoDAO();
    private ParticipaDAO participaDAO = new ParticipaDAO();

    // Método para recibir la competición de la pantalla anterior
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
    public void setCompeticion(Competicion c) {
        this.competicionActiva = c;
        System.out.println("Gestionando equipos para: " + c.getNombre());
    }

    @FXML
    private void crearEquipo() {
        // Aquí abriríamos una ventanita pequeña para nombre y ciudad del equipo
        System.out.println("Abriendo formulario de creación de equipo nuevo...");
    }

    @FXML
    private void insertarEquipos(ActionEvent event) {
        try {
            // 1. Cargar el FXML de la nueva ventana
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/leaguemanager/selector_equipos.fxml"));
            Parent root = loader.load();

            // 2. Obtener el controlador y pasarle el nombre de la competición activa
            SelectorEquiposController controller = loader.getController();
            controller.initData(this.competicionActiva.getNombre());

            // 3. Crear y configurar el nuevo escenario (Stage)
            Stage stage = new Stage();
            stage.setTitle("Seleccionar Equipos para " + competicionActiva.getNombre());
            stage.setScene(new Scene(root));

            // 4. Hacer que sea una ventana modal (bloquea la principal hasta cerrar esta)
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) event.getSource()).getScene().getWindow());

            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void eliminarEquipos() {
        // Lógica para desvincular un equipo de esta competición
        System.out.println("Función para eliminar equipo de la competición...");
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}