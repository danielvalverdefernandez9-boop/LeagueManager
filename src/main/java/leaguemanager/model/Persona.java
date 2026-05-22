package leaguemanager.model;

/**
 * Clase abstracta que representa la entidad Persona en el sistema.
 */
public abstract class Persona {

    protected String dni;
    protected String nombre;
    protected int edad;

    /**
     * Constructor vacío por defecto.
     */
    public Persona() {
    }

    /**
     * Constructor completo para inicializar los datos base de la persona.
     */
    public Persona(String dni, String nombre, int edad) {
        this.dni = dni;
        this.nombre = nombre;
        this.edad = edad;
    }


    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }
}