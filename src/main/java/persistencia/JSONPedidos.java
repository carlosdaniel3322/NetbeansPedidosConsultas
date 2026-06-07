/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

/**
 *
 * @author ehuan
 */
import estructuras.ArbolABB;
import estructuras.NodoArbol;
import tads.ListaDoble;
import modelo.Nodo;
import modelo.Pedido;
import modelo.Producto;
import org.json.JSONArray;
import org.json.JSONObject;

public class JSONPedidos {

    private static final String RUTA = "src/main/resources/pedidos.json";

    // CARGAR DESDE JSON - ARBOL
    public static ArbolABB<Pedido> cargarPedidos() {

        ArbolABB<Pedido> arbol = new ArbolABB<>();

        String contenido = JSONReader.leerArchivo(RUTA);
        if (contenido.isEmpty()) return arbol;

        JSONArray arreglo = new JSONArray(contenido);

        for (int i = 0; i < arreglo.length(); i++) {

            JSONObject obj = arreglo.getJSONObject(i);

            int id = obj.getInt("id");
            String fecha = obj.getString("fecha");
            String cliente = obj.getString("cliente");
            String estado = obj.getString("estado");

            // Lista de productos
            JSONArray arrProds = obj.getJSONArray("productos");
            ListaDoble productos = new ListaDoble();

            for (int j = 0; j < arrProds.length(); j++) {
                JSONObject prodJSON = arrProds.getJSONObject(j);

                Producto p = new Producto(
                        prodJSON.getInt("id"),
                        prodJSON.getString("nombre"),
                        prodJSON.getString("descripcion"),
                        prodJSON.getDouble("precio")
                );

                productos.insertar(p);
            }

            Pedido pedido = new Pedido(id, fecha, cliente, productos, estado);
            arbol.insertar(id, pedido);
        }

        return arbol;
    }

    // GUARDAR ARBOL - JSON
    public static void guardarPedidos(ArbolABB<Pedido> arbol) {

        JSONArray arreglo = new JSONArray();

        recorrerGuardar(arbol.getRaiz(), arreglo);

        JSONWriter.escribirArchivo(RUTA, arreglo.toString(4));
    }

    private static void recorrerGuardar(NodoArbol<Pedido> nodo, JSONArray arreglo) {
        if (nodo == null) return;

        recorrerGuardar(nodo.izquierda, arreglo);

        Pedido p = nodo.dato;

        JSONObject obj = new JSONObject();
        obj.put("id", p.getId());
        obj.put("fecha", p.getFecha());
        obj.put("cliente", p.getCliente());
        obj.put("estado", p.getEstado());

        // Guardar productos
        JSONArray arrProds = new JSONArray();

        Nodo cursor = p.getProductos().getCabeza();
        while (cursor != null) {
            Producto prod = cursor.dato;

            JSONObject prodJSON = new JSONObject();
            prodJSON.put("id", prod.getId());
            prodJSON.put("nombre", prod.getNombre());
            prodJSON.put("descripcion", prod.getDescripcion());
            prodJSON.put("precio", prod.getPrecio());

            arrProds.put(prodJSON);

            cursor = cursor.siguiente;
        }

        obj.put("productos", arrProds);

        arreglo.put(obj);

        recorrerGuardar(nodo.derecha, arreglo);
    }
}