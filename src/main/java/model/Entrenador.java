package model;

public class Entrenador extends Persona {

    public Entrenador() {
    }

    public Entrenador(String dni, String nombre, int edad) {
        super(dni, nombre, edad);
    }

    @Override
    public String toString() {
        return nombre + " - Entrenador";
    }
}
