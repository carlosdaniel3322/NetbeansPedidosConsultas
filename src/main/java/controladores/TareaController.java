package controladores;

import com.google.common.base.Preconditions;
import estructuras.Cola;
import estructuras.NodoCola;
import modelo.Tarea;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistencia.JSONTareas;

public class TareaController {

    private static final Logger LOG =
            LoggerFactory.getLogger(TareaController.class);

    private Cola colaTareas;
    private int contador;

    public TareaController() {

        colaTareas = JSONTareas.cargarTareas();

        contador = obtenerUltimoId();

        LOG.info(
                "Tareas cargadas correctamente");
    }

    private int obtenerUltimoId() {

        int max = 0;

        NodoCola actual =
                colaTareas.getFrenteNodo();

        while (actual != null) {

            if (actual.dato.getIdTarea() > max)
                max = actual.dato.getIdTarea();

            actual = actual.siguiente;
        }

        return max;
    }

    public void generarTarea(
            int idPedido,
            String descripcion,
            String fecha) {

        try {

            Preconditions.checkArgument(
                    idPedido > 0,
                    "ID de pedido inválido");

            if (StringUtils.isBlank(descripcion))
                return;

            if (StringUtils.isBlank(fecha))
                return;

            contador++;

            Tarea t =
                    new Tarea(
                            contador,
                            idPedido,
                            descripcion,
                            fecha,
                            "Pendiente");

            colaTareas.encolar(t);

            JSONTareas.guardarTareas(
                    colaTareas);

            LOG.info(
                    "Tarea creada {}",
                    contador);

        } catch (Exception e) {

            LOG.error(
                    "Error creando tarea",
                    e);
        }
    }

    public Tarea procesar() {

        try {

            Tarea t =
                    colaTareas.desencolar();

            JSONTareas.guardarTareas(
                    colaTareas);

            LOG.info(
                    "Tarea procesada");

            return t;

        } catch (Exception e) {

            LOG.error(
                    "Error procesando tarea",
                    e);

            return null;
        }
    }

    public Cola getCola() {
        return colaTareas;
    }
}