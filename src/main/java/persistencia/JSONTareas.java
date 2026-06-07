/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

/**
 *
 * @author ehuan
 */
import estructuras.Cola;
import estructuras.NodoCola;
import modelo.Tarea;
import org.json.JSONArray;
import org.json.JSONObject;

public class JSONTareas {

    private static final String RUTA = "src/main/resources/tareas.json";

    public static Cola cargarTareas() {
        Cola cola = new Cola();
        String contenido = JSONReader.leerArchivo(RUTA);

        if (contenido.isEmpty())
            return cola;

        JSONArray arr = new JSONArray(contenido);

        for (int i = 0; i < arr.length(); i++) {

            JSONObject obj = arr.getJSONObject(i);

            Tarea t = new Tarea(
                obj.getInt("idTarea"),
                obj.getInt("idPedido"),
                obj.getString("descripcion"),
                obj.getString("fechaCreacion"),
                obj.getString("estado")
            );

            cola.encolar(t);
        }

        return cola;
    }

    public static void guardarTareas(Cola cola) {
        JSONArray arr = new JSONArray();

        NodoCola actual = cola.getFrenteNodo();

        while (actual != null) {
            Tarea t = actual.dato;

            JSONObject obj = new JSONObject();
            obj.put("idTarea", t.getIdTarea());
            obj.put("idPedido", t.getIdPedido());
            obj.put("descripcion", t.getDescripcion());
            obj.put("fechaCreacion", t.getFechaCreacion());
            obj.put("estado", t.getEstado());

            arr.put(obj);
            actual = actual.siguiente;
        }

        JSONWriter.escribirArchivo(RUTA, arr.toString(4));
    }
}
