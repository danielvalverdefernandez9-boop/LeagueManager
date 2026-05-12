package leaguemanager.DAO;

import leaguemanager.dataAccess.ConnectionBD;
import leaguemanager.model.Competicion;
import leaguemanager.model.Equipo;
import leaguemanager.model.Partido;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PartidoDAO {

    private Connection con;

    public PartidoDAO() {
        con = ConnectionBD.getInstance().getCon();
    }

    // INSERTAR PARTIDO
    public boolean insertar(Partido p) {

        String sql = "INSERT INTO Partido " +
                "(id_partido, fecha, goles_local, goles_visitante, " +
                "equipo_local, equipo_visitante, competicion_nombre) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, p.getId_partido());
            ps.setDate(2, Date.valueOf(p.getFecha()));
            ps.setInt(3, p.getGoles_local());
            ps.setInt(4, p.getGoles_visitante());

            ps.setString(5, p.getEquipoLocal().getNombre());
            ps.setString(6, p.getEquipoVisitante().getNombre());

            ps.setString(7, p.getCompeticion().getNombre());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // LISTAR TODOS LOS PARTIDOS
    public List<Partido> listarTodos() {

        List<Partido> lista = new ArrayList<>();

        String sql = "SELECT p.*, c.numero_equipos " +
                "FROM Partido p " +
                "JOIN Competicion c ON p.competicion_nombre = c.nombre";

        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {

                Competicion comp = new Competicion(
                        rs.getString("competicion_nombre"),
                        rs.getInt("numero_equipos")
                );

                Equipo local = new Equipo();
                local.setNombre(rs.getString("equipo_local"));

                Equipo visitante = new Equipo();
                visitante.setNombre(rs.getString("equipo_visitante"));

                Partido partido = new Partido(
                        rs.getInt("id_partido"),
                        rs.getDate("fecha").toLocalDate(),
                        rs.getInt("goles_local"),
                        rs.getInt("goles_visitante"),
                        local,
                        visitante,
                        comp
                );

                lista.add(partido);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // BUSCAR PARTIDO POR ID
    public Partido buscarPorId(int id) {

        String sql = "SELECT p.*, c.numero_equipos " +
                "FROM Partido p " +
                "JOIN Competicion c ON p.competicion_nombre = c.nombre " +
                "WHERE p.id_partido = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Competicion comp = new Competicion(
                        rs.getString("competicion_nombre"),
                        rs.getInt("numero_equipos")
                );

                Equipo local = new Equipo();
                local.setNombre(rs.getString("equipo_local"));

                Equipo visitante = new Equipo();
                visitante.setNombre(rs.getString("equipo_visitante"));

                return new Partido(
                        rs.getInt("id_partido"),
                        rs.getDate("fecha").toLocalDate(),
                        rs.getInt("goles_local"),
                        rs.getInt("goles_visitante"),
                        local,
                        visitante,
                        comp
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}