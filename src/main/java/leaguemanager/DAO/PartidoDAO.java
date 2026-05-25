package leaguemanager.DAO;
import leaguemanager.dataAccess.ConnectionBD;
import leaguemanager.model.Partido;
import java.sql.*;

public class PartidoDAO {

    private Connection con;

    public PartidoDAO() {
        con = ConnectionBD.getInstance().getCon();
    }

    /**
     * Método que registra un nuevo partido en la base de datos almacenando sus goles,
     * fecha y los nombres de los equipos y la competición vinculados.
     *
     * @param p objeto Partido que contiene toda la información a insertar
     * @return true si el registro se insertó correctamente, false en caso de error
     */
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
}