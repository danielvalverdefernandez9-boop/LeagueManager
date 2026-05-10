package DAO;

import dataAccess.ConnectionBD;
import model.Entrenador;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EntrenadorDAO {

    private Connection con;

    public EntrenadorDAO() {
        // Obtenemos la conexión igual que en tus otros DAOs
        this.con = ConnectionBD.getInstance().getCon();
    }

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

    public boolean actualizar(Entrenador e) {
        String sql = "UPDATE Entrenador SET nombre=?, edad=? WHERE dni=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, e.getNombre());
            ps.setInt(2, e.getEdad());
            ps.setString(3, e.getDni());

            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

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

    public Entrenador buscarPorId(String dni) {
        String sql = "SELECT * FROM Entrenador WHERE dni=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, dni);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Entrenador(
                        rs.getString("dni"),
                        rs.getString("nombre"),
                        rs.getInt("edad")
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

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
}
