package leaguemanager.DAO;

import leaguemanager.dataAccess.ConnectionBD;
import java.sql.*;

public class EntrenaDAO {
    private Connection con;

    public EntrenaDAO() {
        /* Inicializamos la conexión utilizando el Singleton de la base de datos */
        con = ConnectionBD.getInstance().getCon();
    }

    /**
     * Método que registra la asignación de un entrenador a un equipo para una
     * temporada específica insertando una nueva fila en la tabla Entrena.
     *
     * @param dniEntrenador documento de identidad del entrenador a asignar
     * @param nombreEquipo nombre del equipo al que se asigna el entrenador
     * @param temporada periodo o año de la competición (ej: "2023/24")
     * @return true si la asignación se registró con éxito, false en caso contrario
     */
    public boolean asignarEntrenador(String dniEntrenador, String nombreEquipo, String temporada) {
        String sql = "INSERT INTO Entrena (entrenador_dni, equipo_nombre, temporada) VALUES (?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, dniEntrenador);
            ps.setString(2, nombreEquipo);
            ps.setString(3, temporada);

            /* Retornamos true si executeUpdate confirma que se ha creado la fila en la tabla intermedia */
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}