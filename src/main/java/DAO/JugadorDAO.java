package DAO;

import dataAccess.ConnectionBD;
import model.Equipo;
import model.Jugador;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JugadorDAO implements DAO<Jugador, String> {

    @Override
    public boolean insertar(Jugador j) {

        String sql = "INSERT INTO Jugador (dni, nombre, edad, posicion, dorsal, equipo_nombre) VALUES (?, ?, ?, ?, ?, ?)";

        Connection con = ConnectionBD.getInstance().getCon();

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, j.getDni());
            ps.setString(2, j.getNombre());
            ps.setInt(3, j.getEdad());
            ps.setString(4, j.getPosicion());
            ps.setInt(5, j.getDorsal());
            ps.setString(6, j.getEquipo().getNombre());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean actualizar(Jugador j) {

        String sql = "UPDATE Jugador SET nombre=?, edad=?, posicion=?, dorsal=?, equipo_nombre=? WHERE dni=?";
        Connection con = ConnectionBD.getInstance().getCon();
        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, j.getNombre());
            ps.setInt(2, j.getEdad());
            ps.setString(3, j.getPosicion());
            ps.setInt(4, j.getDorsal());
            ps.setString(5, j.getEquipo().getNombre());
            ps.setString(6, j.getDni());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminar(String dni) {

        String sql = "DELETE FROM Jugador WHERE dni=?";
        Connection con = ConnectionBD.getInstance().getCon();
        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, dni);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Jugador buscarPorId(String dni) {

        String sql = "SELECT j.*, e.nombre AS eq_nombre, e.ciudad, e.estadio, e.fecha_fundacion " +
                "FROM Jugador j " +
                "LEFT JOIN Equipo e ON j.equipo_nombre = e.nombre " +
                "WHERE j.dni=?";
        Connection con = ConnectionBD.getInstance().getCon();
        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, dni);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Equipo equipo = null;

                String nombreEq = rs.getString("eq_nombre");

                if (nombreEq != null) {
                    equipo = new Equipo(
                            nombreEq,
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

    @Override
    public List<Jugador> listarTodos() {

        List<Jugador> lista = new ArrayList<>();

        String sql = "SELECT j.*, e.nombre AS eq_nombre " +
                "FROM Jugador j " +
                "LEFT JOIN Equipo e ON j.equipo_nombre = e.nombre";
        Connection con = ConnectionBD.getInstance().getCon();
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

                Jugador j = new Jugador(
                        rs.getString("dni"),
                        rs.getString("nombre"),
                        rs.getInt("edad"),
                        rs.getString("posicion"),
                        rs.getInt("dorsal"),
                        equipo
                );

                lista.add(j);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // 🔥 EXTRA: jugadores por equipo (muy típico en examen)
    public List<Jugador> buscarPorEquipo(String nombreEquipo) {

        List<Jugador> lista = new ArrayList<>();

        String sql = "SELECT * FROM Jugador WHERE equipo_nombre=?";
        Connection con = ConnectionBD.getInstance().getCon();
        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombreEquipo);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Equipo equipo = new Equipo(nombreEquipo, null, null, null);

                lista.add(new Jugador(
                        rs.getString("dni"),
                        rs.getString("nombre"),
                        rs.getInt("edad"),
                        rs.getString("posicion"),
                        rs.getInt("dorsal"),
                        equipo
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}
