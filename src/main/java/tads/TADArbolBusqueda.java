package tads;
import estructuras.NodoArbol;


public interface TADArbolBusqueda<T> {

    // Inserta un dato con una clave
    void insertar(int clave, T dato);

    // Busca un elemento por su clave
    T buscar(int clave);

    // Recorre el arbol en inorden
    void recorrerInOrden();

    // Verifica si el arbol está vacío
    boolean estaVacio();

    // Elimina un nodo por clave
    boolean eliminar(int clave);
}