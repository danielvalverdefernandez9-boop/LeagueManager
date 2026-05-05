package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Partido {
    private int id_partido;
    private LocalDate fecha;
    private int goles_local;
    private int goles_visitante;

    private Competicion competicion;

    private List<Equipo> equipos;

    public Partido() {
        equipos = new ArrayList<>();
    }

    public Partido(int id_partido, LocalDate fecha, int goles_local, int goles_visitante, Competicion competicion) {

        this.id_partido = id_partido;
        this.fecha = fecha;
        this.goles_local = goles_local;
        this.goles_visitante = goles_visitante;
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

    public List<Equipo> getEquipos() {
        return equipos;
    }

    public void setEquipos(List<Equipo> equipos) {
        this.equipos = equipos;
    }

    @Override
    public String toString() {
        return "Partido " + id_partido;
    }
}
