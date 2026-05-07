package DAO;

import java.util.List;

public interface DAO<T, ID> {

    boolean insertar(T obj);

    boolean actualizar(T obj);

    boolean eliminar(ID id);

    T buscarPorId(ID id);

    List<T> listarTodos();
}
