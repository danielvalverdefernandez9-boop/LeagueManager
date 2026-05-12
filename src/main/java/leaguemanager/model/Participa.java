package leaguemanager.model;

public class Participa {

    private Equipo equipo;

    private Competicion competicion;

    public Participa() {}

    public Participa(Equipo equipo, Competicion competicion) {

        this.equipo = equipo;
        this.competicion = competicion;
    }

    // GETTERS Y SETTERS

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
