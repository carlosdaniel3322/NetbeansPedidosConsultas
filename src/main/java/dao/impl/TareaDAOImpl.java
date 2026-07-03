package dao.impl;

import conexion.ConexionBD;
import dao.TareaDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Tarea;

public class TareaDAOImpl implements TareaDAO {

    public TareaDAOImpl() {
        ConexionBD.inicializarTablas();
    }

    @Override
    public boolean insertar(Tarea tarea) {
        String sql = "INSERT INTO tareas (id_tarea, id_pedido, descripcion, fecha_creacion, estado) VALUES (?, ?, ?, ?, ?)";
        try (Connection cn = ConexionBD.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, tarea.getIdTarea());
            ps.setInt(2, tarea.getIdPedido());
            ps.setString(3, tarea.getDescripcion());
            ps.setString(4, tarea.getFechaCreacion());
            ps.setString(5, tarea.getEstado());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public List<Tarea> listar() {
        List<Tarea> tareas = new ArrayList<>();
        String sql = "SELECT id_tarea, id_pedido, descripcion, fecha_creacion, estado FROM tareas ORDER BY id_tarea";
        try (Connection cn = ConexionBD.conectar(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                tareas.add(mapearTarea(rs));
            }
        } catch (SQLException e) {
            return tareas;
        }
        return tareas;
    }

    @Override
    public Tarea obtenerPrimera() {
        String sql = "SELECT id_tarea, id_pedido, descripcion, fecha_creacion, estado FROM tareas ORDER BY id_tarea LIMIT 1";
        try (Connection cn = ConexionBD.conectar(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return mapearTarea(rs);
            }
        } catch (SQLException e) {
            return null;
        }
        return null;
    }

    @Override
    public boolean eliminar(int idTarea) {
        String sql = "DELETE FROM tareas WHERE id_tarea = ?";
        try (Connection cn = ConexionBD.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idTarea);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public int obtenerSiguienteId() {
        String sql = "SELECT COALESCE(MAX(id_tarea), 0) + 1 AS siguiente FROM tareas";
        try (Connection cn = ConexionBD.conectar(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("siguiente");
            }
        } catch (SQLException e) {
            return 1;
        }
        return 1;
    }

    private Tarea mapearTarea(ResultSet rs) throws SQLException {
        return new Tarea(
                rs.getInt("id_tarea"),
                rs.getInt("id_pedido"),
                rs.getString("descripcion"),
                rs.getString("fecha_creacion"),
                rs.getString("estado")
        );
    }
}
