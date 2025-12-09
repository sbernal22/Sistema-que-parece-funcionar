CREATE DATABASE IF NOT EXISTS banco;

USE banco;

CREATE TABLE cliente (
    dni VARCHAR(8) PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    telefono VARCHAR(15),
    direccion VARCHAR(100),
    nombre_usuario VARCHAR(50) NOT NULL UNIQUE,
    contrasenia VARCHAR(255) NOT NULL,
    estado BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE empleado (
    id VARCHAR(10) PRIMARY KEY,
    dni VARCHAR(8) NOT NULL UNIQUE,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    telefono VARCHAR(15),
    direccion VARCHAR(100),
    nombre_usuario VARCHAR(50) NOT NULL UNIQUE,
    contrasenia VARCHAR(255) NOT NULL,
    estado BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE administrador (
    dni VARCHAR(8) PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    telefono VARCHAR(15),
    direccion VARCHAR(100),
    nombre_usuario VARCHAR(50) NOT NULL UNIQUE,
    contrasenia VARCHAR(255) NOT NULL,
    estado BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE cuenta (
    numero_cuenta VARCHAR(20) PRIMARY KEY,
    moneda VARCHAR(10) NOT NULL,
    saldo DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
    dni_cliente VARCHAR(8) NOT NULL,
    FOREIGN KEY (dni_cliente) REFERENCES cliente(dni)
);

CREATE TABLE tarjeta (
    numero_tarjeta VARCHAR(16) PRIMARY KEY,
    compania VARCHAR(50) NOT NULL,
    tipo VARCHAR(10) NOT NULL,
    dni_cliente VARCHAR(8) NOT NULL,
    FOREIGN KEY (dni_cliente) REFERENCES cliente(dni)
);

CREATE TABLE cajero (
    id VARCHAR(10) PRIMARY KEY,
    disponible BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE transaccion (
    id INT AUTO_INCREMENT PRIMARY KEY,
    monto DECIMAL(10, 2) NOT NULL,
    descripcion VARCHAR(255),
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    tipo ENUM('DEPOSITO', 'RETIRO', 'TRANSFERENCIA') NOT NULL,
    cuenta_origen_numero VARCHAR(20) NOT NULL,
    cuenta_destino_numero VARCHAR(20),
    empleado_id VARCHAR(10),
    FOREIGN KEY (cuenta_origen_numero) REFERENCES cuenta(numero_cuenta),
    FOREIGN KEY (cuenta_destino_numero) REFERENCES cuenta(numero_cuenta),
    FOREIGN KEY (empleado_id) REFERENCES empleado(id)
);
