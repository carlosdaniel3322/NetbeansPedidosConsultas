package vista;

import controladores.PedidoController;
import controladores.ProductoController;
import controladores.TareaController;
import tads.ListaDoble;
import modelo.Nodo;
import modelo.Producto;
import modelo.Pedido;

import javax.swing.*;
import java.awt.*;

public class FrmPedidos extends JFrame {

    private PedidoController controller;
    private TareaController tareaController;
    private ProductoController productoController;

    private JTextField txtId, txtFecha, txtCliente;
    private JComboBox<String> cbEstado;
    private JTextArea areaProductos;

    private JButton btnAgregarProducto;

    private Producto[] productosSistema;

    private ListaDoble listaTemp = new ListaDoble();

    public FrmPedidos() {

        controller = new PedidoController();
        tareaController = new TareaController();
        productoController = new ProductoController();
        productosSistema = productoController.obtenerProductosComoArreglo();

        setTitle("Gestion de Pedidos");
        setSize(800, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panelSuperior = new JPanel(new GridLayout(4, 2, 5, 5));

        txtId = new JTextField();
        txtFecha = new JTextField();
        txtCliente = new JTextField();
        cbEstado = new JComboBox<>(new String[]{"pendiente", "en proceso", "completado"});

        panelSuperior.add(new JLabel("ID Pedido:"));
        panelSuperior.add(txtId);
        panelSuperior.add(new JLabel("Fecha:"));
        panelSuperior.add(txtFecha);
        panelSuperior.add(new JLabel("Cliente:"));
        panelSuperior.add(txtCliente);
        panelSuperior.add(new JLabel("Estado:"));
        panelSuperior.add(cbEstado);

        add(panelSuperior, BorderLayout.NORTH);

        areaProductos = new JTextArea();
        areaProductos.setEditable(false);
        add(new JScrollPane(areaProductos), BorderLayout.CENTER);

        btnAgregarProducto = new JButton("Agregar Producto");
        btnAgregarProducto.addActionListener(e -> seleccionarProducto());

        JButton btnRegistrar = new JButton("Registrar Pedido");
        btnRegistrar.addActionListener(e -> registrarPedido());

        JButton btnBuscar = new JButton("Buscar Pedido");
        btnBuscar.addActionListener(e -> buscarPedido());

        JButton btnEliminar = new JButton("Eliminar Pedido");
        btnEliminar.addActionListener(e -> eliminarPedido());

        JPanel panelBotones = new JPanel(new GridLayout(1, 5));
        panelBotones.add(btnAgregarProducto);
        panelBotones.add(btnRegistrar);
        panelBotones.add(btnBuscar);
        panelBotones.add(btnEliminar);

        add(panelBotones, BorderLayout.SOUTH);
    }

    private void seleccionarProducto() {
        productosSistema = productoController.obtenerProductosComoArreglo();
        if (productosSistema.length == 0) {
            JOptionPane.showMessageDialog(this, "No hay productos registrados en PostgreSQL.");
            return;
        }

        String[] nombres = new String[productosSistema.length];
        for (int i = 0; i < productosSistema.length; i++) {
            nombres[i] = productosSistema[i].getNombre();
        }

        String sel = (String) JOptionPane.showInputDialog(
                this,
                "Seleccione un producto",
                "Productos",
                JOptionPane.QUESTION_MESSAGE,
                null,
                nombres,
                nombres[0]
        );

        if (sel != null) {
            for (Producto p : productosSistema) {
                if (p.getNombre().equals(sel)) {
                    listaTemp.insertar(p);
                    areaProductos.append(p.toString() + "\n");
                    break;
                }
            }
        }
    }

    private void registrarPedido() {
        try {
            int id = Integer.parseInt(txtId.getText());
            String fecha = txtFecha.getText();
            String cliente = txtCliente.getText();
            String estado = cbEstado.getSelectedItem().toString();

            boolean ok = controller.registrarPedido(id, fecha, cliente, listaTemp, estado);

            if (!ok) {
                JOptionPane.showMessageDialog(this, "No se pudo registrar. Verifica datos o ID duplicado.");
                return;
            }

            tareaController.generarTarea(id, "Procesar pedido del cliente " + cliente, fecha);
            JOptionPane.showMessageDialog(this, "Pedido registrado y tarea generada en PostgreSQL.");

            listaTemp = new ListaDoble();
            areaProductos.setText("");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error en datos.");
        }
    }

    private void buscarPedido() {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog("ID de pedido a buscar:"));
            Pedido p = controller.buscarPedido(id);

            if (p == null) {
                JOptionPane.showMessageDialog(this, "Pedido no encontrado.");
                return;
            }

            txtId.setText(String.valueOf(p.getId()));
            txtFecha.setText(p.getFecha());
            txtCliente.setText(p.getCliente());
            cbEstado.setSelectedItem(p.getEstado());

            areaProductos.setText("");
            Nodo actual = p.getProductos().getCabeza();
            while (actual != null) {
                areaProductos.append(actual.dato.toString() + "\n");
                actual = actual.siguiente;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "ID invalido.");
        }
    }

    private void eliminarPedido() {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog("ID a eliminar:"));
            boolean ok = controller.eliminarPedido(id);
            JOptionPane.showMessageDialog(this, ok ? "Pedido eliminado." : "No existe.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "ID invalido.");
        }
    }
}
