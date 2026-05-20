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
     * Se ejecuta al abrir la ventana. Guarda el nombre de la liga, configura la columna
     * de la tabla, activa la opción para poder seleccionar varios equipos a la vez y la rellena.
     *
     * @param nombreComp El nombre de la competición de la que queremos quitar equipos.
     */
    public void initData(String nombreComp) {
        this.nombreCompeticion = nombreComp;

        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tablaBorrar.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        refrescarTabla();
    }

    /**
     * Mira en la base de datos qué equipos están metidos en la liga. Si no hay ninguno,
     * esconde la tabla y muestra un texto avisando. Si encuentra equipos, los pinta en la tabla
     * y activa el botón de eliminar.
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
            tablaBorrar.setVisible(false);
            btnEliminar.setDisable(true);
            lblMensajeVacio.setText("La competición '" + nombreCompeticion + "' aún no tiene equipos inscritos.");
            lblMensajeVacio.setVisible(true);
        } else {
            tablaBorrar.setItems(inscritos);
            tablaBorrar.setVisible(true);
            btnEliminar.setDisable(false);
            lblMensajeVacio.setVisible(false);
        }
    }

    /**
     * Se activa al pulsar el botón de eliminar. Coge los equipos que el usuario haya seleccionado,
     * saca un cuadro de confirmación en la pantalla y, si el usuario dice que sí, los borra de la
     * tabla 'Participa' en la base de datos y actualiza la vista.
     */
    @FXML
    private void eliminarSeleccionados() {
        ObservableList<Equipo> seleccionados = tablaBorrar.getSelectionModel().getSelectedItems();

        if (seleccionados.isEmpty()) {
            Utils.mostrarAlerta("Atención", "Selecciona al menos un equipo para retirar de la liga.");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar");
        confirmacion.setHeaderText("Retirar equipos de '" + nombreCompeticion + "'");
        confirmacion.setContentText("¿Deseas quitar los equipos seleccionados?");

        Optional<ButtonType> resultado = confirmacion.showAndWait();

        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            for (Equipo e : seleccionados) {
                participaDAO.eliminarEquipoDeCompeticion(e.getNombre(), nombreCompeticion);
            }
            refrescarTabla();
        }
    }

    /**
     * Cierra la ventana emergente actual sin guardar ningún cambio ni borrar nada.
     *
     * @param event El clic en el botón de cancelar.
     */
    @FXML
    private void cancelar(ActionEvent event) {
        Stage stage = (Stage) tablaBorrar.getScene().getWindow();
        stage.close();
    }
}