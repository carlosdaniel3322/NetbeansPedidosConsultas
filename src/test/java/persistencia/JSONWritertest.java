package persistencia;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JSONWritertest {
    
    @Test
    public void testEscribirArchivo() throws IOException {
        File archivo = File.createTempFile("jsonwriter", ".json");
        String contenido = "{\"nombre\":\"Carlos\"}";
        JSONWriter.escribirArchivo(archivo.getAbsolutePath(), contenido);
        String leido = Files.readString(archivo.toPath());
        assertEquals(contenido, leido);
        archivo.delete();
    }

    @Test
    public void testSobrescribirArchivo() throws IOException {
        File archivo = File.createTempFile("jsonwriter", ".json");
        JSONWriter.escribirArchivo(archivo.getAbsolutePath(), "Hola");
        JSONWriter.escribirArchivo(archivo.getAbsolutePath(), "Mundo");
        String leido = Files.readString(archivo.toPath());
        assertEquals("Mundo", leido);
        archivo.delete();
    }

    @Test
    public void testEscribirArchivoVacio() throws IOException {
        File archivo = File.createTempFile("jsonwriter", ".json");
        JSONWriter.escribirArchivo(archivo.getAbsolutePath(), "");
        String leido = Files.readString(archivo.toPath());
        assertEquals("", leido);
        archivo.delete();
    }
}
