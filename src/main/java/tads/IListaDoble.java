package tads;
/**
 *
 * @author Admin
 */
import modelo.Producto;
public interface IListaDoble {
    void insertar(Producto producto);
    Producto buscar(int id);
    boolean eliminar(int id);
    void mostrarAdelante();
    void mostrarAtras();
}
