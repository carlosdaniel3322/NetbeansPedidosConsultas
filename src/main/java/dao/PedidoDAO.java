package dao;

import java.util.List;
import modelo.Pedido;

public interface PedidoDAO {
    boolean insertar(Pedido pedido);
    Pedido buscarPorId(int id);
    List<Pedido> listar();
    boolean eliminar(int id);
}
