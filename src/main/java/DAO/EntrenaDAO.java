package DAO;

import dataAccess.ConnectionBD;
import java.sql.*;

public class EntrenaDAO {
    private Connection con;

    public EntrenaDAO() {
        con = ConnectionBD.getInstance().getCon();
    }

    public boolean asignarEntrenador(String dniEntrenador, String nombreEquipo, String temporada) {
        String sql = "INSERT INTO Entrena (entrenador_dni, equipo_nombre, temporada) VALUES (?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, dniEntrenador);
            ps.setString(2, nombreEquipo);
            ps.setString(3, temporada);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
