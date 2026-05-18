package leaguemanager.DAO;

import leaguemanager.dataAccess.ConnectionBD;
import leaguemanager.model.Equipo;
import leaguemanager.model.Jugador;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JugadorDAO {

    private final Connection con;

    public JugadorDAO() {
        // Inicializar conexión mediante Singleton
        con = ConnectionBD.getInstance().getCon();
    }
    /**
     * Inserta un nuevo jugador en la base de datos.
     *
     * @param jugador objeto Jugador a insertar
     * @return true si se insertó correctamente
     */
    public boolean insertar(Jugador jugador) {

        String sql = """
                INSERT INTO Jugador
                (dni, nombre, edad, posicion, dorsal, equipo_nombre)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, jugador.getDni());
            ps.setString(2, jugador.getNombre());
            ps.setInt(3, jugador.getEdad());
            ps.setString(4, jugador.getPosicion());
            ps.setInt(5, jugador.getDorsal());
            ps.setString(6, jugador.getEquipo().getNombre());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Actualiza los datos de un jugador existente.
     *
     * @param jugador jugador con datos actualizados
     * @return true si se actualizó correctamente
     */
    public boolean actualizar(Jugador jugador) {

        String sql = """
                UPDATE Jugador
                SET nombre = ?,
                    edad = ?,
                    posicion = ?,
                    dorsal = ?,
                    equipo_nombre = ?
                WHERE dni = ?
                """;

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, jugador.getNombre());
            ps.setInt(2, jugador.getEdad());
            ps.setString(3, jugador.getPosicion());
            ps.setInt(4, jugador.getDorsal());
            ps.setString(5, jugador.getEquipo().getNombre());
            ps.setString(6, jugador.getDni());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Elimina un jugador por su DNI.
     *
     * @param dni DNI del jugador
     * @return true si se eliminó correctamente
     */
    public boolean eliminar(String dni) {

        String sql = "DELETE FROM Jugador WHERE dni = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, dni);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Busca un jugador por su DNI.
     *
     * @param dni DNI del jugador
     * @return objeto Jugador o null si no existe
     */
    public Jugador buscarPorId(String dni) {

        String sql = """
                SELECT
                    j.*,
                    e.nombre AS eq_nombre,
                    e.ciudad,
                    e.estadio,
                    e.fecha_fundacion
                FROM Jugador j
                LEFT JOIN Equipo e
                    ON j.equipo_nombre = e.nombre
                WHERE j.dni = ?
                """;

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, dni);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Equipo equipo = null;

                if (rs.getString("eq_nombre") != null) {

                    equipo = new Equipo(
                            rs.getString("eq_nombre"),
                            rs.getString("ciudad"),
                            rs.getString("estadio"),
                            rs.getDate("fecha_fundacion").toLocalDate()
                    );
                }

                return new Jugador(
                        rs.getString("dni"),
                        rs.getString("nombre"),
                        rs.getInt("edad"),
                        rs.getString("posicion"),
                        rs.getInt("dorsal"),
                        equipo
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Lista todos los jugadores.
     *
     * @return lista de jugadores
     */
    public List<Jugador> listarTodos() {

        List<Jugador> jugadores = new ArrayList<>();

        String sql = """
                SELECT
                    j.*,
                    e.nombre AS eq_nombre
                FROM Jugador j
                LEFT JOIN Equipo e
                    ON j.equipo_nombre = e.nombre
                """;

        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {

                Equipo equipo = null;

                if (rs.getString("eq_nombre") != null) {
                    equipo = new Equipo(
                            rs.getString("eq_nombre"),
                            null,
                            null,
                            null
                    );
                }

                Jugador jugador = new Jugador(
                        rs.getString("dni"),
                        rs.getString("nombre"),
                        rs.getInt("edad"),
                        rs.getString("posicion"),
                        rs.getInt("dorsal"),
                        equipo
                );

                jugadores.add(jugador);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return jugadores;
    }

    /**
     * Busca jugadores pertenecientes a un equipo.
     *
     * @param nombreEquipo nombre del equipo
     * @return lista de jugadores
     */
    public List<Jugador> buscarPorEquipo(String nombreEquipo) {

        List<Jugador> jugadores = new ArrayList<>();

        String sql = """
                SELECT *
                FROM Jugador
                WHERE equipo_nombre = ?
                """;

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombreEquipo);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Equipo equipo = new Equipo(
                        nombreEquipo,
                        null,
                        null,
                        null
                );

                Jugador jugador = new Jugador(
                        rs.getString("dni"),
                        rs.getString("nombre"),
                        rs.getInt("edad"),
                        rs.getString("posicion"),
                        rs.getInt("dorsal"),
                        equipo
                );

                jugadores.add(jugador);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return jugadores;
    }

    /**
     * Cuenta el número de jugadores de un equipo.
     *
     * @param nombreEquipo nombre del equipo
     * @return total de jugadores
     */
    public int contarPorEquipo(String nombreEquipo) {

        String sql = """
                SELECT COUNT(*) AS total
                FROM Jugador
                WHERE equipo_nombre = ?
                """;

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombreEquipo);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }
}