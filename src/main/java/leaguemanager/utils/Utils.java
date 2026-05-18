package leaguemanager.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;

public class Utils {

    /**
     * Cambia la escena actual por una nueva.
     *
     * @param event El evento para obtener la ventana actual.
     * @param rutaFXML El camino al archivo .fxml (ej: "/leaguemanager/ficha.fxml").
     * @param titulo El título que tendrá la nueva ventana.
     * @return El FXMLLoader utilizado, por si necesitas obtener el controlador.
     */
    public static FXMLLoader cambiarEscena(ActionEvent event, String rutaFXML, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(Utils.class.getResource(rutaFXML));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle(titulo);
            stage.show();

            return loader;
        } catch (IOException e) {
            mostrarAlerta("Error de Navegación", "No se pudo cargar la vista: " + rutaFXML);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Muestra una alerta rápida en pantalla.
     */
    public static void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    // En leaguemanager.utils.Utils
    public static void mostrarMensaje(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
