package leaguemanager.model;

/**
 * Clase asociativa que representa la relación N:M entre Equipo y Competición.
 */
public class Participa {

    // --- Atributos que representan las entidades relacionadas ---
    private Equipo equipo;
    private Competicion competicion;

    /**
     * Constructor vacío por defecto.
     */
    public Participa() {}

    /**
     * Constructor completo para inicializar la relación de participación.
     */
    public Participa(Equipo equipo, Competicion competicion) {
        this.equipo = equipo;
        this.competicion = competicion;
    }


    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    public Competicion getCompeticion() {
        return competicion;
    }

    public void setCompeticion(Competicion competicion) {
        this.competicion = competicion;
    }
}