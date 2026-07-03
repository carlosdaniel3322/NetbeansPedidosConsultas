package controladores;

import com.google.common.base.Preconditions;
import dao.TareaDAO;
import dao.impl.TareaDAOImpl;
import estructuras.Cola;
import java.util.List;
import modelo.Tarea;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TareaController {

    private static final Logger LOG = LoggerFactory.getLogger(TareaController.class);

    private final TareaDAO tareaDAO;
    private Cola colaTareas;

    public TareaController() {
        tareaDAO = new TareaDAOImpl();
        recargarCola();
        LOG.info("Tareas cargadas desde PostgreSQL");
    }

    public void generarTarea(int idPedido, String descripcion, String fecha) {
        try {
            Preconditions.checkArgument(idPedido > 0, "ID de pedido invalido");

            if (StringUtils.isBlank(descripcion) || StringUtils.isBlank(fecha)) {
                return;
            }

            int idTarea = tareaDAO.obtenerSiguienteId();
            Tarea t = new Tarea(idTarea, idPedido, descripcion, fecha, "Pendiente");
            if (tareaDAO.insertar(t)) {
                recargarCola();
                LOG.info("Tarea creada {}", idTarea);
            }
        } catch (Exception e) {
            LOG.error("Error creando tarea", e);
        }
    }

    public Tarea procesar() {
        try {
            Tarea t = tareaDAO.obtenerPrimera();
            if (t == null) {
                return null;
            }

            tareaDAO.eliminar(t.getIdTarea());
            recargarCola();
            LOG.info("Tarea procesada");
            return t;
        } catch (Exception e) {
            LOG.error("Error procesando tarea", e);
            return null;
        }
    }

    public Cola getCola() {
        return colaTareas;
    }

    private void recargarCola() {
        colaTareas = new Cola();
        List<Tarea> tareas = tareaDAO.listar();
        for (Tarea tarea : tareas) {
            colaTareas.encolar(tarea);
        }
    }
}
