package org.example.leaguemanager.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import leaguemanager.DAO.CompeticionDAO;
import leaguemanager.model.Competicion;
import leaguemanager.utils.Utils;

public class CrearCompeticionController {

    @FXML private TextField nombre;
    @FXML private TextField temporada;
    @FXML private TextField numero_equipos;

    private CompeticionDAO competicionDAO = new CompeticionDAO();

    @FXML
    private void volverMenu(ActionEvent event) {
        Utils.cambiarEscena(event, "/leaguemanager/principal.fxml", "Menú Principal");
    }

    @FXML
    private void guardar(ActionEvent event) {
        try {
            // 1. Validación de campos vacíos
            if (nombre.getText().isEmpty() || temporada.getText().isEmpty() || numero_equipos.getText().isEmpty()) {
                Utils.mostrarAlerta("Campos incompletos", "Por favor, rellena todos los campos.");
                return;
            }

            String nombreComp = nombre.getText();
            String temp = temporada.getText();
            int numEquipos = Integer.parseInt(numero_equipos.getText());

            // 2. Crear el objeto
            Competicion nuevaComp = new Competicion(nombreComp, numEquipos, temp);

            // 3. PERSISTENCIA: Guardar realmente en la Base de Datos
            boolean exito = competicionDAO.insertar(nuevaComp);

            if (exito) {
                Utils.mostrarMensaje("Competición Guardada",
                        "La competición '" + nombreComp + "' ha sido registrada con éxito en el sistema.");
            } else {
                Utils.mostrarAlerta("Error al guardar",
                        "No se pudo insertar en la base de datos. Verifica si el nombre ya existe.");
            }

        } catch (NumberFormatException e) {
            Utils.mostrarAlerta("Error de datos", "En 'Nº equipos' debes introducir un número entero.");
        }
    }

    @FXML
    private void continuarEquipos(ActionEvent event) {
        try {
            if (nombre.getText().isEmpty()) {
                Utils.mostrarAlerta("Error", "Debes indicar el nombre de la competición.");
                return;
            }

            // Creamos el objeto con los datos actuales de los campos
            Competicion compTemporal = new Competicion(
                    nombre.getText(),
                    Integer.parseInt(numero_equipos.getText()),
                    temporada.getText()
            );

            // Intentamos asegurar que la competición exista en la BD antes de pasar a la siguiente pantalla
            // Esto evita el error de la Foreign Key que viste en image_929481.png
            competicionDAO.insertar(compTemporal);

            irAEquipos(event, compTemporal);

        } catch (NumberFormatException e) {
            Utils.mostrarAlerta("Error", "Revisa que el número de equipos sea válido.");
        }
    }

    private void irAEquipos(ActionEvent event, Competicion comp) {
        FXMLLoader loader = Utils.cambiarEscena(event, "/leaguemanager/equipos.fxml", "Gestión de Equipos");

        if (loader != null) {
            EquiposController controller = loader.getController();
            controller.setCompeticion(comp);
        }
    }
}