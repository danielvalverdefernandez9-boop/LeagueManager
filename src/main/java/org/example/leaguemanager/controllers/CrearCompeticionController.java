package org.example.leaguemanager.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import leaguemanager.DAO.CompeticionDAO;
import leaguemanager.model.Competicion;
import leaguemanager.utils.Utils;

public class CrearCompeticionController {

    @FXML private TextField nombre;
    @FXML private ComboBox<String> comboTemporada;
    @FXML private TextField numero_equipos;

    private CompeticionDAO competicionDAO = new CompeticionDAO();

    /**
     * Este método se ejecuta solo al abrir la pantalla. Sirve para cargar las tres
     * temporadas en el ComboBox y dejar seleccionada la "25/26" por defecto.
     */
    @FXML
    public void initialize() {
        comboTemporada.setItems(FXCollections.observableArrayList(
                "24/25",
                "25/26",
                "26/27"
        ));
        comboTemporada.setValue("25/26");
    }

    /**
     * Nos saca de esta ventana y nos lleva de vuelta al menú principal de la aplicación
     * usando la clase de utilidades.
     *
     * @param event El clic en el botón de volver.
     */
    @FXML
    private void volverMenu(ActionEvent event) {
        Utils.cambiarEscena(event, "/leaguemanager/principal.fxml", "Menú Principal");
    }

    /**
     * Se activa al pulsar en guardar. Comprueba que todo esté relleno y que el número de
     * equipos sea un número. Si pasa los filtros, guarda la nueva liga en la base de datos.
     *
     * @param event El clic en el botón de guardar.
     */
    @FXML
    private void guardar(ActionEvent event) {
        try {
            if (nombre.getText().isEmpty() || comboTemporada.getValue() == null || numero_equipos.getText().isEmpty()) {
                Utils.mostrarAlerta("Campos incompletos", "Por favor, rellena todos los campos.");
                return;
            }

            String nombreComp = nombre.getText();
            String temp = comboTemporada.getValue();
            int numEquipos = Integer.parseInt(numero_equipos.getText());

            Competicion nuevaComp = new Competicion(nombreComp, numEquipos, temp);
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

    /**
     * Hace lo mismo que el botón guardar, pero además de meter la liga en la base de datos,
     * nos pasa automáticamente a la pantalla de añadir equipos vinculando la liga que acabamos de crear.
     *
     * @param event El clic en el botón de continuar a equipos.
     */
    @FXML
    private void continuarEquipos(ActionEvent event) {
        try {
            if (nombre.getText().isEmpty() || comboTemporada.getValue() == null) {
                Utils.mostrarAlerta("Error", "Debes indicar el nombre y la temporada de la competición.");
                return;
            }

            Competicion compTemporal = new Competicion(
                    nombre.getText(),
                    Integer.parseInt(numero_equipos.getText()),
                    comboTemporada.getValue()
            );

            competicionDAO.insertar(compTemporal);
            irAEquipos(event, compTemporal);

        } catch (NumberFormatException e) {
            Utils.mostrarAlerta("Error", "Revisa que el número de equipos sea válido.");
        }
    }

    /**
     * Método auxiliar privado que realiza el cambio de ventana hacia la gestión de equipos
     * e inicializa su controlador pasándole el objeto de la competición activa.
     *
     * @param event El evento que originó el cambio de pantalla.
     * @param comp El objeto de la competición que usará la ventana de equipos.
     */
    private void irAEquipos(ActionEvent event, Competicion comp) {
        FXMLLoader loader = Utils.cambiarEscena(event, "/leaguemanager/equipos.fxml", "Gestión de Equipos");

        if (loader != null) {
            EquiposController controller = loader.getController();
            controller.setCompeticion(comp);
        }
    }
}