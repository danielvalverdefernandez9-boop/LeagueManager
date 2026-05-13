package leaguemanager.model;

import java.util.Objects;

public class Competicion {

    private String nombre;
    private int numero_equipos;
    private String temporada;

    public Competicion() {
    }

    public Competicion(String nombre, int numeroEquipos, String temporada) {
        this.nombre = nombre;
        this.numero_equipos = numeroEquipos;
        this.temporada = temporada;
    }

    // GETTERS Y SETTERS

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

    @Override
    public String toString() {
        return nombre + " - " + numero_equipos+ " - " + temporada;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Competicion that = (Competicion) o;
        return numero_equipos == that.numero_equipos && Objects.equals(nombre, that.nombre) && Objects.equals(temporada, that.temporada);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, numero_equipos, temporada);
    }
}