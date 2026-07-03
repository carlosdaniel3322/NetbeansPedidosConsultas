CREATE TABLE IF NOT EXISTS productos (
    id INTEGER PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    precio NUMERIC(10, 2) NOT NULL
);

CREATE TABLE IF NOT EXISTS pedidos (
    id INTEGER PRIMARY KEY,
    fecha VARCHAR(20) NOT NULL,
    cliente VARCHAR(100) NOT NULL,
    estado VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS pedido_productos (
    id SERIAL PRIMARY KEY,
    pedido_id INTEGER NOT NULL REFERENCES pedidos(id) ON DELETE CASCADE,
    producto_id INTEGER NOT NULL REFERENCES productos(id),
    UNIQUE (pedido_id, producto_id)
);

CREATE TABLE IF NOT EXISTS tareas (
    id_tarea INTEGER PRIMARY KEY,
    id_pedido INTEGER NOT NULL REFERENCES pedidos(id) ON DELETE CASCADE,
    descripcion TEXT NOT NULL,
    fecha_creacion VARCHAR(20) NOT NULL,
    estado VARCHAR(50) NOT NULL
);
