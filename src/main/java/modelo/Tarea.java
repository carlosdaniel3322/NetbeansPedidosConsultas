/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author ehuan
 */
public class Tarea {
    private int idTarea;
    private int idPedido;
    private String descripcion;
    private String fechaCreacion;
    private String estado;

    public Tarea(int idTarea, int idPedido, String descripcion, String fechaCreacion, String estado) {
        this.idTarea = idTarea;
        this.idPedido = idPedido;
        this.descripcion = descripcion;
        this.fechaCreacion = fechaCreacion;
        this.estado = estado;
    }

    public int getIdTarea() { return idTarea; }
    public int getIdPedido() { return idPedido; }
    public String getDescripcion() { return descripcion; }
    public String getFechaCreacion() { return fechaCreacion; }
    public String getEstado() { return estado; }

    public void setEstado(String estado) { this.estado = estado; }

    @Override
    public String toString() {
        return "Tarea #" + idTarea + " → Pedido: " + idPedido + " | " + descripcion + " | " + estado;
    }
}