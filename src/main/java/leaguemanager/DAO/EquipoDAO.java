package leaguemanager.DAO;

import leaguemanager.dataAccess.ConnectionBD;
import leaguemanager.model.Equipo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquipoDAO {

    private Connection con;

    public EquipoDAO() {
        /* Inicializamos la conexión utilizando el Singleton de la base de datos */
        con = ConnectionBD.getInstance().getCon();
    }

    /**
     * Método que registra un nuevo equipo en la base de datos almacenando su nombre,
     * ciudad, estadio y fecha de fundación.
     *
     * @param equipo objeto Equipo que contiene la información a persistir
     * @return true si la inserción fue exitosa, false si hubo un error de SQL
     */
    public boolean insertar(Equipo equipo) {
        String sql = "INSERT INTO Equipo (nombre, ciudad, estadio, fecha_fundacion) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, equipo.getNombre());
            ps.setString(2, equipo.getCiudad());
            ps.setString(3, equipo.getEstadio());
            ps.setDate(4, Date.valueOf(equipo.getFecha_fundacion()));

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Versión que devuelve una lista con todos los equipos almacenados en la tabla
     * Equipo de la base de datos, obteniendo sus datos principales.
     *
     * @return lista con todos los objetos Equipo registrados
     */
    public List<Equipo> listarTodos() {
        List<Equipo> equipos = new ArrayList<>();
        String sql = "SELECT * FROM Equipo";

        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Equipo equipo = new Equipo();
                equipo.setNombre(rs.getString("nombre"));
                equipo.setCiudad(rs.getString("ciudad"));
                equipo.setEstadio(rs.getString("estadio"));
                equipo.setFecha_fundacion(rs.getDate("fecha_fundacion").toLocalDate());

                equipos.add(equipo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return equipos;
    }
}