package DAO;

import dataAccess.ConnectionBD;
import model.Equipo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquipoDAO implements DAO<Equipo> {

    private Connection con;

    public EquipoDAO() {
        // Obtenemos la conexión del Singleton que ya tienes
        this.con = ConnectionBD.getInstance().getCon();
    }

    @Override
    public boolean insertar(Equipo equipo) {
        String sql = "INSERT INTO Equipo (nombre, ciudad, estadio, fecha_fundacion) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, equipo.getNombre());
            ps.setString(2, equipo.getCiudad());
            ps.setString(3, equipo.getEstadio());
            ps.setDate(4, Date.valueOf(equipo.getFecha_fundacion())); // Conversión LocalDate a SQL Date

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Equipo> listarTodos() {
        ArrayList<Equipo> lista = new ArrayList<>(); // Estilo Autor: uso de ArrayList[cite: 1]
        String sql = "SELECT * FROM Equipo";

        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Equipo e = new Equipo(
                        rs.getString("nombre"),
                        rs.getString("ciudad"),
                        rs.getString("estadio"),
                        rs.getDate("fecha_fundacion").toLocalDate()
                );
                lista.add(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // Los métodos actualizar, eliminar y buscarPorId seguirían una lógica similar...
    @Override public boolean actualizar(Equipo objeto) { return false; }
    @Override public boolean eliminar(Equipo objeto) { return false; }
    @Override public Equipo buscarPorId(Object id) { return null; }
}
