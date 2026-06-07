package vista;

import controladores.PedidoController;

import javax.swing.*;
import java.awt.*;

public class FrmVerPedidos extends JFrame {

    private PedidoController controller;

    public FrmVerPedidos() {
        controller = new PedidoController();

        setTitle("Lista de Pedidos");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        mostrarTabla();
    }

    private void mostrarTabla() {

        Object[][] datos = controller.obtenerPedidosComoMatriz();
        String[] columnas = {"ID", "Fecha", "Cliente", "Estado"};

        JTable tabla = new JTable(datos, columnas);
        JScrollPane scroll = new JScrollPane(tabla);

        add(scroll, BorderLayout.CENTER);
    }
}