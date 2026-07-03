package dao.impl;

import conexion.ConexionBD;
import dao.PedidoDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Nodo;
import modelo.Pedido;
import modelo.Producto;
import tads.ListaDoble;

public class PedidoDAOImpl implements PedidoDAO {

    private final ProductoDAOImpl productoDAO;

    public PedidoDAOImpl() {
        ConexionBD.inicializarTablas();
        productoDAO = new ProductoDAOImpl();
    }

    @Override
    public boolean insertar(Pedido pedido) {
        String sqlPedido = "INSERT INTO pedidos (id, fecha, cliente, estado) VALUES (?, ?, ?, ?)";
        String sqlDetalle = "INSERT INTO pedido_productos (pedido_id, producto_id) VALUES (?, ?) ON CONFLICT DO NOTHING";

        try (Connection cn = ConexionBD.conectar()) {
            cn.setAutoCommit(false);

            try (PreparedStatement psPedido = cn.prepareStatement(sqlPedido)) {
                psPedido.setInt(1, pedido.getId());
                psPedido.setString(2, pedido.getFecha());
                psPedido.setString(3, pedido.getCliente());
                psPedido.setString(4, pedido.getEstado());
                psPedido.executeUpdate();
            }

            try (PreparedStatement psDetalle = cn.prepareStatement(sqlDetalle)) {
                Nodo actual = pedido.getProductos().getCabeza();
                while (actual != null) {
                    productoDAO.guardarSiNoExiste(actual.dato);
                    psDetalle.setInt(1, pedido.getId());
                    psDetalle.setInt(2, actual.dato.getId());
                    psDetalle.addBatch();
                    actual = actual.siguiente;
                }
                psDetalle.executeBatch();
            }

            cn.commit();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public Pedido buscarPorId(int id) {
        String sql = "SELECT id, fecha, cliente, estado FROM pedidos WHERE id = ?";
        try (Connection cn = ConexionBD.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Pedido(
                            rs.getInt("id"),
                            rs.getString("fecha"),
                            rs.getString("cliente"),
                            cargarProductos(id),
                            rs.getString("estado")
                    );
                }
            }
        } catch (SQLException e) {
            return null;
        }
        return null;
    }

    @Override
    public List<Pedido> listar() {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT id, fecha, cliente, estado FROM pedidos ORDER BY id";
        try (Connection cn = ConexionBD.conectar(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int idPedido = rs.getInt("id");
                pedidos.add(new Pedido(
                        idPedido,
                        rs.getString("fecha"),
                        rs.getString("cliente"),
                        cargarProductos(idPedido),
                        rs.getString("estado")
                ));
            }
        } catch (SQLException e) {
            return pedidos;
        }
        return pedidos;
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM pedidos WHERE id = ?";
        try (Connection cn = ConexionBD.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    private ListaDoble cargarProductos(int idPedido) {
        ListaDoble productos = new ListaDoble();
        String sql = "SELECT p.id, p.nombre, p.descripcion, p.precio "
                + "FROM productos p INNER JOIN pedido_productos pp ON p.id = pp.producto_id "
                + "WHERE pp.pedido_id = ? ORDER BY p.id";

        try (Connection cn = ConexionBD.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idPedido);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    productos.insertar(new Producto(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("descripcion"),
                            rs.getDouble("precio")
                    ));
                }
            }
        } catch (SQLException e) {
            return productos;
        }
        return productos;
    }
}
