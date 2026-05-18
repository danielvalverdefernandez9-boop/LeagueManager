package leaguemanager.DAO;

import leaguemanager.dataAccess.ConnectionBD;
import java.sql.*;

public class JuegaDAO {
    private Connection con;

    public JuegaDAO() {
        /* Inicializamos la conexión utilizando el Singleton de la base de datos */
        con = ConnectionBD.getInstance().getCon();
    }

    /**
     * Método que registra la participación de un equipo concreto en un partido
     * específico insertando una nueva fila en la tabla Juega.
     *
     * @param idPartido identificador único del partido
     * @param nombreEquipo nombre del equipo que participa en dicho partido
     * @return true si el registro se insertó correctamente, false en caso de error
     */
    public boolean registrarEquipoEnPartido(int idPartido, String nombreEquipo) {
        String sql = "INSERT INTO Juega (id_partido, equipo_nombre) VALUES (?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idPartido);
            ps.setString(2, nombreEquipo);

            /* Retornamos true si executeUpdate confirma que se ha creado la fila */
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}