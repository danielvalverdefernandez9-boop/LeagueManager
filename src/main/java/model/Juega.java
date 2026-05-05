package model;

public class Juega {

    private Equipo equipo;

    private Partido partido;

    public Juega() {}

    public Juega(Equipo equipo, Partido partido) {

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

    public Partido getPartido() {
        return partido;
    }

    public void setPartido(Partido partido) {
        this.partido = partido;
    }
}
