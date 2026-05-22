package org.example.leaguemanager.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import leaguemanager.model.Competicion;
import leaguemanager.model.Equipo;
import leaguemanager.model.Partido;
import leaguemanager.utils.Utils;
import leaguemanager.dataAccess.ConnectionBD;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ClasificacionController {

    @FXML private TableView<Equipo> tablaClasificacion;
    @FXML private TableColumn<Equipo, String> colEquipo;
    @FXML private TableColumn<Equipo, Integer> colVictorias;
    @FXML private TableColumn<Equipo, Integer> colEmpates;
    @FXML private TableColumn<Equipo, Integer> colDerrotas;
    @FXML private TableColumn<Equipo, Integer> colPuntos;
    @FXML private VBox vboxPartidos;
    @FXML private Label Jornada;
    @FXML private Label lblTituloPanel;

    private ObservableList<Equipo> listaEquipos;
    private int jornadaActual = 1;
    private Competicion competicionActual;
    private final Connection con = ConnectionBD.getInstance().getCon();

    /**
     * Prepara todo al arrancar la pantalla. Mete los equipos en la lista, los ordena
     * por puntos para que la clasificación salga bien, pone el título y calcula qué jornada toca.
     * * MODIFICACIÓN: Ahora recalcula las estadísticas guardadas en la BD antes de ordenar.
     *
     * @param comp El objeto de la competición que se está jugando.
     */
    public void init(Competicion comp) {
        this.competicionActual = comp;
        this.listaEquipos = FXCollections.observableArrayList(comp.getEquipos());

        recalcularEstadisticasDesdeBD();

        this.listaEquipos.sort((e1, e2) -> Integer.compare(e2.getPuntos(), e1.getPuntos()));

        if (lblTituloPanel != null && comp != null) {
            lblTituloPanel.setText(comp.getNombre().toUpperCase());
        }

        configurarTabla();
        calcularJornadaActual();
        generarEnfrentamientos();
    }

    /**
     * Recupera el histórico de partidos de la competición desde la base de datos
     * y recalcula en memoria las estadísticas (victorias, empates, derrotas y puntos)
     * de cada equipo para actualizar correctamente la tabla de clasificación.
     */
    private void recalcularEstadisticasDesdeBD() {
        if (competicionActual == null || listaEquipos.isEmpty()) return;

        for (Equipo e : listaEquipos) {
            e.setVictorias(0);
            e.setEmpates(0);
            e.setDerrotas(0);
        }

        String sql = "SELECT p.goles_local, p.goles_visitante, " +
                "       (SELECT equipo_nombre FROM Juega j WHERE j.id_partido = p.id_partido LIMIT 1) as loc, " +
                "       (SELECT equipo_nombre FROM Juega j WHERE j.id_partido = p.id_partido LIMIT 1, 1) as vis " +
                "FROM Partido p " +
                "WHERE p.competicion_nombre = ? " +
                "ORDER BY p.id_partido ASC";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, competicionActual.getNombre());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String locNom = rs.getString("loc");
                    String visNom = rs.getString("vis");
                    int gL = rs.getInt("goles_local");
                    int gV = rs.getInt("goles_visitante");

                    Equipo local = listaEquipos.stream().filter(e -> e.getNombre().equals(locNom)).findFirst().orElse(null);
                    Equipo visitante = listaEquipos.stream().filter(e -> e.getNombre().equals(visNom)).findFirst().orElse(null);

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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Conecta las columnas de la tabla de la interfaz con las variables de la clase Equipo
     * para que los datos de las victorias, empates y puntos se muestren en sus celdas.
     */
    private void configurarTabla() {
        colEquipo.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colVictorias.setCellValueFactory(new PropertyValueFactory<>("victorias"));
        colEmpates.setCellValueFactory(new PropertyValueFactory<>("empates"));
        colDerrotas.setCellValueFactory(new PropertyValueFactory<>("derrotas"));
        colPuntos.setCellValueFactory(new PropertyValueFactory<>("puntos"));

        tablaClasificacion.setItems(listaEquipos);
    }

    /**
     * Cuenta cuántos partidos hay guardados en la base de datos para saber de forma
     * automática por qué número de jornada va la liga actualmente.
     */
    private void calcularJornadaActual() {
        if (listaEquipos.isEmpty()) {
            jornadaActual = 1;
            return;
        }

        String sqlContar = "SELECT COUNT(*) FROM Partido WHERE competicion_nombre = ?";
        try (PreparedStatement ps = con.prepareStatement(sqlContar)) {
            ps.setString(1, competicionActual.getNombre());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int partidosTotalesJugados = rs.getInt(1);
                    int partidosPorJornada = listaEquipos.size() / 2;

                    if (partidosPorJornada > 0) {
                        jornadaActual = (partidosTotalesJugados / partidosPorJornada) + 1;
                    } else {
                        jornadaActual = 1;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            jornadaActual = 1;
        }
    }

    /**
     * Dibuja los partidos en la pantalla usando el sistema Round Robin para que jueguen todos
     * contra todos. Si la jornada ya se jugó, la busca en la base de datos y la pinta en modo lectura.
     */
    private void generarEnfrentamientos() {
        vboxPartidos.getChildren().clear();

        int numEquipos = listaEquipos.size();
        if (numEquipos < 2) {
            return;
        }

        int totalJornadasVuelta = numEquipos - 1;
        int totalJornadasMaximas = 2 * totalJornadasVuelta;

        if (jornadaActual > totalJornadasMaximas) {
            Jornada.setText("COMPETICIÓN FINALIZADA");
            if (!listaEquipos.isEmpty()) {
                Equipo campeon = listaEquipos.get(0);
                Utils.mostrarMensaje("¡Tenemos Campeón!", "La liga ha concluido con éxito.\nEl campeón es: " + campeon.getNombre().toUpperCase());
            }

            Button btnReiniciar = new Button("REINICIAR LIGA");
            btnReiniciar.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 16;");
            btnReiniciar.setOnAction(e -> reiniciarCompeticion());
            vboxPartidos.setAlignment(Pos.CENTER);
            vboxPartidos.getChildren().add(btnReiniciar);
            return;
        }

        Jornada.setText("JORNADA " + jornadaActual);

        int partidosPorJornada = numEquipos / 2;
        int offsetPartidos = (jornadaActual - 1) * partidosPorJornada;
        List<HBox> partidosGuardados = obtenerPartidosPasadosDeBD(offsetPartidos, partidosPorJornada);

        if (!partidosGuardados.isEmpty()) {
            vboxPartidos.getChildren().addAll(partidosGuardados);
            return;
        }

        List<Equipo> copiaFijaCalendario = new ArrayList<>(listaEquipos);
        copiaFijaCalendario.sort((e1, e2) -> e1.getNombre().compareTo(e2.getNombre()));

        int jornadaEnVuelta = (jornadaActual - 1) % totalJornadasVuelta;
        boolean esSegundaVuelta = (jornadaActual - 1) / totalJornadasVuelta >= 1;

        for (int i = 0; i < partidosPorJornada; i++) {
            int localIdx = (jornadaEnVuelta + i) % (numEquipos - 1);
            int visitanteIdx = (numEquipos - 1 - i + jornadaEnVuelta) % (numEquipos - 1);

            if (i == 0) {
                visitanteIdx = numEquipos - 1;
            }

            Equipo local = copiaFijaCalendario.get(localIdx);
            Equipo visitante = copiaFijaCalendario.get(visitanteIdx);

            if (esSegundaVuelta) {
                Equipo temporal = local;
                local = visitante;
                visitante = temporal;
            }

            HBox filaPartido = crearFilaPartidoInterfaz(local.getNombre(), visitante.getNombre(), "", "");
            vboxPartidos.getChildren().add(filaPartido);
        }
    }

    /**
     * Crea una fila visual con los nombres de los dos equipos y los cuadros de texto
     * donde van los goles para meterlos dentro del contenedor de la interfaz.
     */
    private HBox crearFilaPartidoInterfaz(String local, String visitante, String golesL, String golesV) {
        HBox filaPartido = new HBox(10);
        filaPartido.setAlignment(Pos.CENTER);

        Label lblLocal = new Label(local);
        lblLocal.setPrefWidth(120);
        lblLocal.setAlignment(Pos.CENTER_RIGHT);

        TextField resLocal = new TextField(golesL);
        resLocal.setPrefWidth(40);
        resLocal.setPromptText("-");
        resLocal.setEditable(false);

        Label separador = new Label("vs");

        TextField resVis = new TextField(golesV);
        resVis.setPrefWidth(40);
        resVis.setPromptText("-");
        resVis.setEditable(false);

        Label lblVis = new Label(visitante);
        lblVis.setPrefWidth(120);

        filaPartido.getChildren().addAll(lblLocal, resLocal, separador, resVis, lblVis);
        return filaPartido;
    }

    /**
     * Va a la base de datos a buscar los partidos que ya se jugaron anteriormente
     * usando límites y saltos en la consulta SQL según la jornada por la que vayamos.
     */
    private List<HBox> obtenerPartidosPasadosDeBD(int offset, int limite) {
        List<HBox> filas = new ArrayList<>();
        String sql = "SELECT p.id_partido, p.goles_local, p.goles_visitante, " +
                "       (SELECT equipo_nombre FROM Juega j WHERE j.id_partido = p.id_partido LIMIT 1) as loc, " +
                "       (SELECT equipo_nombre FROM Juega j WHERE j.id_partido = p.id_partido LIMIT 1, 1) as vis " +
                "FROM Partido p " +
                "WHERE p.competicion_nombre = ? " +
                "ORDER BY p.id_partido ASC " +
                "LIMIT ? OFFSET ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, competicionActual.getNombre());
            ps.setInt(2, limite);
            ps.setInt(3, offset);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String loc = rs.getString("loc");
                    String vis = rs.getString("vis");
                    String gL = String.valueOf(rs.getInt("goles_local"));
                    String gV = String.valueOf(rs.getInt("goles_visitante"));

                    filas.add(crearFilaPartidoInterfaz(loc, vis, gL, gV));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filas;
    }

    /**
     * Borra todos los partidos de la liga en la base de datos mediante una transacción,
     * pone a cero las estadísticas de los equipos en la memoria y vuelve a la jornada 1.
     */
    private void reiniciarCompeticion() {
        String sqlBorrarJuega = "DELETE FROM Juega WHERE id_partido IN (SELECT id_partido FROM Partido WHERE competicion_nombre = ?)";
        String sqlBorrarPartidos = "DELETE FROM Partido WHERE competicion_nombre = ?";

        try {
            con.setAutoCommit(false);

            try (PreparedStatement psJ = con.prepareStatement(sqlBorrarJuega)) {
                psJ.setString(1, competicionActual.getNombre());
                psJ.executeUpdate();
            }

            try (PreparedStatement psP = con.prepareStatement(sqlBorrarPartidos)) {
                psP.setString(1, competicionActual.getNombre());
                psP.executeUpdate();
            }

            con.commit();
            con.setAutoCommit(true);

            Utils.mostrarMensaje("Reinicio Completado", "Los partidos anteriores de esta competición han sido eliminados.");

            for (Equipo e : listaEquipos) {
                e.setVictorias(0);
                e.setEmpates(0);
                e.setDerrotas(0);
            }

            jornadaActual = 1;
            listaEquipos.sort((e1, e2) -> Integer.compare(e2.getPuntos(), e1.getPuntos()));
            tablaClasificacion.refresh();

            vboxPartidos.getChildren().clear();
            generarEnfrentamientos();

        } catch (SQLException e) {
            try { con.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            e.printStackTrace();
            Utils.mostrarAlerta("Error", "No se pudo reiniciar la competición.");
        }
    }

    /**
     * Saca goles aleatorios para los partidos vacíos que hay en pantalla, los mete
     * en la base de datos y actualiza los puntos de la clasificación al momento.
     */
    @FXML
    private void simularJornada(ActionEvent event) {
        Random rand = new Random();
        boolean seSimuloAlgo = false;

        for (Node node : vboxPartidos.getChildren()) {
            if (!(node instanceof HBox)) continue;

            HBox fila = (HBox) node;
            Label lLocal = (Label) fila.getChildren().get(0);
            TextField t1 = (TextField) fila.getChildren().get(1);
            TextField t2 = (TextField) fila.getChildren().get(3);
            Label lVis = (Label) fila.getChildren().get(4);

            if (!t1.getText().isEmpty()) continue;

            int golesLocal = rand.nextInt(5);
            int golesVis = rand.nextInt(5);

            t1.setText(String.valueOf(golesLocal));
            t2.setText(String.valueOf(golesVis));

            registrarPartidoEnBD(lLocal.getText(), lVis.getText(), golesLocal, golesVis);
            actualizarEstadisticas(lLocal.getText(), lVis.getText(), golesLocal, golesVis);
            seSimuloAlgo = true;
        }

        if (seSimuloAlgo) {
            listaEquipos.sort((e1, e2) -> Integer.compare(e2.getPuntos(), e1.getPuntos()));
            tablaClasificacion.refresh();
        }
    }

    /**
     * Guarda el resultado de un partido simulado metiendo los datos en la tabla Partido
     * y las uniones de qué equipos jugaron en la tabla intermedia Juega.
     */
    private void registrarPartidoEnBD(String local, String visitante, int gL, int gV) {
        String sqlPartido = "INSERT INTO Partido (fecha, goles_local, goles_visitante, competicion_nombre) VALUES (CURDATE(), ?, ?, ?)";
        String sqlJuega = "INSERT INTO Juega (equipo_nombre, id_partido) VALUES (?, ?)";

        try {
            PreparedStatement psP = con.prepareStatement(sqlPartido, Statement.RETURN_GENERATED_KEYS);
            psP.setInt(1, gL);
            psP.setInt(2, gV);
            psP.setString(3, competicionActual.getNombre());
            psP.executeUpdate();

            ResultSet rsKeys = psP.getGeneratedKeys();
            if (rsKeys.next()) {
                int idPartido = rsKeys.getInt(1);

                PreparedStatement psJLocal = con.prepareStatement(sqlJuega);
                psJLocal.setString(1, local);
                psJLocal.setInt(2, idPartido);
                psJLocal.executeUpdate();

                PreparedStatement psJVis = con.prepareStatement(sqlJuega);
                psJVis.setString(1, visitante);
                psJVis.setInt(2, idPartido);
                psJVis.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Compara los goles del partido simulado para sumarle los partidos ganados,
     * empatados o perdidos a los objetos de los dos equipos que acaban de jugar.
     */
    private void actualizarEstadisticas(String localNom, String visNom, int gL, int gV) {
        Equipo local = listaEquipos.stream().filter(e -> e.getNombre().equals(localNom)).findFirst().get();
        Equipo vis = listaEquipos.stream().filter(e -> e.getNombre().equals(visNom)).findFirst().get();

        if (gL > gV) {
            local.setVictorias(local.getVictorias() + 1);
            vis.setDerrotas(vis.getDerrotas() + 1);
        } else if (gL < gV) {
            vis.setVictorias(vis.getVictorias() + 1);
            local.setDerrotas(local.getDerrotas() + 1);
        } else {
            local.setEmpates(local.getEmpates() + 1);
            vis.setEmpates(vis.getEmpates() + 1);
        }
    }

    /**
     * Resta uno al contador de la jornada para volver atrás y ver qué partidos se jugaron
     * en las semanas anteriores de la competición.
     */
    @FXML
    private void anteriorJornada(ActionEvent event) {
        if (jornadaActual > 1) {
            jornadaActual--;
            generarEnfrentamientos();
        } else {
            Utils.mostrarAlerta("Línea temporal", "Ya estás en la Jornada 1. No puedes retroceder más.");
        }
    }

    /**
     * Suma uno a la jornada para avanzar, pero antes mira en la base de datos que hayamos
     * simulado obligatoriamente los partidos de la jornada actual para no dejar huecos vacíos.
     */
    @FXML
    private void siguienteJornada(ActionEvent event) {
        String sqlContar = "SELECT COUNT(*) FROM Partido WHERE competicion_nombre = ?";
        int partidosTotalesJugados = 0;
        try (PreparedStatement ps = con.prepareStatement(sqlContar)) {
            ps.setString(1, competicionActual.getNombre());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) partidosTotalesJugados = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        int partidosPorJornada = listaEquipos.size() / 2;
        int maximaJornadaSimulada = partidosTotalesJugados / partidosPorJornada;

        if (jornadaActual <= maximaJornadaSimulada) {
            jornadaActual++;
            generarEnfrentamientos();
        } else {
            Utils.mostrarAlerta("Simulación pendiente", "Debes simular la jornada actual en pantalla antes de avanzar a una nueva.");
        }
    }

    /**
     * Coge el equipo que tengamos seleccionado en la tabla y abre su pantalla de detalles
     * (la ficha técnica) pasándole la información que necesita.
     */
    @FXML
    private void irAFichaEquipo(ActionEvent event) {
        Equipo seleccionado = tablaClasificacion.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            Utils.mostrarAlerta("Selección", "Por favor, selecciona un equipo de la tabla.");
            return;
        }

        try {
            Scene escenaClasificacionAnterior = tablaClasificacion.getScene();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/leaguemanager/ficha-equipo.fxml"));
            Parent root = loader.load();

            FichaEquipoController controller = loader.getController();
            controller.init(seleccionado.getNombre(), competicionActual, escenaClasificacionAnterior);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Ficha del Equipo - " + seleccionado.getNombre());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Utils.mostrarAlerta("Error", "No se pudo abrir la ficha del equipo.");
        }
    }

    /**
     * Muestra un mensaje avisando de que todo está guardado correctamente y nos manda
     * de vuelta al menú de inicio del programa.
     */
    @FXML
    private void guardarYSalir(ActionEvent event) {
        Utils.mostrarMensaje("Guardado Exitoso", "Todos los partidos simulados se han registrado correctamente en la Base de Datos.");

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/leaguemanager/principal.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("League Manager - Inicio");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}