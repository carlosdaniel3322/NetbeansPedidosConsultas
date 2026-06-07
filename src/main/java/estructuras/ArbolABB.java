/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructuras;

/**
 *
 * @author ehuan
 */

import tads.TADArbolBusqueda;

public class ArbolABB<T> implements TADArbolBusqueda<T> {

    private NodoArbol<T> raiz;

    // INSERTAR
    @Override
    public void insertar(int clave, T dato) {
        raiz = insertarRecursivo(raiz, clave, dato);
    }

    private NodoArbol<T> insertarRecursivo(NodoArbol<T> nodo, int clave, T dato) {

        if (nodo == null)
            return new NodoArbol<>(clave, dato);

        if (clave < nodo.clave)
            nodo.izquierda = insertarRecursivo(nodo.izquierda, clave, dato);

        else if (clave > nodo.clave)
            nodo.derecha = insertarRecursivo(nodo.derecha, clave, dato);

        return nodo;
    }

    // BUSCAR
    @Override
    public T buscar(int clave) {

        NodoArbol<T> aux = raiz;

        while (aux != null) {
            if (clave == aux.clave)
                return aux.dato;

            if (clave < aux.clave)
                aux = aux.izquierda;
            else
                aux = aux.derecha;
        }
        return null;
    }

    // RECORRIDO
    @Override
    public void recorrerInOrden() {
        recorrerEnOrdenRecursivo(raiz);
    }

    private void recorrerEnOrdenRecursivo(NodoArbol<T> nodo) {
        if (nodo != null) {
            recorrerEnOrdenRecursivo(nodo.izquierda);
            System.out.println("Clave: " + nodo.clave + " → " + nodo.dato);
            recorrerEnOrdenRecursivo(nodo.derecha);
        }
    }

    // ¿ESTÁ VACÍO?
    @Override
    public boolean estaVacio() {
        return raiz == null;
    }

    public NodoArbol<T> getRaiz() {
        return raiz;
    }

    // ELIMINAR
    @Override
    public boolean eliminar(int clave) {

        // si no existe, no se elimina
        if (buscar(clave) == null)
            return false;

        raiz = eliminarRecursivo(raiz, clave);
        return true;
    }

    private NodoArbol<T> eliminarRecursivo(NodoArbol<T> nodo, int clave) {

        if (nodo == null)
            return null;

        if (clave < nodo.clave) {
            nodo.izquierda = eliminarRecursivo(nodo.izquierda, clave);
        } 
        else if (clave > nodo.clave) {
            nodo.derecha = eliminarRecursivo(nodo.derecha, clave);
        } 
        else {  
            // ========== Nodo encontrado ==========

            // Caso 1: NO tiene hijo izquierdo
            if (nodo.izquierda == null)
                return nodo.derecha;

            // Caso 2: NO tiene hijo derecho
            if (nodo.derecha == null)
                return nodo.izquierda;

            // Caso 3: Tiene DOS hijos
            NodoArbol<T> sucesor = nodo.derecha;

            while (sucesor.izquierda != null)
                sucesor = sucesor.izquierda;

            nodo.clave = sucesor.clave;
            nodo.dato = sucesor.dato;

            nodo.derecha = eliminarRecursivo(nodo.derecha, sucesor.clave);
        }

        return nodo;
    }

    // CONVERTIR A ARREGLO (para JSON)
    public T[] convertirAArreglo() {

        int cantidad = contarNodos(raiz);
        @SuppressWarnings("unchecked")
        T[] arreglo = (T[]) new Object[cantidad];

        llenarArregloEnOrden(arreglo, raiz, new int[]{0}); // índice dentro de un array para modificarlo
        return arreglo;
    }

    private int contarNodos(NodoArbol<T> nodo) {
        if (nodo == null) return 0;
        return 1 + contarNodos(nodo.izquierda) + contarNodos(nodo.derecha);
    }

    private void llenarArregloEnOrden(T[] arr, NodoArbol<T> nodo, int[] idx) {

        if (nodo != null) {
            llenarArregloEnOrden(arr, nodo.izquierda, idx);
            arr[idx[0]++] = nodo.dato;
            llenarArregloEnOrden(arr, nodo.derecha, idx);
        }
    }
}
