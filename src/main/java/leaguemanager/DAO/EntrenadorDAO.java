package leaguemanager.DAO;

import leaguemanager.dataAccess.ConnectionBD;
import leaguemanager.model.Entrenador;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EntrenadorDAO {

    private Connection con;

    public EntrenadorDAO() {
        /* Inicializamos la conexión utilizando el Singleton de la base de datos */
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
     * Método que actualiza los datos personales de un entrenador existente
     * utilizando su DNI como clave de búsqueda.
     *
     * @param e objeto Entrenador con la información actualizada
     * @return true si se modificó el registro correctamente, false si no hubo cambios
     */
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
     * Método de búsqueda que recupera la información de un entrenador específico
     * a partir de su identificador único (DNI).
     *
     * @param dni documento de identidad del entrenador a localizar
     * @return objeto Entrenador si se encuentra en la base de datos, o null si no existe
     */
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
}