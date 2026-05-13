package leaguemanager.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Partido {

    private int id_partido;
    private LocalDate fecha;
    private int goles_local;
    private int goles_visitante;

    private Competicion competicion;

    // NUEVOS ATRIBUTOS
    private Equipo equipoLocal;
    private Equipo equipoVisitante;

    private List<Equipo> equipos;

    public Partido() {
        equipos = new ArrayList<>();
    }

    // CONSTRUCTOR COMPLETO
    public Partido(int id_partido,
                   LocalDate fecha,
                   int goles_local,
                   int goles_visitante,
                   Equipo equipoLocal,
                   Equipo equipoVisitante,
                   Competicion competicion) {

        this.id_partido = id_partido;
        this.fecha = fecha;
        this.goles_local = goles_local;
        this.goles_visitante = goles_visitante;

        this.equipoLocal = equipoLocal;
        this.equipoVisitante = equipoVisitante;

        this.competicion = competicion;

        equipos = new ArrayList<>();
    }

    // GETTERS Y SETTERS

    public int getId_partido() {
        return id_partido;
    }

    public void setId_partido(int id_partido) {
        this.id_partido = id_partido;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public int getGoles_local() {
        return goles_local;
    }

    public void setGoles_local(int goles_local) {
        this.goles_local = goles_local;
    }

    public int getGoles_visitante() {
        return goles_visitante;
    }

    public void setGoles_visitante(int goles_visitante) {
        this.goles_visitante = goles_visitante;
    }

    public Competicion getCompeticion() {
        return competicion;
    }

    public void setCompeticion(Competicion competicion) {
        this.competicion = competicion;
    }

    public Equipo getEquipoLocal() {
        return equipoLocal;
    }

    public void setEquipoLocal(Equipo equipoLocal) {
        this.equipoLocal = equipoLocal;
    }

    public Equipo getEquipoVisitante() {
        return equipoVisitante;
    }

    public void setEquipoVisitante(Equipo equipoVisitante) {
        this.equipoVisitante = equipoVisitante;
    }

    public List<Equipo> getEquipos() {
        return equipos;
    }

    public void setEquipos(List<Equipo> equipos) {
        this.equipos = equipos;
    }

    @Override
    public String toString() {

        String local = (equipoLocal != null) ? equipoLocal.getNombre() : "Local";
        String visitante = (equipoVisitante != null) ? equipoVisitante.getNombre() : "Visitante";

        return local + " " +
                goles_local +
                "-" +
                goles_visitante +
                " " +
                visitante +
                " [" +
                competicion.getNombre() +
                "]";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Partido partido = (Partido) o;
        return id_partido == partido.id_partido && goles_local == partido.goles_local && goles_visitante == partido.goles_visitante && Objects.equals(fecha, partido.fecha) && Objects.equals(competicion, partido.competicion) && Objects.equals(equipoLocal, partido.equipoLocal) && Objects.equals(equipoVisitante, partido.equipoVisitante) && Objects.equals(equipos, partido.equipos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_partido, fecha, goles_local, goles_visitante, competicion, equipoLocal, equipoVisitante, equipos);
    }
}
