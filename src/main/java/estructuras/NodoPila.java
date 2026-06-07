/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructuras;

/**
 *
 * @author ehuan
 */
public class NodoPila<T> {
    public T dato;
    public NodoPila<T> siguiente;

    public NodoPila(T dato) {
        this.dato = dato;
    }
}