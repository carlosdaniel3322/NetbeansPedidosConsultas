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
public class JSONReader {

    public static String leerArchivo(String ruta) {
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(ruta));
            return new String(bytes);
        } catch (Exception e) {
            System.out.println("Error al leer JSON (" + ruta + "): " + e.getMessage());
            return "";
        }
    }
}