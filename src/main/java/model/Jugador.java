package model;

public class Jugador extends Persona implements Validable {

    private String posicion;
    private int dorsal;

    private Equipo equipo;

    public Jugador() {}

    public Jugador(String dni, String nombre, int edad, String posicion, int dorsal, Equipo equipo) {
        super(dni, nombre, edad);
        this.posicion = posicion;
        this.dorsal = dorsal;
        this.equipo = equipo;
    }

    @Override
    public boolean validarDatos() {
        return dorsal > 0 && dorsal <= 99;
    }

    // GETTERS Y SETTERS

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public int getDorsal() {
        return dorsal;
    }

    public void setDorsal(int dorsal) {
        this.dorsal = dorsal;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    @Override
    public String toString() {
        return nombre + " - " + posicion;
    }
}