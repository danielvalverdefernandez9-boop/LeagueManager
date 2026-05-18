package org.example.leaguemanager.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import leaguemanager.DAO.EquipoDAO;
import leaguemanager.DAO.ParticipaDAO;
import leaguemanager.model.Equipo;
import leaguemanager.utils.Utils;
import java.time.LocalDate;

public class CrearEquipoController {

    @FXML private TextField nombre;
    @FXML private TextField ciudad;
    @FXML private TextField estadio;
    @FXML private DatePicker fecha_fundacion;

    private String nombreCompeticion;
    private final EquipoDAO equipoDAO = new EquipoDAO();
    private final ParticipaDAO participaDAO = new ParticipaDAO();

    /**
     * Recibe el nombre de la competición desde la pantalla anterior.
     * Es vital que este nombre coincida EXACTAMENTE con el de la BD.
     */
    public void init(String nombreComp) {
        this.nombreCompeticion = nombreComp;
        System.out.println("DEBUG: Controlador Equipo listo para liga: [" + nombreCompeticion + "]");
    }

    @FXML
    private void guardarNuevoEquipo() {
        // Extraemos y limpiamos espacios en blanco
        String nombreEquipo = nombre.getText().trim();
        String ciudadEquipo = ciudad.getText().trim();
        String estadioEquipo = estadio.getText().trim();
        LocalDate fecha = fecha_fundacion.getValue();

        // 1. Validación de campos obligatorios
        if (nombreEquipo.isEmpty() || ciudadEquipo.isEmpty()) {
            Utils.mostrarAlerta("Campos vacíos", "El nombre y la ciudad son obligatorios para crear un equipo.");
            return;
        }

        // 2. Validación de seguridad de la competición
        if (nombreCompeticion == null || nombreCompeticion.isEmpty()) {
            Utils.mostrarAlerta("Error de flujo", "No se ha detectado una competición activa. Por favor, vuelve atrás y guarda la liga.");
            return;
        }

        try {
            Equipo nuevo = new Equipo(nombreEquipo, ciudadEquipo, estadioEquipo, fecha);

            // PASO A: Intentar insertar el equipo en la tabla 'equipo'
            // Esto es necesario porque 'participa' tiene una FK hacia 'equipo'
            boolean equipoInsertado = equipoDAO.insertar(nuevo);

            if (equipoInsertado) {
                // PASO B: Intentar vincular el equipo a la competición en la tabla 'participa'
                boolean vinculado = participaDAO.vincularEquipoACompeticion(nombreEquipo, nombreCompeticion);

                if (vinculado) {
                    Utils.mostrarMensaje("Registro Exitoso",
                            "¡Hecho! El equipo '" + nombreEquipo + "' ha sido creado y asignado a " + nombreCompeticion + ".");
                    limpiarCampos();
                } else {
                    // Si el equipo se creó pero esto falla, es por la FK de la competición (image_929481.png)
                    Utils.mostrarAlerta("Error de Vinculación",
                            "El equipo se guardó en el sistema, pero no se pudo unir a '" + nombreCompeticion + "'.\n" +
                                    "Asegúrate de que la competición existe realmente en la base de datos.");
                }
            } else {
                // Si equipoDAO.insertar devuelve false, suele ser por clave primaria duplicada
                Utils.mostrarAlerta("Equipo Duplicado", "Ya existe un equipo registrado con el nombre '" + nombreEquipo + "'.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            Utils.mostrarAlerta("Error de Sistema", "Ocurrió un error inesperado al acceder a la base de datos.");
        }
    }

    /**
     * Limpia el formulario y devuelve el foco al nombre para agilizar la carga de datos
     */
    private void limpiarCampos() {
        nombre.clear();
        ciudad.clear();
        estadio.clear();
        fecha_fundacion.setValue(null);
        nombre.requestFocus();
    }

    @FXML
    private void cancelar() {
        limpiarCampos();
        System.out.println("Formulario reiniciado por el usuario.");
    }

    @FXML
    private void volver(ActionEvent event) {
        Stage stage = (Stage) nombre.getScene().getWindow();
        stage.close();
    }
}