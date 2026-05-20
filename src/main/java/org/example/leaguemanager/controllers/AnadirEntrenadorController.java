package org.example.leaguemanager.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import leaguemanager.DAO.EntrenadorDAO;
import leaguemanager.model.Competicion;
import leaguemanager.model.Entrenador;
import leaguemanager.utils.Utils;
import java.io.IOException;

public class AnadirEntrenadorController {

    @FXML private TextField txtDni;
    @FXML private TextField txtNombre;
    @FXML private TextField txtEdad;

    private String nombreEquipo;
    private Competicion competicionActual;
    private Scene escenaClasificacion;
    private final EntrenadorDAO entrenadorDAO = new EntrenadorDAO();

    /**
     * Recibe los datos de la pantalla anterior para no perder el hilo de qué
     * equipo y qué liga estamos gestionando, además de guardar la escena para poder volver.
     *
     * @param nombreEquipo El nombre del club al que le vamos a meter el entrenador.
     * @param comp Objeto con la información de la liga actual.
     * @param escenaClasificacion La escena de la clasificación para guardarla en memoria.
     */
    public void init(String nombreEquipo, Competicion comp, Scene escenaClasificacion) {
        this.nombreEquipo = nombreEquipo;
        this.competicionActual = comp;
        this.escenaClasificacion = escenaClasificacion;
    }

    /**
     * Se activa al pulsar el botón de guardar. Valida que los campos no estén vacíos,
     * que el DNI sea correcto y que la edad sea un número. Si todo está bien, crea
     * al entrenador en la base de datos y lo vincula con el equipo.
     *
     * @param event El evento del clic en el botón guardar.
     */
    @FXML
    private void guardar(ActionEvent event) {
        try {
            String dni = txtDni.getText().trim();
            String nombre = txtNombre.getText().trim();
            String edadStr = txtEdad.getText().trim();

            if (dni.isEmpty() || nombre.isEmpty() || edadStr.isEmpty()) {
                Utils.mostrarAlerta("Campos vacíos", "Debes completar todos los campos.");
                return;
            }

            if (!Utils.validarDNI(dni)) {
                Utils.mostrarAlerta("DNI Inválido", "El DNI no tiene un formato válido (debe tener 8 números y 1 letra).");
                return;
            }

            int edad = Integer.parseInt(edadStr);
            Entrenador nuevoEntrenador = new Entrenador(dni, nombre, edad);

            if (entrenadorDAO.insertar(nuevoEntrenador)) {
                if (entrenadorDAO.asignarAEquipo(dni, this.nombreEquipo, "25/26")) {
                    Utils.mostrarMensaje("Éxito", "Entrenador añadido y asignado al equipo correctamente.");
                    volver(event);
                } else {
                    Utils.mostrarAlerta("Error", "Se creó el entrenador pero no se pudo vincular al equipo.");
                }
            } else {
                Utils.mostrarAlerta("Error", "No se pudo guardar el entrenador.");
            }

        } catch (NumberFormatException e) {
            Utils.mostrarAlerta("Error", "La edad debe ser un número.");
        }
    }

    /**
     * Nos devuelve a la pantalla anterior de gestión de entrenadores, volviendo a pasarle
     * los datos del equipo y la competición para que no se pierda la información.
     *
     * @param event El evento del clic en el botón de volver.
     */
    @FXML
    private void volver(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/leaguemanager/entrenador.fxml"));
            Parent root = loader.load();

            EntrenadorController controller = loader.getController();
            controller.init(this.nombreEquipo, this.competicionActual, this.escenaClasificacion);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Gestión de Entrenadores - " + this.nombreEquipo);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Utils.mostrarAlerta("Error", "No se pudo regresar al panel de entrenadores.");
        }
    }
}