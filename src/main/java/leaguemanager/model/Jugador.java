package leaguemanager.model;

import java.util.Objects;

/**
 * Clase que representa a un Jugador en el sistema.
 * Hereda los atributos básicos de Persona e implementa la interfaz Validable
 * para asegurar restricciones en sus datos deportivos.
 */
public class Jugador extends Persona implements Validable {

    private String posicion;
    private int dorsal;

    private Equipo equipo;

    /**
     * Constructor vacío por defecto.
     */
    public Jugador() {}

    /**
     * Constructor completo que inicializa los atributos heredados de Persona
     * y los campos específicos del jugador.
     */
    public Jugador(String dni, String nombre, int edad, String posicion, int dorsal, Equipo equipo) {
        super(dni, nombre, edad);
        this.posicion = posicion;
        this.dorsal = dorsal;
        this.equipo = equipo;
    }

    /**
     * Método sobreescrito de la interfaz Validable.
     * Comprueba que el dorsal asignado esté dentro del rango reglamentario (1 al 99).
     */
    @Override
    public boolean validarDatos() {
        return dorsal > 0 && dorsal <= 99;
    }

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

    /**
     * Método para mostrar los datos del jugador de forma resumida en formato de texto.
     */
    @Override
    public String toString() {
        return nombre + " - " + posicion;
    }

    /**
     * Comparación de objetos Jugador basándose en sus atributos específicos.
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Jugador jugador = (Jugador) o;
        return dorsal == jugador.dorsal &&
                Objects.equals(posicion, jugador.posicion) &&
                Objects.equals(equipo, jugador.equipo);
    }

    /**
     * Generación del código hash único basado en los datos deportivos del jugador.
     */
    @Override
    public int hashCode() {
        return Objects.hash(posicion, dorsal, equipo);
    }
}