package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Equipo {

    private String nombre;
    private String ciudad;
    private String estadio;
    private LocalDate fecha_fundacion;

    private List<Jugador> jugadores;

    public Equipo() {
        jugadores = new ArrayList<>();
    }

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

    // GETTERS Y SETTERS

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

    public List<Jugador> getJugadores() {
        return jugadores;
    }

    public void setJugadores(List<Jugador> jugadores) {
        this.jugadores = jugadores;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
