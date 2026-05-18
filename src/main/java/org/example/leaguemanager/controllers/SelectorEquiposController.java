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
import leaguemanager.utils.Utils;
import java.util.List;

public class SelectorEquiposController {

    @FXML private TableView<Equipo> tablaEquiposDisponibles;
    @FXML private TableColumn<Equipo, String> colNombre;

    private String nombreCompeticion;
    private final EquipoDAO equipoDAO = new EquipoDAO();
    private final ParticipaDAO participaDAO = new ParticipaDAO();

    public void initData(String nombreComp) {
        this.nombreCompeticion = nombreComp;
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tablaEquiposDisponibles.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        cargarEquiposDesdeBD();

        System.out.println("DEBUG: Selector listo para la competición: [" + nombreCompeticion + "]");
    }

    private void cargarEquiposDesdeBD() {
        List<Equipo> lista = equipoDAO.listarTodos();
        if (lista != null) {
            tablaEquiposDisponibles.setItems(FXCollections.observableArrayList(lista));
        }
    }

    @FXML
    private void insertarSeleccionados(ActionEvent event) {
        ObservableList<Equipo> seleccionados = tablaEquiposDisponibles.getSelectionModel().getSelectedItems();

        if (seleccionados.isEmpty()) {
            Utils.mostrarAlerta("Sin selección", "Selecciona al menos un equipo.");
            return;
        }

        boolean huboError = false;
        int insertados = 0;

        for (Equipo equipo : seleccionados) {
            boolean exito = participaDAO.vincularEquipoACompeticion(equipo.getNombre(), nombreCompeticion);
            if (exito) {
                insertados++;
            } else {
                huboError = true;
                Utils.mostrarAlerta("Error de Vinculación",
                        "No se pudo añadir el equipo: " + equipo.getNombre() +
                                ". Verifique que la competición '" + nombreCompeticion + "' existe.");
                break;
            }
        }

        if (insertados > 0 && !huboError) {
            Utils.mostrarMensaje("Éxito", "Todos los equipos se han inscrito correctamente.");
            // CERRAMOS LA VENTANA AL TERMINAR: Así volvemos a la pantalla principal de Equipos
            cerrarVentana(event);
        }
    }

    @FXML
    private void cancelar(ActionEvent event) {
        // En lugar de limpiar, si el usuario cancela, cerramos la ventanita
        cerrarVentana(event);
    }

    @FXML
    private void volver(ActionEvent event) {
        // IMPORTANTE: Eliminamos Utils.cambiarEscena para no perder la competición del padre
        cerrarVentana(event);
    }

    /**
     * Método auxiliar para cerrar esta ventana sin destruir la de Equipos
     */
    private void cerrarVentana(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}