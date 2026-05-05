package model;

public class Competicion {

    private String nombre;
    private int numero_equipos;

    public Competicion() {
    }

    public Competicion(String nombre, int numeroEquipos) {
        this.nombre = nombre;
        this.numero_equipos = numeroEquipos;
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

    @Override
    public String toString() {
        return nombre;
    }
}