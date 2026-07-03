package vista;

import controladores.ProductoController;
import javax.swing.JFrame;
import modelo.Producto;
import tads.ListaDoble;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class VentanaProductos extends JFrame {

    private final ProductoController controller;
    private ListaDoble lista;
    private Producto[] productos;
    private JTextArea areaTexto;

    public VentanaProductos() {
        controller = new ProductoController();

        setTitle("Gestion de Productos");
        setSize(600, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        cargarDatos();

        JPanel panelBotones = new JPanel(new GridLayout(6, 1, 5, 5));

        JButton btnMostrar = new JButton("Mostrar productos");
        JButton btnAgregar = new JButton("Agregar producto");
        JButton btnBuscar = new JButton("Buscar producto");
        JButton btnEliminar = new JButton("Eliminar producto");
        JButton btnAtras = new JButton("Mostrar hacia atras");
        JButton btnSalir = new JButton("Salir");

        panelBotones.add(btnMostrar);
        panelBotones.add(btnAgregar);
        panelBotones.add(btnBuscar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnAtras);
        panelBotones.add(btnSalir);

        add(panelBotones, BorderLayout.WEST);

        areaTexto = new JTextArea();
        areaTexto.setEditable(false);
        add(new JScrollPane(areaTexto), BorderLayout.CENTER);

        btnMostrar.addActionListener((ActionEvent e) -> mostrarAdelante());
        btnAgregar.addActionListener(e -> agregarProducto());
        btnBuscar.addActionListener(e -> buscarProducto());
        btnEliminar.addActionListener(e -> eliminarProducto());
        btnAtras.addActionListener(e -> mostrarAtras());
        btnSalir.addActionListener(e -> dispose());
    }

    private void cargarDatos() {
        productos = controller.obtenerProductosComoArreglo();
        lista = controller.getListaProductos();
    }

    private void mostrarAdelante() {
        cargarDatos();
        areaTexto.setText("");
        lista.mostrarAdelanteTexto(areaTexto);
    }

    private void mostrarAtras() {
        cargarDatos();
        areaTexto.setText("");
        lista.mostrarAtrasTexto(areaTexto);
    }

    private void agregarProducto() {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog("ID:"));
            String nombre = JOptionPane.showInputDialog("Nombre:");
            String desc = JOptionPane.showInputDialog("Descripcion:");
            double precio = Double.parseDouble(JOptionPane.showInputDialog("Precio:"));

            boolean ok = controller.registrarProducto(id, nombre, desc, precio);
            if (!ok) {
                JOptionPane.showMessageDialog(this, "No se pudo agregar. Verifica datos o ID duplicado.");
                return;
            }

            cargarDatos();
            JOptionPane.showMessageDialog(this, "Producto agregado en PostgreSQL.");
            mostrarAdelante();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error en los datos.");
        }
    }

    private void buscarProducto() {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog("ID a buscar:"));
            Producto p = controller.buscarProducto(id);

            if (p != null) {
                JOptionPane.showMessageDialog(this, "Encontrado:\n" + p);
            } else {
                JOptionPane.showMessageDialog(this, "Producto no encontrado.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "ID invalido.");
        }
    }

    private void eliminarProducto() {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog("ID a eliminar:"));
            boolean eliminado = controller.eliminarProducto(id);

            if (eliminado) {
                cargarDatos();
                JOptionPane.showMessageDialog(this, "Producto eliminado de PostgreSQL.");
                mostrarAdelante();
            } else {
                JOptionPane.showMessageDialog(this, "No existe el producto o esta asociado a un pedido.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "ID invalido.");
        }
    }
}
