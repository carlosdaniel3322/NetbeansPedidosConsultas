package modelo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ArregloProductostest {
    
    @Test
    public void testAgregarProductoArregloVacio() {

        Producto[] productos = new Producto[0];

        Producto nuevo = new Producto(
                1,
                "Laptop",
                "Laptop Gamer",
                3500.00
        );

        Producto[] resultado = ArregloProductos.agregar(productos, nuevo);

        assertEquals(1, resultado.length);
        assertEquals(nuevo, resultado[0]);
    }

    @Test
    public void testAgregarProductoAlFinal() {

        Producto p1 = new Producto(1, "Mouse", "Mouse Gamer", 80);
        Producto p2 = new Producto(2, "Teclado", "Mecánico", 180);

        Producto[] productos = {p1};

        Producto[] resultado = ArregloProductos.agregar(productos, p2);

        assertEquals(2, resultado.length);
        assertEquals(p1, resultado[0]);
        assertEquals(p2, resultado[1]);
    }

    @Test
    public void testEliminarProductoExistente() {

        Producto p1 = new Producto(1, "Mouse", "Mouse Gamer", 80);
        Producto p2 = new Producto(2, "Teclado", "Mecánico", 180);

        Producto[] productos = {p1, p2};

        Producto[] resultado = ArregloProductos.eliminar(productos, 1);

        assertEquals(1, resultado.length);
        assertEquals(2, resultado[0].getId());
    }

    @Test
    public void testEliminarProductoInexistente() {

        Producto p1 = new Producto(1, "Mouse", "Mouse Gamer", 80);
        Producto p2 = new Producto(2, "Teclado", "Mecánico", 180);

        Producto[] productos = {p1, p2};

        Producto[] resultado = ArregloProductos.eliminar(productos, 99);

        assertEquals(2, resultado.length);
    }

    @Test
    public void testEliminarUnicoProducto() {

        Producto p1 = new Producto(1, "Mouse", "Mouse Gamer", 80);

        Producto[] productos = {p1};

        Producto[] resultado = ArregloProductos.eliminar(productos, 1);

        assertEquals(0, resultado.length);
    }

    @Test
    public void testAgregarConservaDatos() {

        Producto p1 = new Producto(1, "Monitor", "24 pulgadas", 600);

        Producto[] productos = new Producto[0];

        Producto[] resultado = ArregloProductos.agregar(productos, p1);

        assertEquals("Monitor", resultado[0].getNombre());
        assertEquals("24 pulgadas", resultado[0].getDescripcion());
        assertEquals(600, resultado[0].getPrecio());
    }
    
}
