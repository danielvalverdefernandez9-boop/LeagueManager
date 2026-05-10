package DAO;

import dataAccess.ConnectionBD;
import model.Equipo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquipoDAO{

    private Connection con;

    public EquipoDAO() {

        con = ConnectionBD
                .getInstance()
                .getCon();
    }

    public boolean insertar(Equipo equipo) {

        String sql =
                "INSERT INTO Equipo " +
                        "(nombre, ciudad, estadio, fecha_fundacion) " +
                        "VALUES (?, ?, ?, ?)";

        try {

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, equipo.getNombre());

            ps.setString(2, equipo.getCiudad());

            ps.setString(3, equipo.getEstadio());

            ps.setDate(4, Date.valueOf(equipo.getFecha_fundacion())
            );

            ps.executeUpdate();

            return true;

        } catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }
    public List<Equipo> listarTodos() {

        List<Equipo> equipos =
                new ArrayList<>();

        String sql = "SELECT * FROM Equipo";

        try {

            Statement st =
                    con.createStatement();

            ResultSet rs =
                    st.executeQuery(sql);

            while (rs.next()) {

                Equipo equipo =
                        new Equipo();

                equipo.setNombre(
                        rs.getString("nombre")
                );

                equipo.setCiudad(
                        rs.getString("ciudad")
                );

                equipo.setEstadio(rs.getString("estadio")
                );

                equipo.setFecha_fundacion(rs.getDate("fecha_fundacion")
                                .toLocalDate()
                );

                equipos.add(equipo);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return equipos;
    }

    public boolean eliminar(String nombre) {

        String sql =
                "DELETE FROM Equipo WHERE nombre = ?";

        try {

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, nombre);

            ps.executeUpdate();

            return true;

        } catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }

}