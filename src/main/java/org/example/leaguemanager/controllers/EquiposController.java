package org.example.leaguemanager.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import leaguemanager.DAO.ParticipaDAO;
import leaguemanager.model.Competicion;
import leaguemanager.model.Equipo;
import leaguemanager.utils.Utils;
import java.io.IOException;
import java.util.List;

public class EquiposController {

    @FXML private TableView<Equipo> tablaEquiposDisponibles;
    @FXML private TableColumn<Equipo, String> colNombre;

    private Competicion competicionActiva;
    private final ParticipaDAO participaDAO = new ParticipaDAO();

    public void setCompeticion(Competicion c) {
        this.competicionActiva = c;
        if (colNombre != null) {
            colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        }
        cargarDatosTabla();
    }

    private void cargarDatosTabla() {
        if (competicionActiva != null && tablaEquiposDisponibles != null) {
            List<Equipo> listaActualizada = participaDAO.obtenerEquiposPorCompeticion(competicionActiva.getNombre());
            tablaEquiposDisponibles.setItems(FXCollections.observableArrayList(listaActualizada));
        }
    }

    @FXML
    private void continuarJugadores(ActionEvent event) {
        if (competicionActiva == null) {
            Utils.mostrarAlerta("Error", "No hay competición seleccionada.");
            return;
        }

        Equipo seleccionado = tablaEquiposDisponibles.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            Utils.mostrarAlerta("Atención", "Selecciona un equipo de la tabla.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/leaguemanager/ficha-equipo.fxml"));
            Parent root = loader.load();

            FichaEquipoController controller = loader.getController();
            // PASAMOS EQUIPO Y COMPETICIÓN
            controller.init(seleccionado.getNombre(), this.competicionActiva);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void abrirVentanaEmergente(String fxml, String titulo, int modo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();
            if (modo == 1) ((SelectorEquiposController) loader.getController()).initData(competicionActiva.getNombre());
            if (modo == 2) ((CrearEquipoController) loader.getController()).init(competicionActiva.getNombre());
            if (modo == 3) ((EliminarEquiposController) loader.getController()).initData(competicionActiva.getNombre());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            cargarDatosTabla(); 
        } catch (IOException e) { e.printStackTrace(); }
    }

    @FXML private void insertarEquipos(ActionEvent event) { abrirVentanaEmergente("/leaguemanager/selector_equipos.fxml", "Seleccionar", 1); }
    @FXML private void crearEquipo(ActionEvent event) { abrirVentanaEmergente("/leaguemanager/crear-equipo.fxml", "Nuevo", 2); }
    @FXML private void eliminarEquipos(ActionEvent event) { abrirVentanaEmergente("/leaguemanager/eliminar-equipos.fxml", "Eliminar", 3); }
    @FXML private void volverMenu(ActionEvent event) { Utils.cambiarEscena(event, "/leaguemanager/principal.fxml", "Menú"); }
}