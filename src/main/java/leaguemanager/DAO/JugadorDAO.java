package leaguemanager.DAO;

import leaguemanager.dataAccess.ConnectionBD;
import leaguemanager.model.Equipo;
import leaguemanager.model.Jugador;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JugadorDAO {

    private final Connection con;

    /**
     * Constructor de la clase. Nos conectamos a la base de datos usando el Singleton
     * de ConnectionBD para no andar abriendo conexiones nuevas todo el rato.
     */
    public JugadorDAO() {
        con = ConnectionBD.getInstance().getCon();
    }

    /**
     * Mete a un jugador nuevo en la base de datos.
     * Le pasamos todos sus datos personales y el nombre del equipo en el que juega.
     *
     * @param jugador El objeto jugador que queremos guardar.
     * @return true si se guardó bien en la tabla, false si salta un error de SQL.
     */
    public boolean insertar(Jugador jugador) {
        String sql = """
                INSERT INTO Jugador (dni, nombre, edad, posicion, dorsal, equipo_nombre)
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
     * Borra a un jugador de la base de datos usando su DNI.
     *
     * @param dni El DNI del jugador que queremos quitar del sistema.
     * @return true si se borró el jugador, false si hubo un problema.
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
     * Saca la plantilla completa de un equipo buscando por su nombre.
     * Crea un objeto equipo básico y se lo asocia a cada jugador que va encontrando.
     *
     * @param nombreEquipo El nombre del club del que queremos sacar los jugadores.
     * @return Una lista de jugadores que pertenecen a ese equipo de fútbol.
     */
    public List<Jugador> buscarPorEquipo(String nombreEquipo) {
        List<Jugador> jugadores = new ArrayList<>();
        String sql = "SELECT * FROM Jugador WHERE equipo_nombre = ?";

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
}