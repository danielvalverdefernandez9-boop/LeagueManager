package leaguemanager.DAO;

import leaguemanager.dataAccess.ConnectionBD;
import leaguemanager.model.Competicion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompeticionDAO {

    private Connection con;

    public CompeticionDAO() {
        /* Inicializamos la conexión utilizando el Singleton de la base de datos */
        con = ConnectionBD.getInstance().getCon();
    }

    /**
     * Método que registra una nueva competición en la base de datos almacenando su nombre,
     * el número máximo de equipos y la temporada correspondiente.
     *
     * @param c objeto Competicion que contiene la información a persistir
     * @return true si la inserción fue exitosa, false en caso de error de SQL
     */
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

    /**
     * Método de búsqueda que recupera la información detallada de una competición
     * específica a partir de su nombre único.
     *
     * @param nombre nombre identificador de la competición a localizar
     * @return objeto Competicion si se encuentra en la base de datos, o null si no existe
     */
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

    /**
     * Versión que devuelve una lista con todas las competiciones almacenadas en la
     * tabla Competicion de la base de datos.
     *
     * @return lista con todos los objetos Competicion registrados
     */
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