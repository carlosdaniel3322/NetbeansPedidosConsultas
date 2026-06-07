/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructuras;

/**
 *
 * @author ehuan
 */
public class NodoArbol<T> {

    public int clave;
    public T dato;
    public NodoArbol<T> izquierda;
    public NodoArbol<T> derecha;

    public NodoArbol(int clave, T dato) {
        this.clave = clave;
        this.dato = dato;
        this.izquierda = null;
        this.derecha = null;
    }
}