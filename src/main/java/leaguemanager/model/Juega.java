package leaguemanager.model;

import java.util.Objects;

public class Juega {

    private Equipo equipo;

    private Partido partido;

    public Juega() {}

    public Juega(Equipo equipo, leaguemanager.model.Partido partido) {

        this.equipo = equipo;
        this.partido = partido;
    }

    // GETTERS Y SETTERS

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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Juega juega = (Juega) o;
        return Objects.equals(equipo, juega.equipo) && Objects.equals(partido, juega.partido);
    }

    @Override
    public int hashCode() {
        return Objects.hash(equipo, partido);
    }
}
