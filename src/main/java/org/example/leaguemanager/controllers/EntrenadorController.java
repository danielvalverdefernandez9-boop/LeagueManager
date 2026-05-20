package org.example.leaguemanager.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import leaguemanager.DAO.EntrenadorDAO;
import leaguemanager.model.Competicion;
import leaguemanager.model.Entrenador;
import leaguemanager.utils.Utils;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class EntrenadorController {

    @FXML private TableView<Entrenador> tablaEntrenadores;
    @FXML private TableColumn<Entrenador, String> colDni;
    @FXML private TableColumn<Entrenador, String> colNombre;
    @FXML private TableColumn<Entrenador, Integer> colEdad;
    @FXML private Label NombreEquipo;

    private String nombreEquipo;
    private Competicion competicionActual;
    private Scene escenaClasificacion;
    private final EntrenadorDAO entrenadorDAO = new EntrenadorDAO();

    /**
     * Prepara la pantalla al cargar. Guarda el equipo, la liga y la escena de la clasificación
     * en memoria para no perderlas de vista. También pone el nombre del equipo arriba en grande,
     * configura las columnas de la tabla y llama a cargar los entrenadores.
     *
     * @param nombreEquipo El nombre del club del que estamos viendo los entrenadores.
     * @param comp Objeto con la información de la liga activa.
     * @param escenaClasificacion La ventana de la clasificación para poder regresar hasta el final.
     */
    public void init(String nombreEquipo, Competicion comp, Scene escenaClasificacion) {
        this.nombreEquipo = nombreEquipo;
        this.competicionActual = comp;
        this.escenaClasificacion = escenaClasificacion;
        NombreEquipo.setText("EQUIPO: " + nombreEquipo.toUpperCase());

        colDni.setCellValueFactory(new PropertyValueFactory<>("dni"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));

        cargarDatos();
    }

    /**
     * Va a la base de datos a buscar el entrenador que pertenece a este equipo
     * y lo mete en la tabla de la pantalla para que el usuario pueda verlo.
     */
    private void cargarDatos() {
        List<Entrenador> lista = entrenadorDAO.buscarPorEquipo(nombreEquipo);
        tablaEntrenadores.setItems(FXCollections.observableArrayList(lista));
    }

    /**
     * Se activa al pulsar el botón de añadir. Carga la pantalla del formulario para
     * registrar un entrenador nuevo y le pasa toda la información del equipo y de las escenas
     * para no perder la cadena de navegación.
     *
     * @param event El clic en el botón de nuevo entrenador.
     */
    @FXML
    private void nuevoEntrenador(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/leaguemanager/añadir-entrenador.fxml"));
            Parent root = loader.load();

            AnadirEntrenadorController cont = loader.getController();
            cont.init(this.nombreEquipo, this.competicionActual, this.escenaClasificacion);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Asignar Entrenador - " + nombreEquipo);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            Utils.mostrarAlerta("Error", "No se pudo abrir el formulario de inscripción.");
        }
    }

    /**
     * Se activa al pulsar el botón de eliminar. Comprueba que hayamos marcado a alguien
     * en la tabla, nos saca el típico aviso de confirmación y, si le damos a aceptar,
     * lo borra de la base de datos usando el DNI del entrenador y refresca la tabla.
     *
     * @param event El clic en el botón de eliminar.
     */
    @FXML
    private void eliminarSeleccionado(ActionEvent event) {
        Entrenador seleccionado = tablaEntrenadores.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            Utils.mostrarAlerta("Atención", "Selecciona un entrenador de la tabla para eliminarlo.");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar baja");
        confirmacion.setHeaderText("¿Seguro que quieres eliminar al entrenador?");
        confirmacion.setContentText("Se eliminará a: " + seleccionado.getNombre());

        Optional<ButtonType> resultado = confirmacion.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            if (entrenadorDAO.eliminar(seleccionado.getDni())) {
                Utils.mostrarMensaje("Éxito", "Entrenador eliminado correctamente.");
                cargarDatos();
            } else {
                Utils.mostrarAlerta("Error", "No se pudo eliminar el registro de la base de datos.");
            }
        }
    }

    /**
     * Nos saca de esta pantalla y nos devuelve a la ficha técnica del equipo, volviendo
     * a pasarle el nombre del club, la liga y la escena de la clasificación para poder
     * seguir moviéndonos por los menús sin problemas.
     *
     * @param event El clic en el botón de volver.
     */
    @FXML
    private void volver(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/leaguemanager/ficha-equipo.fxml"));
            Parent root = loader.load();

            FichaEquipoController controller = loader.getController();
            controller.init(this.nombreEquipo, this.competicionActual, this.escenaClasificacion);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Ficha del Equipo");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Utils.mostrarAlerta("Error", "No se pudo regresar a la ficha del equipo.");
        }
    }
}