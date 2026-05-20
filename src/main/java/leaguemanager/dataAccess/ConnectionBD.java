package leaguemanager.dataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionBD {

    private final String FILE = "connection.xml";

    private Connection con;
    private ConnectionProperties properties;

    private static ConnectionBD _instance;

    private ConnectionBD() {

        properties = XMLManager.readXML(new ConnectionProperties(), FILE);

        try {
            connect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void connect() throws SQLException {

        try {

            con = DriverManager.getConnection(
                    properties.getURL(),
                    properties.getUser(),
                    properties.getPassword()
            );

            System.out.println("Conexión realizada correctamente");

        } catch (SQLException e) {

            con = null;
            e.printStackTrace();
            throw e;
        }
    }

    private void disconnect() {
        con = null;
    }

    private boolean isConnected() {
        return con != null;
    }

    public static ConnectionBD getInstance() {

        if (_instance == null) {
            _instance = new ConnectionBD();
        }

        return _instance;
    }

    public Connection getCon() {
        return con;
    }
}