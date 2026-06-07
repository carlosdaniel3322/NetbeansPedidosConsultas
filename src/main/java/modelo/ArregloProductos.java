package modelo;
/**
 *
 * @author Admin
 */

public class ArregloProductos {
    public static Producto[] agregar(Producto[] arr, Producto nuevo) {
        Producto[] nuevoArr = new Producto[arr.length + 1];
        for (int i = 0; i < arr.length; i++)
            nuevoArr[i] = arr[i];

        nuevoArr[arr.length] = nuevo;
        return nuevoArr;
    }

    public static Producto[] eliminar(Producto[] arr, int id) {
        int count = 0;
        for (Producto p : arr)
            if (p.getId() != id) count++;

        Producto[] nuevoArr = new Producto[count];
        int index = 0;
        for (Producto p : arr)
            if (p.getId() != id)
                nuevoArr[index++] = p;

        return nuevoArr;
    }
}
