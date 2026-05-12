module leaguemanager {

    requires javafx.controls;
    requires javafx.fxml;

    requires java.sql;
    requires java.xml.bind;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens leaguemanager to javafx.fxml;
    exports leaguemanager;

    opens leaguemanager.DAO to javafx.fxml;
    exports leaguemanager.DAO;

    opens org.example.leaguemanager.controllers to javafx.fxml;
    exports org.example.leaguemanager.controllers;

    opens leaguemanager.model to javafx.fxml;
    exports leaguemanager.model;

    opens leaguemanager.dataAccess to javafx.fxml, java.xml.bind;
    exports leaguemanager.dataAccess;
    exports org.example.leaguemanager;
    opens org.example.leaguemanager to javafx.fxml;
}