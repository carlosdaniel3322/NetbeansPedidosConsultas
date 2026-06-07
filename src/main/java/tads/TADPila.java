/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package tads;

/**
 *
 * @author ehuan
 */
public interface TADPila<T> {

    void apilar(T dato);

    T desapilar();

    T cima();

    boolean estaVacia();
}