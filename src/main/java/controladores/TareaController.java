/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

/**
 *
 * @author ehuan
 */
import estructuras.Cola;
import estructuras.NodoCola;
import modelo.Tarea;
import persistencia.JSONTareas;

public class TareaController {

    private Cola colaTareas;
    private int contador;

    public TareaController() {
        colaTareas = JSONTareas.cargarTareas();
        contador = obtenerUltimoId();
    }

    private int obtenerUltimoId() {
        int max = 0;
        NodoCola actual = colaTareas.getFrenteNodo();

        while (actual != null) {
            if (actual.dato.getIdTarea() > max)
                max = actual.dato.getIdTarea();
            actual = actual.siguiente;
        }

        return max;
    }

    public void generarTarea(int idPedido, String descripcion, String fecha) {
        contador++;
        Tarea t = new Tarea(contador, idPedido, descripcion, fecha, "Pendiente");
        colaTareas.encolar(t);
        JSONTareas.guardarTareas(colaTareas);
    }

    public Tarea procesar() {
        Tarea t = colaTareas.desencolar();
        JSONTareas.guardarTareas(colaTareas);
        return t;
    }

    public Cola getCola() {
        return colaTareas;
    }
}

