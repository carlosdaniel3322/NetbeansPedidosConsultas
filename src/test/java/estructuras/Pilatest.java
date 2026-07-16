package estructuras;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Pilatest {
    
     @Test
    public void testPilaVaciaAlCrear() {
        Pila<String> pila = new Pila<>();

        assertTrue(pila.estaVacia());
        assertNull(pila.cima());
    }

    @Test
    public void testApilar() {
        Pila<String> pila = new Pila<>();

        pila.apilar("A");

        assertFalse(pila.estaVacia());
        assertEquals("A", pila.cima());
    }

    @Test
    public void testDesapilar() {
        Pila<String> pila = new Pila<>();

        pila.apilar("A");

        String dato = pila.desapilar();

        assertEquals("A", dato);
        assertTrue(pila.estaVacia());
    }

    @Test
    public void testDesapilarPilaVacia() {
        Pila<String> pila = new Pila<>();

        assertNull(pila.desapilar());
    }

    @Test
    public void testOrdenLIFO() {
        Pila<String> pila = new Pila<>();

        pila.apilar("A");
        pila.apilar("B");
        pila.apilar("C");

        assertEquals("C", pila.desapilar());
        assertEquals("B", pila.desapilar());
        assertEquals("A", pila.desapilar());
        assertTrue(pila.estaVacia());
    }

    @Test
    public void testCimaNoEliminaElemento() {
        Pila<String> pila = new Pila<>();

        pila.apilar("A");

        assertEquals("A", pila.cima());
        assertFalse(pila.estaVacia());

        // Verifica que siga existiendo el elemento
        assertEquals("A", pila.desapilar());
    }
    
}
