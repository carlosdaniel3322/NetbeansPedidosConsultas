/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author ehuan
 */
import tads.ListaDoble;

public class Pedido {

    private int id;
    private String fecha;
    private String cliente;
    private ListaDoble productos;
    private String estado;

    public Pedido(int id, String fecha, String cliente, ListaDoble productos, String estado) {
        this.id = id;
        this.fecha = fecha;
        this.cliente = cliente;
        this.productos = productos;
        this.estado = estado;
    }

    // GETTERS
    public int getId() { return id; }
    public String getFecha() { return fecha; }
    public String getCliente() { return cliente; }
    public ListaDoble getProductos() { return productos; }
    public String getEstado() { return estado; }

    // SETTERS
    public void setProductos(ListaDoble productos) { this.productos = productos; }
}
