package leaguemanager.DAO;

import leaguemanager.dataAccess.ConnectionBD;
import java.sql.*;

public class JuegaDAO {
    private Connection con;

    public JuegaDAO() {
        con = ConnectionBD.getInstance().getCon();
    }

    public boolean registrarEquipoEnPartido(int idPartido, String nombreEquipo) {
        String sql = "INSERT INTO Juega (id_partido, equipo_nombre) VALUES (?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idPartido);
            ps.setString(2, nombreEquipo);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
