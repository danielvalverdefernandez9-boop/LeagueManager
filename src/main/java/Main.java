import DAO.EquipoDAO;
import DAO.JugadorDAO;
import dataAccess.ConnectionBD;
import model.Equipo;
import model.Jugador;

import java.sql.SQLException;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {

        try {

            ConnectionBD.getInstance().connect();

            EquipoDAO equipoDAO = new EquipoDAO();
            JugadorDAO jugadorDAO = new JugadorDAO();

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
                    LocalDate.now()
            );

            equipoDAO.insertar(barcelona);
            equipoDAO.insertar(madrid);

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

            jugadorDAO.insertar(jugador1);
            jugadorDAO.insertar(jugador2);

            System.out.println("\n--- EQUIPOS ---");
            equipoDAO.listarTodos()
                    .forEach(System.out::println);

            System.out.println("\n--- JUGADORES ---");
            jugadorDAO.listarTodos()
                    .forEach(System.out::println);

            System.out.println("\n--- BUSCAR JUGADOR ---");
            System.out.println(
                    jugadorDAO.buscarPorId("12345678A")
            );

            System.out.println("\n--- JUGADORES DEL BARÇA ---");
            jugadorDAO.buscarPorEquipo("FC Barcelona")
                    .forEach(System.out::println);

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }
}