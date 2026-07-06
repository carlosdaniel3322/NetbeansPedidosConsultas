package conexion;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class ConexionBD {
    private static final String CONFIG_FILE = "database.properties";
    private static final Properties PROPERTIES = cargarConfiguracion();

    private ConexionBD() {
    }
    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(
                PROPERTIES.getProperty("db.url"),
                PROPERTIES.getProperty("db.user"),
                PROPERTIES.getProperty("db.password")
        );
    }
    public static void inicializarTablas() {
        try (Connection cn = conectar(); Statement st = cn.createStatement()) {
            st.executeUpdate("CREATE TABLE IF NOT EXISTS productos ("
                    + "id INTEGER PRIMARY KEY,"
                    + "nombre VARCHAR(100) NOT NULL,"
                    + "descripcion TEXT,"
                    + "precio NUMERIC(10, 2) NOT NULL"
                    + ")");
            st.executeUpdate("CREATE TABLE IF NOT EXISTS pedidos ("
                    + "id INTEGER PRIMARY KEY,"
                    + "fecha VARCHAR(20) NOT NULL,"
                    + "cliente VARCHAR(100) NOT NULL,"
                    + "estado VARCHAR(50) NOT NULL"
                    + ")");
            st.executeUpdate("CREATE TABLE IF NOT EXISTS pedido_productos ("
                    + "id SERIAL PRIMARY KEY,"
                    + "pedido_id INTEGER NOT NULL REFERENCES pedidos(id) ON DELETE CASCADE,"
                    + "producto_id INTEGER NOT NULL REFERENCES productos(id),"
                    + "UNIQUE (pedido_id, producto_id)"
                    + ")");
            st.executeUpdate("CREATE TABLE IF NOT EXISTS tareas ("
                    + "id_tarea INTEGER PRIMARY KEY,"
                    + "id_pedido INTEGER NOT NULL REFERENCES pedidos(id) ON DELETE CASCADE,"
                    + "descripcion TEXT NOT NULL,"
                    + "fecha_creacion VARCHAR(20) NOT NULL,"
                    + "estado VARCHAR(50) NOT NULL"
                    + ")");
        } catch (SQLException e) {
            throw new IllegalStateException("No se pudo inicializar la base de datos PostgreSQL", e);
        }
    }
    private static Properties cargarConfiguracion() {
        Properties props = new Properties();
        try (InputStream input = ConexionBD.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input != null) {
                props.load(input);
            }
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo leer " + CONFIG_FILE, e);
        }

        props.putIfAbsent("db.url", "jdbc:postgresql://localhost:5432/sgpt");
        props.putIfAbsent("db.user", "postgres");
        props.putIfAbsent("db.password", "postgres");
        return props;
    }
}
