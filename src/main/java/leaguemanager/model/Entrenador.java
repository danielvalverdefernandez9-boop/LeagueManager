package leaguemanager.model;

/**
 * Clase que representa a un Entrenador dentro del sistema.
 * Hereda todos los atributos y comportamientos básicos de la clase Persona.
 */
public class Entrenador extends Persona {

    /**
     * Constructor vacío por defecto.
     */
    public Entrenador() {
    }

    /**
     * Constructor completo que delega la inicialización de los datos básicos
     * (DNI, nombre y edad) a la superclase Persona.
     */
    public Entrenador(String dni, String nombre, int edad) {
        super(dni, nombre, edad);
    }

    /**
     * Método para mostrar los datos del entrenador en formato de texto.
     */
    @Override
    public String toString() {
        return nombre + " - Entrenador";
    }
}