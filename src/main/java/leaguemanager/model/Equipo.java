package leaguemanager.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Clase que representa un Equipo dentro del sistema LeagueManager.
 * Almacena los datos del club, su plantilla de jugadores y las estadísticas de la temporada.
 */
public class Equipo {

    private String nombre;
    private String ciudad;
    private String estadio;
    private LocalDate fecha_fundacion;

    private List<Jugador> jugadores;

    private int victorias;
    private int empates;
    private int derrotas;

    /**
     * Constructor vacío por defecto.
     */
    public Equipo() {
        jugadores = new ArrayList<>();
    }

    /**
     * Constructor con los parámetros fundamentales de creación de un club.
     */
    public Equipo(String nombre,
                  String ciudad,
                  String estadio,
                  LocalDate fechaFundacion) {

        this.nombre = nombre;
        this.ciudad = ciudad;
        this.estadio = estadio;
        this.fecha_fundacion = fechaFundacion;
        jugadores = new ArrayList<>();
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getEstadio() {
        return estadio;
    }

    public void setEstadio(String estadio) {
        this.estadio = estadio;
    }

    public LocalDate getFecha_fundacion() {
        return fecha_fundacion;
    }

    public void setFecha_fundacion(LocalDate fecha_fundacion) {
        this.fecha_fundacion = fecha_fundacion;
    }

    public List<leaguemanager.model.Jugador> getJugadores() {
        return jugadores;
    }

    public void setJugadores(List<leaguemanager.model.Jugador> jugadores) {
        this.jugadores = jugadores;
    }


    public int getVictorias() {
        return victorias;
    }

    public void setVictorias(int victorias) {
        this.victorias = victorias;
    }

    public int getEmpates() {
        return empates;
    }

    public void setEmpates(int empates) {
        this.empates = empates;
    }

    public int getDerrotas() {
        return derrotas;
    }

    public void setDerrotas(int derrotas) {
        this.derrotas = derrotas;
    }

    /**
     * Calcula dinámicamente los puntos totales del equipo en la liga.
     * Otorga 3 puntos por victoria y 1 por empate.
     */
    public int getPuntos() {
        return (this.victorias * 3) + this.empates;
    }

    /**
     * Método para mostrar el nombre del equipo en formato texto.
     */
    @Override
    public String toString() {
        return nombre;
    }

    /**
     * Comparación de objetos Equipo basándose en sus atributos de identidad y plantilla.
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Equipo equipo = (Equipo) o;
        return Objects.equals(nombre, equipo.nombre) &&
                Objects.equals(ciudad, equipo.ciudad) &&
                Objects.equals(estadio, equipo.estadio) &&
                Objects.equals(fecha_fundacion, equipo.fecha_fundacion) &&
                Objects.equals(jugadores, equipo.jugadores);
    }

    /**
     * Generación del código hash único para la indexación y colecciones de equipos.
     */
    @Override
    public int hashCode() {
        return Objects.hash(nombre, ciudad, estadio, fecha_fundacion, jugadores);
    }
}