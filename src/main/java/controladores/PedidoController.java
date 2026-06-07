package controladores;

import com.google.common.base.Preconditions;
import estructuras.ArbolABB;
import estructuras.NodoArbol;
import modelo.Pedido;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistencia.JSONPedidos;
import tads.ListaDoble;

public class PedidoController {

    private static final Logger LOG =
            LoggerFactory.getLogger(PedidoController.class);

    private ArbolABB<Pedido> arbol;

    public PedidoController() {

        arbol = JSONPedidos.cargarPedidos();

        LOG.info("Pedidos cargados correctamente");
    }

    public boolean registrarPedido(
            int id,
            String fecha,
            String cliente,
            ListaDoble productos,
            String estado) {

        try {

            Preconditions.checkArgument(
                    id > 0,
                    "ID inválido");

            Preconditions.checkNotNull(
                    productos,
                    "Productos no puede ser null");

            if (StringUtils.isBlank(cliente))
                return false;

            if (StringUtils.isBlank(fecha))
                return false;

            if (StringUtils.isBlank(estado))
                return false;

            if (arbol.buscar(id) != null) {

                LOG.warn(
                        "Intento de registrar ID duplicado: {}",
                        id);

                return false;
            }

            Pedido nuevo =
                    new Pedido(
                            id,
                            fecha,
                            cliente,
                            productos,
                            estado);

            arbol.insertar(id, nuevo);

            JSONPedidos.guardarPedidos(arbol);

            LOG.info(
                    "Pedido registrado correctamente: {}",
                    id);

            return true;

        } catch (Exception e) {

            LOG.error(
                    "Error registrando pedido",
                    e);

            return false;
        }
    }

    public Pedido buscarPedido(int id) {

        LOG.info(
                "Búsqueda de pedido {}",
                id);

        return arbol.buscar(id);
    }

    public boolean eliminarPedido(int id) {

        try {

            if (arbol.buscar(id) == null)
                return false;

            arbol.eliminar(id);

            JSONPedidos.guardarPedidos(arbol);

            LOG.info(
                    "Pedido eliminado {}",
                    id);

            return true;

        } catch (Exception e) {

            LOG.error(
                    "Error eliminando pedido",
                    e);

            return false;
        }
    }

    public Object[][] obtenerPedidosComoMatriz() {

        int total = contarNodos(arbol.getRaiz());

        Object[][] matriz =
                new Object[total][4];

        indiceTabla = 0;

        llenarMatriz(arbol.getRaiz(), matriz);

        return matriz;
    }

    private int contarNodos(
            NodoArbol<Pedido> nodo) {

        if (nodo == null)
            return 0;

        return 1
                + contarNodos(nodo.izquierda)
                + contarNodos(nodo.derecha);
    }

    private int indiceTabla = 0;

    private void llenarMatriz(
            NodoArbol<Pedido> nodo,
            Object[][] matriz) {

        if (nodo == null)
            return;

        llenarMatriz(
                nodo.izquierda,
                matriz);

        Pedido p = nodo.dato;

        matriz[indiceTabla][0] = p.getId();
        matriz[indiceTabla][1] = p.getFecha();
        matriz[indiceTabla][2] = p.getCliente();
        matriz[indiceTabla][3] = p.getEstado();

        indiceTabla++;

        llenarMatriz(
                nodo.derecha,
                matriz);
    }

    public ArbolABB<Pedido> getArbol() {
        return arbol;
    }
}