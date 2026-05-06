package DAO;

import java.util.List;

/**
 * Interfaz genérica para el CRUD
 * @param <T> El tipo de objeto (Equipo, Jugador, etc.)
 */
public interface DAO<T> {
    boolean insertar(T objeto);
    boolean actualizar(T objeto);
    boolean eliminar(T objeto);
    T buscarPorId(Object id);
    List<T> listarTodos();
}
