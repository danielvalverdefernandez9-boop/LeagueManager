package leaguemanager.DAO;

import leaguemanager.dataAccess.ConnectionBD;

import java.sql.*;

public class ParticipaDAO {
    private Connection con;

    public ParticipaDAO() {
        con = ConnectionBD.getInstance().getCon();
    }

    public boolean vincularEquipoACompeticion(String nombreEquipo, String nombreCompeticion) {
        String sql = "INSERT INTO Participa (equipo_nombre, competicion_nombre) VALUES (?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombreEquipo);
            ps.setString(2, nombreCompeticion);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
