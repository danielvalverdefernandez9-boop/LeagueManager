package leaguemanager.model;

import java.util.Objects;

/**
 * Clase asociativa que representa la relación entre un Entrenador y un Equipo.
 * Permite registrar qué equipo dirige cada técnico especificando la temporada concreta.
 */
public class Entrena {

    private Entrenador entrenador;
    private Equipo equipo;
    private String temporada;

    /**
     * Constructor vacío por defecto.
     */
    public Entrena() {
    }

    /**
     * Constructor completo para inicializar el vínculo contractual de un entrenador con un club.
     */
    public Entrena(leaguemanager.model.Entrenador entrenador, Equipo equipo, String temporada) {
        this.entrenador = entrenador;
        this.equipo = equipo;
        this.temporada = temporada;
    }


    public leaguemanager.model.Entrenador getEntrenador() {
        return entrenador;
    }

    public void setEntrenador(leaguemanager.model.Entrenador entrenador) {
        this.entrenador = entrenador;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    public String getTemporada() {
        return temporada;
    }

    public void setTemporada(String temporada) {
        this.temporada = temporada;
    }

    /**
     * Comparación de objetos Entrena basándose en el entrenador, el equipo y la temporada.
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Entrena entrena = (Entrena) o;
        return Objects.equals(entrenador, entrena.entrenador) &&
                Objects.equals(equipo, entrena.equipo) &&
                Objects.equals(temporada, entrena.temporada);
    }

    /**
     * Generación del código hash único combinando el técnico, el club y el año correspondiente.
     */
    @Override
    public int hashCode() {
        return Objects.hash(entrenador, equipo, temporada);
    }
}