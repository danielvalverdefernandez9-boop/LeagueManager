package org.example.leaguemanager.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox; // Cambiado
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import leaguemanager.DAO.JugadorDAO;
import leaguemanager.model.Equipo;
import leaguemanager.model.Jugador;
import leaguemanager.utils.Utils;
import java.io.IOException;

public class AnadirJugadoresController {

    @FXML private TextField txtDni;
    @FXML private TextField txtNombre;
    @FXML private TextField txtEdad;
    @FXML private TextField txtDorsal;

    // 1. Cambiamos TextField por ComboBox
    @FXML private ComboBox<String> comboPosicion;

    private String nombreEquipo;
    private final JugadorDAO jugadorDAO = new JugadorDAO();

    /**
     * Este método se ejecuta automáticamente al cargar el FXML
     */
    @FXML
    public void initialize() {
        // 2. Cargamos las opciones en el desplegable
        comboPosicion.setItems(FXCollections.observableArrayList(
                "Delantero",
                "Centrocampista",
                "Defensa",
                "Portero"
        ));
    }

    public void init(String nombreEquipo) {
        this.nombreEquipo = nombreEquipo;
    }

    @FXML
    private void guardar(ActionEvent event) {
        try {
            String dni = txtDni.getText().trim();
            String nombre = txtNombre.getText().trim();
            String edadStr = txtEdad.getText().trim();
            String dorsalStr = txtDorsal.getText().trim();

            // 3. Obtenemos el valor del ComboBox
            String posicion = comboPosicion.getValue();

            // Validaciones
            if (dni.isEmpty() || nombre.isEmpty() || posicion == null || edadStr.isEmpty() || dorsalStr.isEmpty()) {
                Utils.mostrarAlerta("Campos vacíos", "Debes completar todos los campos, incluida la posición.");
                return;
            }

            int edad = Integer.parseInt(edadStr);
            int dorsal = Integer.parseInt(dorsalStr);

            Equipo equipo = new Equipo(nombreEquipo, null, null, null);
            Jugador nuevoJugador = new Jugador(dni, nombre, edad, posicion, dorsal, equipo);

            if (jugadorDAO.insertar(nuevoJugador)) {
                Utils.mostrarMensaje("Éxito", "Jugador añadido correctamente.");
                volver(event);
            } else {
                Utils.mostrarAlerta("Error", "No se pudo guardar el jugador.");
            }

        } catch (NumberFormatException e) {
            Utils.mostrarAlerta("Error", "Edad y dorsal deben ser números.");
        }
    }

    @FXML
    private void volver(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/leaguemanager/gestion-jugadores.fxml"));
            Parent root = loader.load();

            // Asegúrate de que el nombre del controlador destino sea correcto
            JugadoresController controller = loader.getController();
            controller.init(this.nombreEquipo);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}