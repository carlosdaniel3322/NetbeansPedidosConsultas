/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructuras;

/**
 *
 * @author ehuan
 */
import tads.TADPila;

public class Pila<T> implements TADPila<T> {

    private NodoPila<T> tope;

    @Override
    public void apilar(T dato) {
        NodoPila<T> nuevo = new NodoPila<>(dato);
        nuevo.siguiente = tope;
        tope = nuevo;
    }

    @Override
    public T desapilar() {
        if (estaVacia()) return null;

        T dato = tope.dato;
        tope = tope.siguiente;
        return dato;
    }

    @Override
    public T cima() {
        return estaVacia() ? null : tope.dato;
    }

    @Override
    public boolean estaVacia() {
        return tope == null;
    }
}