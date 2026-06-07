/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package tads;

import modelo.Tarea;

/**
 *
 * @author ehuan
 */
public interface TADCola {

    // Encola una tarea al final
    void encolar(Tarea dato);

    // Quita y devuelve la tarea del frente
    Tarea desencolar();

    // Devuelve la tarea del frente sin quitarla
    Tarea frente();

    // Indica si la cola está vacía
    boolean estaVacia();
}