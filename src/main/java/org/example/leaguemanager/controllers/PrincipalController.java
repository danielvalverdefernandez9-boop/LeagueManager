package org.example.leaguemanager.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import leaguemanager.DAO.CompeticionDAO;
import leaguemanager.model.Competicion;
import leaguemanager.utils.Utils;
import java.io.IOException;

public class PrincipalController {

    /**
     * Se activa al pulsar el botón de crear una nueva liga. Se encarga de cargar
     * el archivo FXML del formulario de creación, le mete los estilos visuales de
     * Bootstrap y cambia la ventana para que podamos empezar a escribir los datos.
     *
     * @param event El clic en el botón de crear competición.
     */
    @FXML
    private void crearCompeticion(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/leaguemanager/crearCompeticion.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(org.kordamp.bootstrapfx.BootstrapFX.bootstrapFXStylesheet());

            stage.setTitle("Nueva Competición");
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            Utils.mostrarAlerta("Error", "No se pudo abrir la pantalla de creación.");
        }
    }

    /**
     * Se activa al pulsar el botón de cargar. Saca un cuadro de texto emergente para
     * que el usuario escriba el nombre de la liga. Si la encuentra en la base de datos a
     * través del DAO, nos manda directos a la pantalla de la clasificación inicializando
     * su controlador con todos los datos que ha recuperado.
     *
     * @param event El clic en el botón de cargar competición.
     */
    @FXML
    private void cargarCompeticion(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Cargar Competición");
        dialog.setHeaderText("Introduce el nombre exacto de la competición que deseas reanudar:");
        dialog.setContentText("Nombre de la competición:");

        dialog.showAndWait().ifPresent(nombreCompeticion -> {
            if (nombreCompeticion.trim().isEmpty()) {
                Utils.mostrarAlerta("Campo vacío", "Por favor, escribe un nombre válido.");
                return;
            }

            try {
                CompeticionDAO compDAO = new CompeticionDAO();
                Competicion compCargada = compDAO.buscarPorNombre(nombreCompeticion.trim());

                if (compCargada == null) {
                    Utils.mostrarAlerta("No encontrada", "No existe ninguna competición registrada con el nombre: " + nombreCompeticion);
                    return;
                }

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/leaguemanager/clasificacion.fxml"));
                Parent root = loader.load();

                ClasificacionController controller = loader.getController();
                controller.init(compCargada);

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Panel de Control - " + compCargada.getNombre());
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
                Utils.mostrarAlerta("Error", "No se pudo abrir la pantalla de clasificación.");
            }
        });
    }
}