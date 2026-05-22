package leaguemanager.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Clase que representa una Competición.
 * Guarda las propiedades del torneo y la lista de los equipos inscritos en él.
 */
public class Competicion {

    private String nombre;
    private int numero_equipos;
    private String temporada;

    private List<Equipo> equipos = new ArrayList<>();

    /**
     * Constructor vacío por defecto.
     */
    public Competicion() {
    }

    /**
     * Constructor completo para inicializar las propiedades básicas de la competición.
     */
    public Competicion(String nombre, int numeroEquipos, String temporada) {
        this.nombre = nombre;
        this.numero_equipos = numeroEquipos;
        this.temporada = temporada;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumero_equipos() {
        return numero_equipos;
    }

    public void setNumero_equipos(int numero_equipos) {
        this.numero_equipos = numero_equipos;
    }

    public String getTemporada() {
        return temporada;
    }

    public void setTemporada(String temporada) {
        this.temporada = temporada;
    }

    public List<Equipo> getEquipos() {
        return equipos;
    }

    /**
     * Método Setter que permite al DAO inyectar la lista de equipos correspondientes
     * recuperados desde la base de datos.
     */
    public void setEquipos(List<Equipo> equipos) {
        this.equipos = equipos;
    }

    /**
     * Método para mostrar los datos clave de la competición en formato de texto.
     */
    @Override
    public String toString() {
        return nombre + " - " + numero_equipos + " - " + temporada;
    }

    /**
     * Comparación de objetos Competicion analizando la identidad y la igualdad de su lista de participantes.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Competicion that = (Competicion) o;
        return numero_equipos == that.numero_equipos &&
                Objects.equals(nombre, that.nombre) &&
                Objects.equals(temporada, that.temporada) &&
                Objects.equals(equipos, that.equipos);
    }

    /**
     * Generación del código hash único vinculando los atributos base y los equipos asignados.
     */
    @Override
    public int hashCode() {
        return Objects.hash(nombre, numero_equipos, temporada, equipos);
    }
}