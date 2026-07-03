package controladores;

import com.google.common.base.Preconditions;
import dao.PedidoDAO;
import dao.impl.PedidoDAOImpl;
import estructuras.ArbolABB;
import estructuras.NodoArbol;
import java.util.List;
import modelo.Pedido;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tads.ListaDoble;

public class PedidoController {

    private static final Logger LOG = LoggerFactory.getLogger(PedidoController.class);

    private final PedidoDAO pedidoDAO;
    private ArbolABB<Pedido> arbol;
    private int indiceTabla = 0;

    public PedidoController() {
        pedidoDAO = new PedidoDAOImpl();
        recargarArbol();
        LOG.info("Pedidos cargados desde PostgreSQL");
    }

    public boolean registrarPedido(int id, String fecha, String cliente, ListaDoble productos, String estado) {
        try {
            Preconditions.checkArgument(id > 0, "ID invalido");
            Preconditions.checkNotNull(productos, "Productos no puede ser null");

            if (StringUtils.isBlank(cliente) || StringUtils.isBlank(fecha) || StringUtils.isBlank(estado)) {
                return false;
            }
            if (pedidoDAO.buscarPorId(id) != null) {
                LOG.warn("Intento de registrar ID duplicado: {}", id);
                return false;
            }

            Pedido nuevo = new Pedido(id, fecha, cliente, productos, estado);
            boolean guardado = pedidoDAO.insertar(nuevo);
            if (guardado) {
                recargarArbol();
                LOG.info("Pedido registrado correctamente: {}", id);
            }
            return guardado;
        } catch (Exception e) {
            LOG.error("Error registrando pedido", e);
            return false;
        }
    }

    public Pedido buscarPedido(int id) {
        LOG.info("Busqueda de pedido {}", id);
        return pedidoDAO.buscarPorId(id);
    }

    public boolean eliminarPedido(int id) {
        try {
            boolean eliminado = pedidoDAO.eliminar(id);
            if (eliminado) {
                recargarArbol();
                LOG.info("Pedido eliminado {}", id);
            }
            return eliminado;
        } catch (Exception e) {
            LOG.error("Error eliminando pedido", e);
            return false;
        }
    }

    public Object[][] obtenerPedidosComoMatriz() {
        int total = contarNodos(arbol.getRaiz());
        Object[][] matriz = new Object[total][4];
        indiceTabla = 0;
        llenarMatriz(arbol.getRaiz(), matriz);
        return matriz;
    }

    private void recargarArbol() {
        arbol = new ArbolABB<>();
        List<Pedido> pedidos = pedidoDAO.listar();
        for (Pedido pedido : pedidos) {
            arbol.insertar(pedido.getId(), pedido);
        }
    }

    private int contarNodos(NodoArbol<Pedido> nodo) {
        if (nodo == null) {
            return 0;
        }
        return 1 + contarNodos(nodo.izquierda) + contarNodos(nodo.derecha);
    }

    private void llenarMatriz(NodoArbol<Pedido> nodo, Object[][] matriz) {
        if (nodo == null) {
            return;
        }

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
