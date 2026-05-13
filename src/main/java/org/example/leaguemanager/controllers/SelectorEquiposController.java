package org.example.leaguemanager.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import leaguemanager.DAO.EquipoDAO;
import leaguemanager.DAO.ParticipaDAO;
import leaguemanager.model.Equipo;

import java.util.List;

public class SelectorEquiposController {

    @FXML private TableView<Equipo> tablaEquiposDisponibles;
    @FXML private TableColumn<Equipo, String> colNombre;

    private String nombreCompeticion;
    private final EquipoDAO equipoDAO = new EquipoDAO();
    private final ParticipaDAO participaDAO = new ParticipaDAO();

    /**
     * Inicializa los datos de la ventana.
     * Se debe llamar justo después de cargar el FXML.
     */
    public void initData(String nombreComp) {
        this.nombreCompeticion = nombreComp;

        // 1. Configurar la columna para que lea el atributo "nombre" del modelo Equipo
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        // 2. Habilitar la SELECCIÓN MÚLTIPLE en la tabla
        tablaEquiposDisponibles.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // 3. Cargar los equipos desde la base de datos
        cargarEquipos();
    }

    private void cargarEquipos() {
        List<Equipo> lista = equipoDAO.listarTodos();
        if (lista != null) {
            tablaEquiposDisponibles.setItems(FXCollections.observableArrayList(lista));
        }
    }

    @FXML
    private void insertarSeleccionados(ActionEvent event) {
        // Obtener los elementos seleccionados por el usuario con el cursor
        ObservableList<Equipo> seleccionados = tablaEquiposDisponibles.getSelectionModel().getSelectedItems();

        if (seleccionados.isEmpty()) {
            System.out.println("No se ha seleccionado ningún equipo.");
            return;
        }

        // Insertar cada relación en la tabla Participa
        for (Equipo equipo : seleccionados) {
            boolean exito = participaDAO.vincularEquipoACompeticion(equipo.getNombre(), nombreCompeticion);
            if (exito) {
                System.out.println("Vinculado: " + equipo.getNombre());
            } else {
                System.err.println("Error o duplicado al vincular: " + equipo.getNombre());
            }
        }

        // Cerrar la ventana actual
        cerrarVentana(event);
    }

    @FXML
    private void cancelar(ActionEvent event) {
        cerrarVentana(event);
    }

    private void cerrarVentana(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
