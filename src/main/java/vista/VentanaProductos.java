package vista;

import javax.swing.JFrame;
import modelo.Producto;
import persistencia.ManejadorArchivos;
import tads.ListaDoble;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import modelo.ArregloProductos;

public class VentanaProductos extends JFrame {
    
    private ListaDoble lista;
    private Producto[] productos;

    private JTextArea areaTexto;

    public VentanaProductos() {
        setTitle("Gestión de Productos");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // cargar datos
        lista = new ListaDoble();
        productos = ManejadorArchivos.leerProductos();
        for (Producto p : productos)
            lista.insertar(p);

        // Panel de botones
        JPanel panelBotones = new JPanel(new GridLayout(6, 1, 5, 5));

        JButton btnMostrar = new JButton("Mostrar productos");
        JButton btnAgregar = new JButton("Agregar producto");
        JButton btnBuscar = new JButton("Buscar producto");
        JButton btnEliminar = new JButton("Eliminar producto");
        JButton btnAtras = new JButton("Mostrar hacia atrás");
        JButton btnSalir = new JButton("Salir");

        panelBotones.add(btnMostrar);
        panelBotones.add(btnAgregar);
        panelBotones.add(btnBuscar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnAtras);
        panelBotones.add(btnSalir);

        add(panelBotones, BorderLayout.WEST);

        // Panel de texto
        areaTexto = new JTextArea();
        areaTexto.setEditable(false);
        add(new JScrollPane(areaTexto), BorderLayout.CENTER);


        // 1. Mostrar adelante
        btnMostrar.addActionListener((ActionEvent e) -> {
            areaTexto.setText("");
            lista.mostrarAdelanteTexto(areaTexto);
        });

        // 2. Agregar
        btnAgregar.addActionListener(e -> agregarProducto());

        // 3. Buscar
        btnBuscar.addActionListener(e -> buscarProducto());

        // 4. Eliminar
        btnEliminar.addActionListener(e -> eliminarProducto());

        // 5. Mostrar hacia atrás
        btnAtras.addActionListener(e -> {
            areaTexto.setText("");
            lista.mostrarAtrasTexto(areaTexto);
        });

        // 6. Salir
        btnSalir.addActionListener(e -> System.exit(0));
    }

    private void agregarProducto() {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog("ID:"));
            String nombre = JOptionPane.showInputDialog("Nombre:");
            String desc = JOptionPane.showInputDialog("Descripción:");
            double precio = Double.parseDouble(JOptionPane.showInputDialog("Precio:"));

            Producto nuevo = new Producto(id, nombre, desc, precio);
            lista.insertar(nuevo);

            productos = ArregloProductos.agregar(productos, nuevo);
            ManejadorArchivos.guardarProductos(productos);

            JOptionPane.showMessageDialog(this, "Producto agregado.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error en los datos.");
        }
    }

    private void buscarProducto() {
        int id = Integer.parseInt(JOptionPane.showInputDialog("ID a buscar:"));
        Producto p = lista.buscar(id);

        if (p != null)
            JOptionPane.showMessageDialog(this, "Encontrado:\n" + p);
        else
            JOptionPane.showMessageDialog(this, "Producto no encontrado.");
    }

    private void eliminarProducto() {
        int id = Integer.parseInt(JOptionPane.showInputDialog("ID a eliminar:"));
        boolean eliminado = lista.eliminar(id);

        if (eliminado) {
            productos = ArregloProductos.eliminar(productos, id);
            ManejadorArchivos.guardarProductos(productos);
            JOptionPane.showMessageDialog(this, "Producto eliminado.");
        } else {
            JOptionPane.showMessageDialog(this, "No existe el producto.");
        }
    }

}