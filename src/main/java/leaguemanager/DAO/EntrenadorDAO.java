package leaguemanager.DAO;

import leaguemanager.dataAccess.ConnectionBD;
import leaguemanager.model.Entrenador;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EntrenadorDAO {

    private Connection con;

    public EntrenadorDAO() {
        this.con = ConnectionBD.getInstance().getCon();
    }

    /**
     * Método que registra un nuevo entrenador en la base de datos almacenando su DNI,
     * nombre y edad en la tabla correspondiente.
     *
     * @param e objeto Entrenador que contiene la información a persistir
     * @return true si la inserción fue exitosa, false en caso contrario
     */
    public boolean insertar(Entrenador e) {
        String sql = "INSERT INTO Entrenador (dni, nombre, edad) VALUES (?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, e.getDni());
            ps.setString(2, e.getNombre());
            ps.setInt(3, e.getEdad());

            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Método que elimina de forma permanente el registro de un entrenador
     * de la base de datos según el DNI proporcionado.
     *
     * @param dni documento de identidad del entrenador a eliminar
     * @return true si la eliminación fue exitosa, false en caso de error
     */
    public boolean eliminar(String dni) {
        String sql = "DELETE FROM Entrenador WHERE dni=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, dni);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Versión que devuelve una lista con todos los entrenadores almacenados en la
     * tabla Entrenador de la base de datos.
     *
     * @return lista con todos los objetos Entrenador registrados
     */
    public List<Entrenador> listarTodos() {
        List<Entrenador> lista = new ArrayList<>();
        String sql = "SELECT * FROM Entrenador";
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new Entrenador(
                        rs.getString("dni"),
                        rs.getString("nombre"),
                        rs.getInt("edad")
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    /**
     * Método que recupera la lista de entrenadores vinculados a un equipo específico
     * realizando una consulta cruzada (JOIN) con la tabla asociativa Entrena.
     *
     * @param nombreEquipo el nombre identificador del equipo seleccionado
     * @return lista de objetos Entrenador que pertenecen o han pertenecido a ese equipo
     */
    public List<Entrenador> buscarPorEquipo(String nombreEquipo) {
        List<Entrenador> lista = new ArrayList<>();
        String sql = "SELECT e.* FROM Entrenador e " +
                "JOIN Entrena ent ON e.dni = ent.entrenador_dni " +
                "WHERE ent.equipo_nombre = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombreEquipo);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Entrenador(
                            rs.getString("dni"),
                            rs.getString("nombre"),
                            rs.getInt("edad")
                    ));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }
    /**
     * Vincula un entrenador existente con un equipo en la tabla asociativa Entrena
     * para la temporada actual.
     *
     * @param dni Entrenador a vincular
     * @param nombreEquipo Equipo al que se le asigna
     * @param temporada Temporada de la vinculación
     * @return true si la relación se guardó correctamente
     */
    public boolean asignarAEquipo(String dni, String nombreEquipo, String temporada) {
        String sql = "INSERT INTO Entrena (entrenador_dni, equipo_nombre, temporada) VALUES (?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, dni);
            ps.setString(2, nombreEquipo);
            ps.setString(3, temporada);

            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}