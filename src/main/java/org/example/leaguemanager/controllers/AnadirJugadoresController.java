package org.example.leaguemanager.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import leaguemanager.DAO.JugadorDAO;
import leaguemanager.model.Competicion;
import leaguemanager.model.Equipo;
import leaguemanager.model.Jugador;
import leaguemanager.utils.Utils;
import java.io.IOException;

public class AnadirJugadoresController {

    @FXML private TextField txtDni;
    @FXML private TextField txtNombre;
    @FXML private TextField txtEdad;
    @FXML private TextField txtDorsal;
    @FXML private ComboBox<String> comboPosicion;

    private String nombreEquipo;
    private Competicion competicionActual;
    private Scene escenaClasificacion;
    private final JugadorDAO jugadorDAO = new JugadorDAO();

    /**
     * Este método se ejecuta automáticamente al cargar la pantalla.
     * Sirve para rellenar el ComboBox con las cuatro posiciones fijas de los futbolistas.
     */
    @FXML
    public void initialize() {
        comboPosicion.setItems(FXCollections.observableArrayList(
                "Delantero",
                "Centrocampista",
                "Defensa",
                "Portero"
        ));
    }

    /**
     * Recibe los datos importantes del controlador anterior para saber a qué equipo
     * le estamos metiendo el jugador y qué liga se está jugando, además de guardar la escena.
     *
     * @param nombreEquipo El nombre del club en el que se va a fichar al jugador.
     * @param comp Objeto con la información de la liga activa.
     * @param escenaClasificacion La ventana de la clasificación para poder regresar después.
     */
    public void init(String nombreEquipo, Competicion comp, Scene escenaClasificacion) {
        this.nombreEquipo = nombreEquipo;
        this.competicionActual = comp;
        this.escenaClasificacion = escenaClasificacion;
    }

    /**
     * Se activa cuando pulsamos el botón de guardar. Recoge los textos, mira que nada esté
     * vacío, valida el formato del DNI y comprueba que la edad y el dorsal sean números.
     * Si todo es correcto, mete al jugador en la base de datos y vuelve atrás.
     *
     * @param event El evento del clic sobre el botón de guardar.
     */
    @FXML
    private void guardar(ActionEvent event) {
        try {
            String dni = txtDni.getText().trim();
            String nombre = txtNombre.getText().trim();
            String edadStr = txtEdad.getText().trim();
            String dorsalStr = txtDorsal.getText().trim();
            String posicion = comboPosicion.getValue();

            if (dni.isEmpty() || nombre.isEmpty() || posicion == null || edadStr.isEmpty() || dorsalStr.isEmpty()) {
                Utils.mostrarAlerta("Campos vacíos", "Debes completar todos los campos, incluida la posición.");
                return;
            }

            if (!Utils.validarDNI(dni)) {
                Utils.mostrarAlerta("DNI Inválido", "El DNI no tiene un formato válido (debe tener 8 números y 1 letra).");
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

    /**
     * Nos saca de esta pantalla y nos lleva de vuelta al panel con la lista de jugadores,
     * volviendo a pasarle los datos del equipo y de la liga para que no se pierdan los datos.
     *
     * @param event El evento del clic sobre el botón de volver.
     */
    @FXML
    private void volver(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/leaguemanager/gestion-jugadores.fxml"));
            Parent root = loader.load();

            JugadoresController controller = loader.getController();
            controller.init(this.nombreEquipo, this.competicionActual, this.escenaClasificacion);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Gestión de Jugadores - " + this.nombreEquipo);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Utils.mostrarAlerta("Error", "No se pudo regresar a la gestión de jugadores.");
        }
    }
}