package org.example.leaguemanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;

public class LeagueManagerAPP extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(LeagueManagerAPP.class.getResource("/leaguemanager/principal.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 900, 700);

        // Añadir BootstrapFX
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());

        stage.setTitle("League Manager - Gestor de Liga");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
