package estructuras;
import modelo.Tarea;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Colatest {
    
    @Test
    public void testColaVaciaAlCrear() {
        Cola cola = new Cola();

        assertTrue(cola.estaVacia());
        assertNull(cola.frente());
    }

    @Test
    public void testEncolar() {
        Cola cola = new Cola();

        Tarea tarea = new Tarea(
                1,
                100,
                "Preparar pedido",
                "08/07/2026",
                "Pendiente"
        );

        cola.encolar(tarea);

        assertFalse(cola.estaVacia());
        assertEquals(tarea, cola.frente());
    }

    @Test
    public void testDesencolar() {
        Cola cola = new Cola();

        Tarea tarea = new Tarea(
                1,
                100,
                "Preparar pedido",
                "08/07/2026",
                "Pendiente"
        );

        cola.encolar(tarea);

        Tarea eliminada = cola.desencolar();

        assertEquals(tarea, eliminada);
        assertTrue(cola.estaVacia());
    }

    @Test
    public void testDesencolarColaVacia() {
        Cola cola = new Cola();

        assertNull(cola.desencolar());
    }

    @Test
    public void testFrenteNoEliminaElemento() {
        Cola cola = new Cola();

        Tarea tarea = new Tarea(
                1,
                100,
                "Preparar pedido",
                "08/07/2026",
                "Pendiente"
        );

        cola.encolar(tarea);

        assertEquals(tarea, cola.frente());
        assertFalse(cola.estaVacia());
    }
}
