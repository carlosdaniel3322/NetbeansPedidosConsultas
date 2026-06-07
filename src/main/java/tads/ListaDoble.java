package tads;
/**
 *
 * @author Admin
 */
import javax.swing.JTextArea;
import modelo.Nodo;
import modelo.Producto;

public class ListaDoble implements IListaDoble{
    private Nodo cabeza;
    private Nodo cola;

    public ListaDoble() {
        cabeza = null;
        cola = null;
    }

    public Nodo getCabeza() {
        return cabeza;
    }

    public void setCabeza(Nodo cabeza) {
        this.cabeza = cabeza;
    }

    public Nodo getCola() {
        return cola;
    }

    public void setCola(Nodo cola) {
        this.cola = cola;
    }

    @Override
    public void insertar(Producto producto) {
        Nodo nuevo = new Nodo(producto);
        if (cabeza == null) {
            cabeza = cola = nuevo;
        } else {
            cola.siguiente = nuevo;
            nuevo.anterior = cola;
            cola = nuevo;
        }
    }

    @Override
    public Producto buscar(int id) {
         Nodo actual = cabeza;
        while (actual != null) {
            if (actual.dato.getId() == id)
                return actual.dato;
            actual = actual.siguiente;
        }
        return null;
    }

    @Override
    public boolean eliminar(int id) {
        Nodo actual = cabeza;
        while (actual != null) {
            if (actual.dato.getId() == id) {
                if (actual == cabeza) {
                    cabeza = actual.siguiente;
                    if (cabeza != null) cabeza.anterior = null;
                } else if (actual == cola) {
                    cola = actual.anterior;
                    cola.siguiente = null;
                } else {
                    actual.anterior.siguiente = actual.siguiente;
                    actual.siguiente.anterior = actual.anterior;
                }
                return true;
            }
            actual = actual.siguiente;
        }
        return false;
    }

    @Override
    public void mostrarAdelante() {
        Nodo actual = cabeza;
        while (actual != null) {
            System.out.println(actual.dato);
            actual = actual.siguiente;
        }
    }

    @Override
    public void mostrarAtras() {
       Nodo actual = cola;
        while (actual != null) {
            System.out.println(actual.dato);
            actual = actual.anterior;
        }
    }
    
    
    public void mostrarAdelanteTexto(JTextArea area) {
    Nodo actual = cabeza;
    while (actual != null) {
        area.append(actual.dato.toString() + "\n");
        actual = actual.siguiente;
    }
}

public void mostrarAtrasTexto(JTextArea area) {
    Nodo actual = cola;
    while (actual != null) {
        area.append(actual.dato.toString() + "\n");
        actual = actual.anterior;
    }
}
}

