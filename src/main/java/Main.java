import DAO.*;
import dataAccess.ConnectionBD;
import model.*;
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
            Equipo barcelona = new Equipo("FC Barcelona", "Barcelona", "Camp Nou", LocalDate.of(1899, 11, 29));
            Equipo madrid = new Equipo("Real Madrid", "Madrid", "Santiago Bernabeu", LocalDate.of(1902, 3, 6));

            System.out.println("Insertando equipos...");
            equipoDAO.insertar(barcelona);
            equipoDAO.insertar(madrid);

            // 4. Crear jugadores
            Jugador jugador1 = new Jugador("12345678A", "Pedri", 22, "Centrocampista", 8, barcelona);
            Jugador jugador2 = new Jugador("87654321B", "Vinicius", 24, "Delantero", 7, madrid);

            System.out.println("Insertando jugadores...");
            jugadorDAO.insertar(jugador1);
            jugadorDAO.insertar(jugador2);

            // 5. Crear competición
            Competicion laliga = new Competicion("LaLiga", 20);
            System.out.println("Insertando competición...");
            competicionDAO.insertar(laliga);

            // 6. Crear partido (ID 1)
            Partido partido = new Partido(1, LocalDate.now(), 3, 1, barcelona, madrid, laliga);
            System.out.println("Insertando partido...");
            partidoDAO.insertar(partido);

            // 7. LISTAR EQUIPOS
            System.out.println("\n--- LISTADO DE EQUIPOS ---");
            equipoDAO.listarTodos().forEach(e -> System.out.println(e.getNombre() + " (" + e.getCiudad() + ")"));

            // 8. LISTAR JUGADORES
            System.out.println("\n--- LISTADO DE JUGADORES ---");
            jugadorDAO.listarTodos().forEach(j -> System.out.println(j.getNombre() + " - " + j.getPosicion() + " [" + j.getEquipo().getNombre() + "]"));

            // 9. BUSCAR JUGADOR POR DNI
            System.out.println("\n--- BUSCAR JUGADOR POR DNI (12345678A) ---");
            Jugador busqueda = jugadorDAO.buscarPorId("12345678A");
            if (busqueda != null) System.out.println("Encontrado: " + busqueda.getNombre());

            // 10. JUGADORES DEL BARÇA
            System.out.println("\n--- JUGADORES DEL BARÇA ---");
            jugadorDAO.buscarPorEquipo("FC Barcelona").forEach(j -> System.out.println("- " + j.getNombre()));

            // 11. LISTAR PARTIDOS
            System.out.println("\n--- LISTADO DE PARTIDOS ---");
            partidoDAO.listarTodos().forEach(p -> System.out.println(p.getEquipoLocal().getNombre() + " " + p.getGoles_local() + "-" + p.getGoles_visitante() + " " + p.getEquipoVisitante().getNombre() + " [" + p.getCompeticion().getNombre() + "]"));


            System.out.println("--- INICIANDO PRUEBAS DE RELACIONES Y ENTRENADORES ---");

            // 2. Instanciar DAOs nuevos
            EntrenadorDAO entrenadorDAO = new EntrenadorDAO();
            EntrenaDAO entrenaDAO = new EntrenaDAO();
            ParticipaDAO participaDAO = new ParticipaDAO();
            JuegaDAO juegaDAO = new JuegaDAO();

            // --- PRUEBA ENTRENADOR ---
            System.out.println("1. Insertando Entrenador...");
            Entrenador flick = new Entrenador("11122233C", "Hansi Flick", 59);
            entrenadorDAO.insertar(flick);

            // --- PRUEBA ENTRENA (Relación Entrenador-Equipo-Temporada) ---
            System.out.println("2. Asignando Entrenador a Equipo (Entrena)...");
            entrenaDAO.asignarEntrenador("11122233C", "FC Barcelona", "2024-2025");

            // --- PRUEBA PARTICIPA (Relación Equipo-Competición) ---
            System.out.println("3. Vinculando Equipos a Competición (Participa)...");
            participaDAO.vincularEquipoACompeticion("FC Barcelona", "LaLiga");
            participaDAO.vincularEquipoACompeticion("Real Madrid", "LaLiga");

            // --- PRUEBA JUEGA (Relación Equipo-Partido) ---
            System.out.println("4. Registrando Equipos en el Partido (Juega)...");
            // Usamos el ID 1 que es el que se creó en la parte comentada
            juegaDAO.registrarEquipoEnPartido(1, "FC Barcelona");
            juegaDAO.registrarEquipoEnPartido(1, "Real Madrid");

            System.out.println("\n--- LISTADO DE ENTRENADORES ---");
            entrenadorDAO.listarTodos().forEach(System.out::println);

            System.out.println("\n✅ Pruebas de relaciones completadas correctamente.");

        } catch (SQLException e) {
            System.err.println("Error SQL: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error general: " + e.getMessage());
            e.printStackTrace();
        }
    }
}