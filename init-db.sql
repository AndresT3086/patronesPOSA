-- Crear tabla clientes primero
CREATE TABLE IF NOT EXISTS clientes (
    id BIGSERIAL PRIMARY KEY,
    numero_documento VARCHAR(20) UNIQUE NOT NULL,
    nombre_completo VARCHAR(200) NOT NULL,
    email VARCHAR(100),
    telefono VARCHAR(20),
    direccion TEXT,
    ciudad VARCHAR(100),
    ramo VARCHAR(50) NOT NULL DEFAULT 'general',
    estado VARCHAR(20) NOT NULL DEFAULT 'activo',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Crear índices para optimizar consultas
CREATE INDEX IF NOT EXISTS idx_clientes_numero_documento ON clientes(numero_documento);
CREATE INDEX IF NOT EXISTS idx_clientes_estado ON clientes(estado);
CREATE INDEX IF NOT EXISTS idx_clientes_ramo ON clientes(ramo);
CREATE INDEX IF NOT EXISTS idx_clientes_email ON clientes(email);

-- Insertar datos de prueba DESPUÉS de crear la tabla
INSERT INTO clientes (numero_documento, nombre_completo, email, telefono, direccion, ciudad, ramo, estado) VALUES
('12345678', 'Juan Pérez', 'juan.perez@email.com', '3001111111', 'CALLE 100 # 20-30', 'BOGOTA', 'salud', 'activo'),
('87654321', 'María González', 'maria.gonzalez@email.com', '3002222222', 'CARRERA 50 # 45-60', 'MEDELLIN', 'movilidad', 'activo'),
('11223344', 'Carlos Rodríguez', 'carlos.rodriguez@email.com', '3003333333', 'AVENIDA 80 # 15-25', 'CALI', 'hogar', 'activo'),
('55667788', 'Ana Martínez', 'ana.martinez@email.com', '3004444444', 'CALLE 85 # 12-34', 'BARRANQUILLA', 'salud', 'activo'),
('99887766', 'Luis Fernández', 'luis.fernandez@email.com', '3005555555', 'CARRERA 15 # 67-89', 'CARTAGENA', 'movilidad', 'activo'),
('987654321', 'Cliente Prueba', 'test@example.com', '3001234567', 'DIRECCION DE PRUEBA', 'MEDELLIN', 'general', 'activo')
ON CONFLICT (numero_documento) DO NOTHING;

-- Verificar inserción
SELECT COUNT(*) as total_clientes FROM clientes;