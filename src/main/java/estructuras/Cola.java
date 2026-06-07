/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructuras;

/**
 *
 * @author ehuan
 */

import modelo.Tarea;
import tads.TADCola;

public class Cola implements TADCola {

    private NodoCola frente;
    private NodoCola fin;

    public Cola() {
        frente = null;
        fin = null;
    }

    @Override
    public void encolar(Tarea dato) {
        NodoCola nuevo = new NodoCola(dato);

        if (estaVacia()) {
            frente = fin = nuevo;
        } else {
            fin.siguiente = nuevo;
            fin = nuevo;
        }
    }

    @Override
    public Tarea desencolar() {
        if (estaVacia())
            return null;

        Tarea tarea = frente.dato;
        frente = frente.siguiente;

        if (frente == null)
            fin = null;

        return tarea;
    }

    @Override
    public Tarea frente() {
        if (estaVacia()) 
            return null;
        return frente.dato;
    }

    @Override
    public boolean estaVacia() {
        return frente == null;
    }

    // Extra útil para recorrer en JSON, tablas, etc.
    public NodoCola getFrenteNodo() {
        return frente;
    }
}

