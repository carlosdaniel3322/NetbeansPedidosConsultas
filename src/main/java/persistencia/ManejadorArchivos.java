package persistencia;

import com.google.gson.Gson;
import java.io.*;

import modelo.Producto;

public class ManejadorArchivos {
    private static final String ARCHIVO = "productos.json";

    public static Producto[] leerProductos() {
        try (InputStream is = ManejadorArchivos.class.getClassLoader().getResourceAsStream(ARCHIVO)) {
            if (is == null) {
                System.out.println("No se encontro productos.json en resources.");
                return new Producto[0]; 
                // arreglo vacío
            }
            InputStreamReader reader = new InputStreamReader(is);
            Gson gson = new Gson();
            return gson.fromJson(reader, Producto[].class);
        } catch (IOException e) {
            e.printStackTrace();
            return new Producto[0];
        }
    }

    public static void guardarProductos(Producto[] productos) {
        try (FileWriter writer = new FileWriter("src/main/resources/" + ARCHIVO)) {
            Gson gson = new Gson();
            gson.toJson(productos, writer);
            System.out.println("Archivo JSON actualizado.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
