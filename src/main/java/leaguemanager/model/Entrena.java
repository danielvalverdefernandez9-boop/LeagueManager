package leaguemanager.model;

public class Entrena {

    private Entrenador entrenador;

    private Equipo equipo;

    private String temporada;

    public Entrena() {
    }

    public Entrena(leaguemanager.model.Entrenador entrenador, Equipo equipo, String temporada) {

        this.entrenador = entrenador;
        this.equipo = equipo;
        this.temporada = temporada;
    }

    // GETTERS Y SETTERS

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
}
