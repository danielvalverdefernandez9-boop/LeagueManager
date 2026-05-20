package leaguemanager.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Competicion {

    private String nombre;
    private int numero_equipos;
    private String temporada;

    private List<Equipo> equipos = new ArrayList<>();

    public Competicion() {
    }

    public Competicion(String nombre, int numeroEquipos, String temporada) {
        this.nombre = nombre;
        this.numero_equipos = numeroEquipos;
        this.temporada = temporada;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumero_equipos() {
        return numero_equipos;
    }

    public void setNumero_equipos(int numero_equipos) {
        this.numero_equipos = numero_equipos;
    }

    public String getTemporada() {
        return temporada;
    }

    public void setTemporada(String temporada) {
        this.temporada = temporada;
    }

    public List<Equipo> getEquipos() {
        return equipos;
    }

    // NUEVO: Setter para que el DAO pueda inyectar los equipos de la BD
    public void setEquipos(List<Equipo> equipos) {
        this.equipos = equipos;
    }

    @Override
    public String toString() {
        return nombre + " - " + numero_equipos + " - " + temporada;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Competicion that = (Competicion) o;
        return numero_equipos == that.numero_equipos &&
                Objects.equals(nombre, that.nombre) &&
                Objects.equals(temporada, that.temporada) &&
                Objects.equals(equipos, that.equipos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, numero_equipos, temporada, equipos);
    }
}