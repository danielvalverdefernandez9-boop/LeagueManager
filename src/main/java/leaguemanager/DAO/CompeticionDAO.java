package leaguemanager.DAO;

import leaguemanager.dataAccess.ConnectionBD;
import leaguemanager.model.Competicion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompeticionDAO {

    private Connection con;

    public CompeticionDAO() {
        con = ConnectionBD.getInstance().getCon();
    }

    public boolean insertar(Competicion c) {
        String sql = "INSERT INTO Competicion (nombre, numero_equipos, temporada) VALUES (?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getNombre());
            ps.setInt(2, c.getNumero_equipos());
            ps.setString(3, c.getTemporada());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Competicion buscarPorNombre(String nombre) {
        String sql = "SELECT * FROM Competicion WHERE nombre = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Competicion(
                        rs.getString("nombre"),
                        rs.getInt("numero_equipos"),
                        rs.getString("temporada")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Competicion> listarTodas() {
        List<Competicion> lista = new ArrayList<>();

        String sql = "SELECT * FROM Competicion";

        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new Competicion(
                        rs.getString("nombre"),
                        rs.getInt("numero_equipos"),
                        rs.getString("temporada")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}