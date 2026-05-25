package leaguemanager.DAO;

import leaguemanager.dataAccess.ConnectionBD;
import leaguemanager.model.Competicion;
import leaguemanager.model.Equipo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompeticionDAO {

    private static Connection con;

    /**
     * Constructor de la clase. Se conecta a la base de datos usando el Singleton
     * que creamos en la clase ConnectionBD para reutilizar la misma conexión.
     */
    public CompeticionDAO() {
        con = ConnectionBD.getInstance().getCon();
    }

    /**
     * Guarda una nueva competición en la base de datos.
     * Le pasa el nombre, el número de equipos que va a tener y la temporada.
     *
     * @param c El objeto competición con los datos que rellenó el usuario.
     * @return true si se guardó bien en la base de datos, false si da algún error.
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
     * Busca una competición en la base de datos usando su nombre.
     * Si la encuentra, crea el objeto y además le carga sus equipos con los puntos actualizados.
     *
     * @param nombre El nombre de la liga que queremos buscar.
     * @return La competición encontrada con sus equipos dentro, o null si no existe.
     */
    public static Competicion buscarPorNombre(String nombre) {
        String sql = "SELECT * FROM Competicion WHERE nombre = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Competicion comp = new Competicion(
                        rs.getString("nombre"),
                        rs.getInt("numero_equipos"),
                        rs.getString("temporada")
                );

                comp.setEquipos(cargarEquiposConEstadisticas(nombre));
                return comp;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Saca una lista con todas las competiciones que hay guardadas en la base de datos.
     * Sirve para listarlas en la interfaz y a cada una le mete sus equipos correspondientes.
     *
     * @return Un ArrayList con todas las competiciones de la tabla.
     */
    public List<Competicion> listarTodas() {
        List<Competicion> lista = new ArrayList<>();
        String sql = "SELECT * FROM Competicion";

        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Competicion comp = new Competicion(
                        rs.getString("nombre"),
                        rs.getInt("numero_equipos"),
                        rs.getString("temporada")
                );
                comp.setEquipos(cargarEquiposConEstadisticas(comp.getNombre()));
                lista.add(comp);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    /**
     * Método público para pedir los equipos de una liga.
     * Llama directamente al método privado que calcula los puntos y estadísticas.
     *
     * @param nombreCompeticion El nombre de la liga de la que queremos los equipos.
     * @return La lista de equipos listos para pintar en la tabla de clasificación.
     */
    public List<Equipo> cargarEquiposDeCompeticion(String nombreCompeticion) {
        return cargarEquiposConEstadisticas(nombreCompeticion);
    }

    /**
     * Este método hace la magia de la clasificación. Primero busca qué equipos están
     * metidos en la liga usando la tabla 'Participa'. Luego mira todos los partidos
     * jugados de esa liga y va sumando las victorias, empates y derrotas en caliente.
     *
     * @param nombreCompeticion El nombre de la liga para buscar sus partidos y equipos.
     * @return Lista de equipos con sus estadísticas calculadas desde cero según los partidos.
     */
    private static List<Equipo> cargarEquiposConEstadisticas(String nombreCompeticion) {
        List<Equipo> listaEquipos = new ArrayList<>();

        String sqlEquipos = "SELECT e.* FROM Equipo e " +
                "JOIN Participa p ON e.nombre = p.equipo_nombre " +
                "WHERE p.competicion_nombre = ?";

        String sqlPartidos = "SELECT p.id_partido, p.goles_local, p.goles_visitante, " +
                "(SELECT j.equipo_nombre FROM Juega j WHERE j.id_partido = p.id_partido LIMIT 1) as local, " +
                "(SELECT j.equipo_nombre FROM Juega j WHERE j.id_partido = p.id_partido LIMIT 1 OFFSET 1) as visitante " +
                "FROM Partido p WHERE p.competicion_nombre = ?";

        try {
            try (PreparedStatement psE = con.prepareStatement(sqlEquipos)) {
                psE.setString(1, nombreCompeticion);
                try (ResultSet rsE = psE.executeQuery()) {
                    while (rsE.next()) {
                        Equipo eq = new Equipo();
                        eq.setNombre(rsE.getString("nombre"));
                        eq.setVictorias(0);
                        eq.setEmpates(0);
                        eq.setDerrotas(0);
                        listaEquipos.add(eq);
                    }
                }
            }

            try (PreparedStatement psP = con.prepareStatement(sqlPartidos)) {
                psP.setString(1, nombreCompeticion);
                try (ResultSet rsP = psP.executeQuery()) {
                    while (rsP.next()) {
                        String localNom = rsP.getString("local");
                        String visNom = rsP.getString("visitor");
                        int gL = rsP.getInt("goles_local");
                        int gV = rsP.getInt("goles_visitante");

                        Equipo local = null;
                        Equipo visitante = null;
                        for (Equipo e : listaEquipos) {
                            if (e.getNombre().equals(localNom)) local = e;
                            if (e.getNombre().equals(visNom))  visitante = e;
                        }

                        if (local != null && visitante != null) {
                            if (gL > gV) {
                                local.setVictorias(local.getVictorias() + 1);
                                visitante.setDerrotas(visitante.getDerrotas() + 1);
                            } else if (gL < gV) {
                                visitante.setVictorias(visitante.getVictorias() + 1);
                                local.setDerrotas(local.getDerrotas() + 1);
                            } else {
                                local.setEmpates(local.getEmpates() + 1);
                                visitante.setEmpates(visitante.getEmpates() + 1);
                            }
                        }
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaEquipos;
    }
}