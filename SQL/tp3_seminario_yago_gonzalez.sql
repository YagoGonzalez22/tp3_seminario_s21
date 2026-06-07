 CREATE DATABASE turnos_medicos;
USE turnos_medicos;

CREATE TABLE usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre_usuario VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    rol VARCHAR(20) NOT NULL,
    email VARCHAR(100)
);

CREATE TABLE pacientes (
    id_paciente INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50),
    apellido VARCHAR(50),
    telefono VARCHAR(20),
    email VARCHAR(100),
    direccion VARCHAR(100)
);

CREATE TABLE medicos (
    id_medico INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50),
    apellido VARCHAR(50),
    telefono VARCHAR(20),
    email VARCHAR(100),
    especialidad VARCHAR(50)
);
CREATE TABLE turnos (
    id_turno INT AUTO_INCREMENT PRIMARY KEY,

    id_paciente INT,
    id_medico INT,

    fecha DATE,
    hora TIME,

    estado VARCHAR(20),

    FOREIGN KEY(id_paciente)
        REFERENCES pacientes(id_paciente),

    FOREIGN KEY(id_medico)
        REFERENCES medicos(id_medico)
);

