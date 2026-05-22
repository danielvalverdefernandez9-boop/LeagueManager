package leaguemanager.model;

import java.util.Objects;

/**
 * Clase asociativa que representa la relación entre un Equipo y un Partido.
 * Permite controlar qué equipos intervienen en cada uno de los encuentros programados.
 */
public class Juega {

    private Equipo equipo;
    private Partido partido;

    /**
     * Constructor vacío por defecto.
     */
    public Juega() {}

    /**
     * Constructor completo para inicializar la relación de juego entre un equipo y un partido.
     */
    public Juega(Equipo equipo, leaguemanager.model.Partido partido) {
        this.equipo = equipo;
        this.partido = partido;
    }


    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    public leaguemanager.model.Partido getPartido() {
        return partido;
    }

    public void setPartido(leaguemanager.model.Partido partido) {
        this.partido = partido;
    }

    /**
     * Comparación de objetos Juega basándose en la coincidencia de equipo y partido.
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Juega juega = (Juega) o;
        return Objects.equals(equipo, juega.equipo) && Objects.equals(partido, juega.partido);
    }

    /**
     * Generación del código hash único combinando las referencias de equipo y partido.
     */
    @Override
    public int hashCode() {
        return Objects.hash(equipo, partido);
    }
}