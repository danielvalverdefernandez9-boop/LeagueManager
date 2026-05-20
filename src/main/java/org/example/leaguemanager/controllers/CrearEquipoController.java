package org.example.leaguemanager.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import leaguemanager.DAO.CompeticionDAO;
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
     * Recibe el nombre de la liga desde la pantalla anterior para saber en qué
     * competición tenemos que meter el equipo que vamos a crear.
     *
     * @param nombreComp El nombre exacto de la competición activa.
     */
    public void init(String nombreComp) {
        this.nombreCompeticion = nombreComp;
    }

    /**
     * Se activa al pulsar el botón de guardar. Recoge los textos, mira que los campos obligatorios
     * no estén vacíos y comprueba que la liga no haya alcanzado el límite máximo de equipos.
     * Si todo es correcto, guarda el equipo en la base de datos y lo une a la liga usando el DAO de 'Participa'.
     */
    @FXML
    private void guardarNuevoEquipo() {
        String nombreEquipo = nombre.getText().trim();
        String ciudadEquipo = ciudad.getText().trim();
        String estadioEquipo = estadio.getText().trim();
        LocalDate fecha = fecha_fundacion.getValue();

        if (nombreEquipo.isEmpty() || ciudadEquipo.isEmpty()) {
            Utils.mostrarAlerta("Campos vacíos", "El nombre y la ciudad son obligatorios para crear un equipo.");
            return;
        }

        if (nombreCompeticion == null || nombreCompeticion.isEmpty()) {
            Utils.mostrarAlerta("Error de flujo", "No se ha detectado una competición activa. Por favor, vuelve atrás y guarda la liga.");
            return;
        }

        leaguemanager.model.Competicion comp = CompeticionDAO.buscarPorNombre(nombreCompeticion);
        if (comp != null) {
            int limiteMaximo = comp.getNumero_equipos();
            int actuales = comp.getEquipos().size();

            if (actuales >= limiteMaximo) {
                Utils.mostrarAlerta("Límite Alcanzado", "No se pueden añadir más equipos. El límite para esta competición es de " + limiteMaximo + " equipos.");
                return;
            }
        }

        try {
            Equipo nuevo = new Equipo(nombreEquipo, ciudadEquipo, estadioEquipo, fecha);
            boolean equipoInsertado = equipoDAO.insertar(nuevo);

            if (equipoInsertado) {
                boolean vinculado = participaDAO.vincularEquipoACompeticion(nombreEquipo, nombreCompeticion);

                if (vinculado) {
                    Utils.mostrarMensaje("Registro Exitoso",
                            "¡Hecho! El equipo '" + nombreEquipo + "' ha sido creado y asignado a " + nombreCompeticion + ".");
                    limpiarCampos();
                } else {
                    Utils.mostrarAlerta("Error de Vinculación",
                            "El equipo se guardó en el sistema, pero no se pudo unir a '" + nombreCompeticion + "'.\n" +
                                    "Asegúrate de que la competición existe realmente en la base de datos.");
                }
            } else {
                Utils.mostrarAlerta("Equipo Duplicado", "Ya existe un equipo registrado con el nombre '" + nombreEquipo + "'.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            Utils.mostrarAlerta("Error de Sistema", "Ocurrió un error inesperado al acceder a la base de datos.");
        }
    }

    /**
     * Vacía todos los cuadros de texto del formulario y vuelve a poner el cursor
     * en el campo del nombre para poder escribir otro equipo rápidamente.
     */
    private void limpiarCampos() {
        nombre.clear();
        ciudad.clear();
        estadio.clear();
        fecha_fundacion.setValue(null);
        nombre.requestFocus();
    }

    /**
     * Se activa con el botón cancelar. Sirve para limpiar todo el formulario por
     * si el usuario se ha equivocado y quiere volver a empezar a escribir.
     */
    @FXML
    private void cancelar() {
        limpiarCampos();
    }

    /**
     * Cierra la ventana actual de añadir equipo para volver a la pantalla anterior.
     *
     * @param event El clic en el botón de salir o volver.
     */
    @FXML
    private void volver(ActionEvent event) {
        Stage stage = (Stage) nombre.getScene().getWindow();
        stage.close();
    }
}