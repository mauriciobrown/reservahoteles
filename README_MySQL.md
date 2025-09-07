Este archivo contiene los script usados para crear las BD y los datos de ejmplos.





drop DATABASE if exists hotel_db;
CREATE DATABASE IF NOT EXISTS hotel_db;
use hotel_db;


-- Crear tabla HOTEL
CREATE TABLE HOTEL (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    ubicacion VARCHAR(100),
    estrellas INT,
    precio_minimo DECIMAL(10,2)
);

-- Crear tabla USUARIO
CREATE TABLE USUARIO (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    email VARCHAR(100),
    password VARCHAR(100)
);

-- Crear tabla HABITACION
CREATE TABLE HABITACION (
    id INT AUTO_INCREMENT PRIMARY KEY,
    hotel_id INT,
    numero VARCHAR(10),
    tipo VARCHAR(50),
    precio DECIMAL(10,2),
    FOREIGN KEY (hotel_id) REFERENCES HOTEL(id)
);

-- Crear tabla RESERVA
CREATE TABLE RESERVA (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT,
    habitacion_id INT,
    fecha_inicio DATE,
    fecha_fin DATE,
    FOREIGN KEY (usuario_id) REFERENCES USUARIO(id),
    FOREIGN KEY (habitacion_id) REFERENCES HABITACION(id)
);

********************************************

DELIMITER //

CREATE DEFINER=`root`@`localhost` PROCEDURE `buscar_hoteles`(IN destinoBuscado VARCHAR(100))
BEGIN
    IF destinoBuscado IS NULL OR destinoBuscado = '' THEN
        SELECT id, nombre, ubicacion, estrellas, precio_minimo
        FROM HOTEL;
    ELSE
        SELECT id, nombre, ubicacion, estrellas, precio_minimo
        FROM HOTEL
        WHERE LOWER(ubicacion) LIKE LOWER(CONCAT('%', destinoBuscado, '%'));
    END IF;
END//

DELIMITER ;
DELIMITER //

CREATE DEFINER=`root`@`localhost` PROCEDURE `buscar_habitaciones`(IN hotel INT)
BEGIN
    SELECT 
        h.id AS habitacion_id,
        h.numero,
        h.tipo,
        h.precio,
        h.hotel_id
    FROM HABITACION h
    WHERE h.hotel_id = hotel;
END//

DELIMITER ;

DELIMITER //

CREATE DEFINER=`root`@`localhost` PROCEDURE `ver_reservas`(IN usuarioId INT)
BEGIN
    SELECT 
        r.id AS reserva_id,
        r.fecha_inicio,
        r.fecha_fin,
        h.id AS habitacion_id,
        h.numero,
        h.tipo,
        h.precio,
        ho.id AS hotel_id,
        ho.nombre AS hotel_nombre,
        ho.ubicacion,
        ho.estrellas
    FROM RESERVA r
    JOIN HABITACION h ON r.habitacion_id = h.id
    JOIN HOTEL ho ON h.hotel_id = ho.id
    WHERE r.usuario_id = usuarioId;
END//

DELIMITER ;

DELIMITER //

CREATE DEFINER=`root`@`localhost` PROCEDURE `buscar_usuario`(IN correo VARCHAR(100))
BEGIN
    SELECT 
        id,
        nombre,
        email,
        password
    FROM USUARIO
    WHERE email = correo;
END//

DELIMITER ;
DELIMITER //

CREATE DEFINER=`root`@`localhost` PROCEDURE `verificar_disponibilidad`(
    IN p_habitacion_id INT,
    IN p_fecha_inicio DATE,
    IN p_fecha_fin DATE
)
BEGIN
    SELECT COUNT(*) AS total
    FROM RESERVA
    WHERE habitacion_id = p_habitacion_id
    AND (
        p_fecha_inicio BETWEEN fecha_inicio AND fecha_fin OR
        p_fecha_fin BETWEEN fecha_inicio AND fecha_fin OR
        fecha_inicio BETWEEN p_fecha_inicio AND p_fecha_fin OR
        fecha_fin BETWEEN p_fecha_inicio AND p_fecha_fin
    );
END//

DELIMITER ;
DELIMITER //

CREATE DEFINER=`root`@`localhost` PROCEDURE `validar_email`(
    IN p_email VARCHAR(100)
)
BEGIN
    SELECT COUNT(*) AS total
    FROM USUARIO
    WHERE email = p_email;
END//

DELIMITER ;
DELIMITER //



CREATE 
    ALGORITHM = UNDEFINED 
    DEFINER = `root`@`localhost` 
    SQL SECURITY DEFINER
VIEW `vista_hoteles_por_destino` AS
    SELECT 
        `hotel`.`id` AS `id`,
        `hotel`.`nombre` AS `nombre`,
        `hotel`.`ubicacion` AS `ubicacion`,
        `hotel`.`estrellas` AS `estrellas`,
        `hotel`.`precio_minimo` AS `precio_minimo`
    FROM
        `hotel`

***********************************************************

-- Insertar datos de prueba en HOTEL
INSERT INTO HOTEL (nombre, ubicacion, estrellas, precio_minimo)
VALUES 
('Grand Santiago Hotel', 'Santiago, Chile', 5, 120.00),
('Hotel Andes View', 'Valparaíso, Chile', 4, 85.00),
('Patagonia Lodge', 'Puerto Natales, Chile', 3, 60.00);

*******************************************************

-- Habitaciones para Grand Santiago Hotel (id = 1)
INSERT INTO HABITACION (hotel_id, numero, tipo, precio) VALUES
(1, '101', 'Individual', 120.00),
(1, '102', 'Doble', 150.00),
(1, '103', 'Suite', 200.00),
(1, '104', 'Individual', 125.00);

-- Habitaciones para Hotel Andes View (id = 2)
INSERT INTO HABITACION (hotel_id, numero, tipo, precio) VALUES
(2, '201', 'Individual', 85.00),
(2, '202', 'Doble', 110.00),
(2, '203', 'Suite', 160.00);

-- Habitaciones para Viña  (id = 3)
INSERT INTO HABITACION (hotel_id, numero, tipo, precio) VALUES
(3, '301', 'Individual', 60.00),
(3, '302', 'Doble', 80.00),
(3, '303', 'Suite', 120.00);



