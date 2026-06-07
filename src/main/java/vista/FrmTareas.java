/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

/**
 *
 * @author ehuan
 */
import controladores.TareaController;
import estructuras.NodoCola;
import modelo.Tarea;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FrmTareas extends JFrame {

    private TareaController controller;
    private JTable tabla;
    private DefaultTableModel modelo;

    public FrmTareas() {
        controller = new TareaController();

        setTitle("Gestión de Tareas (Cola FIFO)");
        setSize(750, 420);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        modelo = new DefaultTableModel(
                new Object[]{"ID Tarea", "ID Pedido", "Descripción", "Fecha", "Estado"}, 0
        );

        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JButton btnProcesar = new JButton("Procesar siguiente");
        btnProcesar.addActionListener(e -> procesarTarea());

        JButton btnRefrescar = new JButton("Refrescar");
        btnRefrescar.addActionListener(e -> cargarTabla());

        JPanel panel = new JPanel();
        panel.add(btnProcesar);
        panel.add(btnRefrescar);

        add(panel, BorderLayout.SOUTH);

        cargarTabla();
    }

    private void cargarTabla() {
        modelo.setRowCount(0);

        NodoCola actual = controller.getCola().getFrenteNodo();

        while (actual != null) {
            Tarea t = actual.dato;

            modelo.addRow(new Object[]{
                    t.getIdTarea(),
                    t.getIdPedido(),
                    t.getDescripcion(),
                    t.getFechaCreacion(),
                    t.getEstado()
            });

            actual = actual.siguiente;
        }
    }

    private void procesarTarea() {
        Tarea t = controller.procesar();

        if (t == null) {
            JOptionPane.showMessageDialog(this, "No hay tareas en la cola.");
            return;
        }

        JOptionPane.showMessageDialog(this,
                "Tarea procesada:\n" + t.getDescripcion()
        );

        cargarTabla();
    }
}
