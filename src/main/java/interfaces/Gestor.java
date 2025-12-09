package interfaces;

import java.util.List;

public interface Gestor<T> {
    void agregar(T entidad);

    List<T> listarTodos();

    void eliminar(int index);
}
