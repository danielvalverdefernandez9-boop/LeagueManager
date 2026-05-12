package leaguemanager.model;

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
}