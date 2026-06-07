/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

/**
 *
 * @author ehuan
 */
import estructuras.ArbolABB;
import estructuras.NodoArbol;
import modelo.Pedido;
import modelo.Producto;
import persistencia.JSONPedidos;
import tads.ListaDoble;
import modelo.Nodo;

public class PedidoController {

    private ArbolABB<Pedido> arbol;

    public PedidoController() {
        arbol = JSONPedidos.cargarPedidos();
    }

    // REGISTRAR PEDIDO
    public boolean registrarPedido(int id, String fecha, String cliente, ListaDoble productos, String estado) {

        if (arbol.buscar(id) != null) {
            return false;
        }

        Pedido nuevo = new Pedido(id, fecha, cliente, productos, estado);
        arbol.insertar(id, nuevo);

        JSONPedidos.guardarPedidos(arbol);
        return true;
    }

    // BUSCAR
    public Pedido buscarPedido(int id) {
        return arbol.buscar(id);
    }

    // ELIMINAR
    public boolean eliminarPedido(int id) {

        if (arbol.buscar(id) == null)
            return false;

        arbol.eliminar(id);
        JSONPedidos.guardarPedidos(arbol);
        return true;
    }

    // GENERAR MATRIZ PARA LA TABLA DEL JTABLE (SIN ARRAYLIST)

    public Object[][] obtenerPedidosComoMatriz() {
        int total = contarNodos(arbol.getRaiz());
        Object[][] matriz = new Object[total][4]; // id, fecha, cliente, estado
        indiceTabla = 0;
        llenarMatriz(arbol.getRaiz(), matriz);
        return matriz;
    }

    private int contarNodos(NodoArbol<Pedido> nodo) {
        if (nodo == null) return 0;
        return 1 + contarNodos(nodo.izquierda) + contarNodos(nodo.derecha);
    }

    private int indiceTabla = 0;

    private void llenarMatriz(NodoArbol<Pedido> nodo, Object[][] matriz) {
        if (nodo == null) return;

        llenarMatriz(nodo.izquierda, matriz);

        Pedido p = nodo.dato;
        matriz[indiceTabla][0] = p.getId();
        matriz[indiceTabla][1] = p.getFecha();
        matriz[indiceTabla][2] = p.getCliente();
        matriz[indiceTabla][3] = p.getEstado();
        indiceTabla++;

        llenarMatriz(nodo.derecha, matriz);
    }

    public ArbolABB<Pedido> getArbol() {
        return arbol;
    }
}
