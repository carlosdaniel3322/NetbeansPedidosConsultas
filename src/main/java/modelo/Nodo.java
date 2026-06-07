package modelo;

public class Nodo {
    public Producto dato;
    public Nodo anterior;
    public Nodo siguiente;

    public Nodo(Producto dato) {
        this.dato = dato;
        this.anterior = null;
        this.siguiente = null;
    }
}
