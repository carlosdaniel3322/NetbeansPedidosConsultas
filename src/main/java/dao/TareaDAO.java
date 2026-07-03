package dao;

import java.util.List;
import modelo.Tarea;

public interface TareaDAO {
    boolean insertar(Tarea tarea);
    List<Tarea> listar();
    Tarea obtenerPrimera();
    boolean eliminar(int idTarea);
    int obtenerSiguienteId();
}
