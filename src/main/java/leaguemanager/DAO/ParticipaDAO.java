package leaguemanager.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import leaguemanager.dataAccess.ConnectionBD;
import leaguemanager.model.Equipo;
import java.sql.*;

public class ParticipaDAO {
    private Connection con;

    public ParticipaDAO() {
        con = ConnectionBD.getInstance().getCon();
    }

    /**
     * Método que registra la participación de un equipo en una competición específica
     * insertando una nueva fila en la tabla Participa.
     *
     * @param nombreEquipo nombre del equipo a inscribir
     * @param nombreCompeticion nombre de la competición donde se inscribe
     * @return true si la inserción fue exitosa, false en caso contrario
     */
    public boolean vincularEquipoACompeticion(String nombreEquipo, String nombreCompeticion) {
        String sql = "INSERT INTO Participa (equipo_nombre, competicion_nombre) VALUES (?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombreEquipo);
            ps.setString(2, nombreCompeticion);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            // Logueamos el error pero permitimos que el flujo continúe devolviendo false
            System.err.println("ERROR SQL en ParticipaDAO: " + e.getMessage());
            return false;
        }
    }

    /**
     * Método que elimina el vínculo de participación entre un equipo y una competición,
     * retirando al equipo de dicha liga sin borrarlo de la tabla general de equipos.
     *
     * @param nombreEquipo nombre del equipo a retirar
     * @param nombreCompeticion nombre de la competición de la cual se retira
     * @return true si se eliminó el registro correctamente, false si no hubo cambios
     */
    public boolean eliminarEquipoDeCompeticion(String nombreEquipo, String nombreCompeticion) {
        String sql = "DELETE FROM Participa WHERE equipo_nombre = ? AND competicion_nombre = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, nombreEquipo);
            pstmt.setString(2, nombreCompeticion);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Método que obtiene la lista de todos los equipos inscritos en una competición
     * determinada mediante una subconsulta a la tabla Participa.
     *
     * @param nombreComp nombre de la competición a consultar
     * @return lista observable con los objetos Equipo que participan en la competición
     */
    public ObservableList<Equipo> obtenerEquiposPorCompeticion(String nombreComp) {
        ObservableList<Equipo> lista = FXCollections.observableArrayList();
        String sql = "SELECT * FROM equipo WHERE nombre IN (SELECT equipo_nombre FROM Participa WHERE competicion_nombre = ?)";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, nombreComp);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Equipo equipo = new Equipo(
                        rs.getString("nombre"),
                        rs.getString("ciudad"),
                        rs.getString("estadio"),
                        rs.getDate("fecha_fundacion").toLocalDate()
                );
                lista.add(equipo);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener equipos de la competición: " + e.getMessage());
        }
        return lista;
    }
}