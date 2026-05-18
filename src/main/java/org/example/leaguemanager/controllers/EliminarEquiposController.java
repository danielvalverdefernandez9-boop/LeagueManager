package org.example.leaguemanager.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import leaguemanager.DAO.ParticipaDAO;
import leaguemanager.model.Equipo;
import leaguemanager.utils.Utils;

import java.util.Optional;

public class EliminarEquiposController {

    @FXML private TableView<Equipo> tablaBorrar;
    @FXML private TableColumn<Equipo, String> colNombre;
    @FXML private Label lblMensajeVacio;
    @FXML private Button btnEliminar;

    private String nombreCompeticion;
    private final ParticipaDAO participaDAO = new ParticipaDAO();

    /**
     * Se llama al abrir la ventana.
     */
    public void initData(String nombreComp) {
        this.nombreCompeticion = nombreComp;

        // Configuración de tabla
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tablaBorrar.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // Cargar equipos por primera vez
        refrescarTabla();
    }

    /**
     * Este método es el corazón de lo que pides:
     * Si no hay equipos (porque la liga es nueva o se borraron todos), muestra el aviso.
     * Si el usuario añade equipos y vuelve a entrar (o refresca), aparecerán.
     */
    public void refrescarTabla() {
        if (nombreCompeticion == null || nombreCompeticion.isEmpty()) {
            lblMensajeVacio.setText("Error: No se ha detectado ninguna competición.");
            lblMensajeVacio.setVisible(true);
            tablaBorrar.setVisible(false);
            btnEliminar.setDisable(true);
            return;
        }

        ObservableList<Equipo> inscritos = participaDAO.obtenerEquiposPorCompeticion(nombreCompeticion);

        if (inscritos == null || inscritos.isEmpty()) {
            /* ESTADO 1: No hay equipos todavía */
            tablaBorrar.setVisible(false);
            btnEliminar.setDisable(true);
            lblMensajeVacio.setText("La competición '" + nombreCompeticion + "' aún no tiene equipos inscritos.");
            lblMensajeVacio.setVisible(true);
        } else {
            /* ESTADO 2: Se han detectado equipos vinculados */
            tablaBorrar.setItems(inscritos);
            tablaBorrar.setVisible(true);
            btnEliminar.setDisable(false);
            lblMensajeVacio.setVisible(false);
        }
    }

    @FXML
    private void eliminarSeleccionados() {
        ObservableList<Equipo> seleccionados = tablaBorrar.getSelectionModel().getSelectedItems();

        if (seleccionados.isEmpty()) {
            Utils.mostrarAlerta("Atención", "Selecciona al menos un equipo para retirar de la liga.");
            return;
        }

        // Confirmación
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar");
        confirmacion.setHeaderText("Retirar equipos de '" + nombreCompeticion + "'");
        confirmacion.setContentText("¿Deseas quitar los equipos seleccionados?");

        Optional<ButtonType> resultado = confirmacion.showAndWait();

        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            for (Equipo e : seleccionados) {
                // Borramos la relación en la tabla 'participa'
                participaDAO.eliminarEquipoDeCompeticion(e.getNombre(), nombreCompeticion);
            }
            // Volvemos a comprobar la base de datos: si ya no quedan, aparecerá el mensaje vacío
            refrescarTabla();
        }
    }

    @FXML
    private void cancelar(ActionEvent event) {
        Stage stage = (Stage) tablaBorrar.getScene().getWindow();
        stage.close();
    }
}