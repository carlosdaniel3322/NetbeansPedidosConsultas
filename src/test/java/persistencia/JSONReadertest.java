package persistencia;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JSONReadertest {
    
    @Test
    public void testLeerArchivoExistente() throws IOException {
        File archivo = File.createTempFile("prueba", ".json");
        FileWriter writer = new FileWriter(archivo);
        writer.write("{\"nombre\":\"Carlos\"}");
        writer.close();
        String contenido = JSONReader.leerArchivo(archivo.getAbsolutePath());
        assertEquals("{\"nombre\":\"Carlos\"}", contenido);
        archivo.delete();
    }

    @Test
    public void testLeerArchivoInexistente() {
        String contenido = JSONReader.leerArchivo("archivo_que_no_existe.json");
        assertEquals("", contenido);
    }

    @Test
    public void testLeerArchivoVacio() throws IOException {
        File archivo = File.createTempFile("vacio", ".json");
        String contenido = JSONReader.leerArchivo(archivo.getAbsolutePath());
        assertEquals("", contenido);
        archivo.delete();
    }
    
}
