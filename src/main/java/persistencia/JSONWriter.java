/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *
 * @author ehuan
 */
public class JSONWriter {

    public static void escribirArchivo(String ruta, String contenido) {
        try {
            Files.write(Paths.get(ruta), contenido.getBytes());
        } catch (Exception e) {
            System.out.println("Error al escribir JSON (" + ruta + "): " + e.getMessage());
        }
    }
}