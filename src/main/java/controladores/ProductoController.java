package controladores;

import dao.ProductoDAO;
import dao.impl.ProductoDAOImpl;
import java.util.List;
import modelo.Producto;
import org.apache.commons.lang3.StringUtils;
import tads.ListaDoble;

public class ProductoController {

    private final ProductoDAO productoDAO;
    private ListaDoble listaProductos;

    public ProductoController() {
        productoDAO = new ProductoDAOImpl();
        recargarLista();
    }

    public boolean registrarProducto(int id, String nombre, String descripcion, double precio) {
        if (id <= 0 || StringUtils.isBlank(nombre) || precio < 0) {
            return false;
        }
        if (productoDAO.buscarPorId(id) != null) {
            return false;
        }

        boolean guardado = productoDAO.insertar(new Producto(id, nombre, descripcion, precio));
        if (guardado) {
            recargarLista();
        }
        return guardado;
    }

    public Producto buscarProducto(int id) {
        return productoDAO.buscarPorId(id);
    }

    public boolean eliminarProducto(int id) {
        boolean eliminado = productoDAO.eliminar(id);
        if (eliminado) {
            recargarLista();
        }
        return eliminado;
    }

    public Producto[] obtenerProductosComoArreglo() {
        List<Producto> productos = productoDAO.listar();
        return productos.toArray(new Producto[0]);
    }

    public ListaDoble getListaProductos() {
        return listaProductos;
    }

    private void recargarLista() {
        listaProductos = new ListaDoble();
        for (Producto producto : productoDAO.listar()) {
            listaProductos.insertar(producto);
        }
    }
}
