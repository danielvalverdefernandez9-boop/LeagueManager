import DAO.EquipoDAO;
import DAO.JugadorDAO;
import DAO.PartidoDAO;
import DAO.CompeticionDAO;

import dataAccess.ConnectionBD;

import model.Competicion;
import model.Equipo;
import model.Jugador;
import model.Partido;

import java.sql.SQLException;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {

        try {

            // 1. Conectar a la BD
            ConnectionBD.getInstance().connect();

            // 2. Instanciar DAOs
            EquipoDAO equipoDAO = new EquipoDAO();
            JugadorDAO jugadorDAO = new JugadorDAO();
            PartidoDAO partidoDAO = new PartidoDAO();
            CompeticionDAO competicionDAO = new CompeticionDAO();

            // 3. Crear equipos
            Equipo barcelona = new Equipo(
                    "FC Barcelona",
                    "Barcelona",
                    "Camp Nou",
                    LocalDate.of(1899, 11, 29)
            );

            Equipo madrid = new Equipo(
                    "Real Madrid",
                    "Madrid",
                    "Santiago Bernabeu",
                    LocalDate.of(1902, 3, 6)
            );

            System.out.println("Insertando equipos...");
            equipoDAO.insertar(barcelona);
            equipoDAO.insertar(madrid);

            // 4. Crear jugadores
            Jugador jugador1 = new Jugador(
                    "12345678A",
                    "Pedri",
                    22,
                    "Centrocampista",
                    8,
                    barcelona
            );

            Jugador jugador2 = new Jugador(
                    "87654321B",
                    "Vinicius",
                    24,
                    "Delantero",
                    7,
                    madrid
            );

            System.out.println("Insertando jugadores...");
            jugadorDAO.insertar(jugador1);
            jugadorDAO.insertar(jugador2);

            // 5. Crear competición
            Competicion laliga = new Competicion("LaLiga", 20);

            System.out.println("Insertando competición...");
            competicionDAO.insertar(laliga);

            // 6. Crear partido
            Partido partido = new Partido(
                    1,
                    LocalDate.now(),
                    3,
                    1,
                    barcelona,
                    madrid,
                    laliga
            );

            System.out.println("Insertando partido...");
            partidoDAO.insertar(partido);

            // 7. LISTAR EQUIPOS
            System.out.println("\n--- LISTADO DE EQUIPOS ---");

            equipoDAO.listarTodos().forEach(e ->
                    System.out.println(
                            e.getNombre() +
                                    " (" +
                                    e.getCiudad() +
                                    ")"
                    )
            );

            // 8. LISTAR JUGADORES
            System.out.println("\n--- LISTADO DE JUGADORES ---");

            jugadorDAO.listarTodos().forEach(j ->
                    System.out.println(
                            j.getNombre() +
                                    " - " +
                                    j.getPosicion() +
                                    " [" +
                                    j.getEquipo().getNombre() +
                                    "]"
                    )
            );

            // 9. BUSCAR JUGADOR POR DNI
            System.out.println("\n--- BUSCAR JUGADOR POR DNI (12345678A) ---");

            Jugador busqueda = jugadorDAO.buscarPorId("12345678A");

            if (busqueda != null) {
                System.out.println("Encontrado: " + busqueda.getNombre());
            }

            // 10. JUGADORES DEL BARÇA
            System.out.println("\n--- JUGADORES DEL BARÇA ---");

            jugadorDAO.buscarPorEquipo("FC Barcelona")
                    .forEach(j -> System.out.println("- " + j.getNombre()));

            // 11. LISTAR PARTIDOS
            System.out.println("\n--- LISTADO DE PARTIDOS ---");

            partidoDAO.listarTodos().forEach(p ->
                    System.out.println(
                            p.getEquipoLocal().getNombre()
                                    + " "
                                    + p.getGoles_local()
                                    + "-"
                                    + p.getGoles_visitante()
                                    + " "
                                    + p.getEquipoVisitante().getNombre()
                                    + " ["
                                    + p.getCompeticion().getNombre()
                                    + "]"
                    )
            );

        } catch (SQLException e) {

            System.err.println("Error SQL: " + e.getMessage());
            e.printStackTrace();

        } catch (Exception e) {

            System.err.println("Error general: " + e.getMessage());
            e.printStackTrace();
        }
    }
}