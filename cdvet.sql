-- phpMyAdmin SQL Dump
-- version 4.8.4
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 27-03-2020 a las 21:07:02
-- Versión del servidor: 10.1.37-MariaDB
-- Versión de PHP: 7.3.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `cdvet`
--

DELIMITER $$
--
-- Procedimientos
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `agregarProductoStock` (IN `idP` INT, IN `cantidadIngresar` FLOAT)  BEGIN
  UPDATE productos SET stock = stock + cantidadIngresar WHERE id = idP;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `venderProductoStock` (IN `idP` INT, IN `cantidadVender` FLOAT)  BEGIN
  UPDATE productos SET stock = stock - cantidadVender WHERE id = idP;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categorias`
--

CREATE TABLE `categorias` (
  `id` smallint(5) UNSIGNED NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `descripcion` varchar(120) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `categorias`
--

INSERT INTO `categorias` (`id`, `nombre`, `descripcion`) VALUES
(1, 'Insecticidas', ''),
(2, 'shampoos', '');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detallefactura`
--

CREATE TABLE `detallefactura` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `factura` bigint(20) UNSIGNED DEFAULT NULL,
  `producto` mediumint(8) UNSIGNED DEFAULT NULL,
  `precioProducto` decimal(11,2) DEFAULT NULL,
  `cantidadProducto` decimal(11,2) UNSIGNED DEFAULT NULL,
  `totalVenta` decimal(11,2) UNSIGNED DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `detallefactura`
--

INSERT INTO `detallefactura` (`id`, `factura`, `producto`, `precioProducto`, `cantidadProducto`, `totalVenta`) VALUES
(6, 1, 1, '150.00', '2.00', '300.00'),
(7, 2, 1, '150.00', '1.00', '150.00'),
(8, 3, 1, '150.00', '1.00', '150.00'),
(9, 4, 1, '150.00', '1.00', '150.00'),
(10, 5, 1, '150.00', '1.00', '150.00'),
(11, 6, 1, '150.00', '1.00', '150.00'),
(12, 7, 1, '150.00', '1.00', '150.00'),
(13, 8, 1, '150.00', '1.00', '150.00'),
(14, 9, 1, '150.00', '1.00', '150.00'),
(15, 10, 1, '150.00', '1.00', '150.00'),
(16, 11, 1, '150.00', '2.00', '300.00'),
(17, 12, 1, '150.00', '1.00', '150.00'),
(18, 13, 1, '150.00', '1.00', '150.00'),
(19, 14, 1, '150.00', '1.00', '150.00'),
(20, 15, 1, '150.00', '1.00', '150.00'),
(21, 16, 1, '150.00', '1.00', '150.00'),
(22, 17, 1, '150.00', '1.00', '150.00'),
(23, 18, 1, '150.00', '1.00', '150.00'),
(24, 19, 1, '150.00', '2.00', '300.00'),
(25, 20, 1, '150.00', '2.00', '300.00'),
(26, 21, 1, '150.00', '4.00', '600.00'),
(27, 22, 1, '150.00', '1.00', '150.00'),
(28, 23, 1, '150.00', '1.00', '150.00'),
(29, 23, 1, '150.00', '1.00', '150.00'),
(30, 23, 1, '150.00', '1.00', '150.00'),
(31, 24, 1, '150.00', '1.00', '150.00'),
(32, 24, 1, '150.00', '1.00', '150.00'),
(33, 25, 1, '150.00', '1.00', '150.00'),
(34, 25, 1, '150.00', '1.00', '150.00'),
(35, 26, 2, '200.00', '1.00', '200.00'),
(36, 26, 1, '150.00', '1.00', '150.00'),
(37, 27, 1, '150.00', '1.00', '150.00'),
(38, 27, 2, '200.00', '1.00', '200.00'),
(39, 28, 1, '150.00', '1.00', '150.00'),
(40, 28, 2, '200.00', '2.00', '400.00'),
(41, 29, 1, '150.00', '2.00', '300.00'),
(42, 29, 2, '200.00', '1.00', '200.00'),
(43, 30, 2, '200.00', '2.00', '400.00'),
(44, 31, 1, '150.00', '1.00', '150.00'),
(45, 31, 2, '200.00', '3.00', '600.00'),
(46, 32, 1, '150.00', '1.00', '150.00'),
(47, 33, 2, '200.00', '1.00', '200.00'),
(48, 34, 1, '150.00', '1.00', '150.00'),
(49, 35, 1, '150.00', '5.00', '750.00'),
(50, 35, 2, '200.00', '2.00', '400.00'),
(51, 36, 1, '150.00', '1.00', '150.00'),
(52, 36, 2, '200.00', '2.00', '400.00'),
(53, 37, 3, '45.00', '2.00', '90.00'),
(54, 38, 2, '200.00', '1.00', '200.00'),
(55, 38, 1, '150.00', '1.00', '150.00'),
(56, 38, 3, '45.00', '1.00', '45.00');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `facturas`
--

CREATE TABLE `facturas` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `nombre_comprador` varchar(50) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `tipoVenta` smallint(5) UNSIGNED DEFAULT NULL,
  `impuestoISV` decimal(11,2) UNSIGNED DEFAULT NULL,
  `totalFactura` decimal(11,2) UNSIGNED DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `facturas`
--

INSERT INTO `facturas` (`id`, `nombre_comprador`, `fecha`, `tipoVenta`, `impuestoISV`, `totalFactura`) VALUES
(1, '', '2020-02-28', 1, '45.00', '300.00'),
(2, '', '2020-02-28', 1, '22.50', '150.00'),
(3, '', '2020-03-13', 2, '22.50', '150.00'),
(4, '', '2020-03-13', 2, '22.50', '150.00'),
(5, '', '2020-03-13', 2, '22.50', '150.00'),
(6, '', '2020-03-13', 2, '22.50', '150.00'),
(7, '', '2020-03-13', 2, '22.50', '150.00'),
(8, '', '2020-03-13', 2, '22.50', '150.00'),
(9, '', '2020-03-13', 2, '22.50', '150.00'),
(10, '', '2020-03-13', 2, '22.50', '150.00'),
(11, '', '2020-03-13', 2, '45.00', '300.00'),
(12, '', '2020-03-13', 2, '22.50', '150.00'),
(13, '', '2020-03-13', 2, '22.50', '150.00'),
(14, '', '2020-03-13', 2, '22.50', '150.00'),
(15, '', '2020-03-13', 2, '22.50', '150.00'),
(16, '', '2020-03-13', 2, '22.50', '150.00'),
(17, '', '2020-03-13', 2, '22.50', '150.00'),
(18, '', '2020-03-13', 2, '22.50', '150.00'),
(19, '', '2020-03-13', 2, '45.00', '300.00'),
(20, 'Cesar', '2020-03-13', 2, '45.00', '300.00'),
(21, 'Ramon', '2020-03-13', 2, '90.00', '600.00'),
(22, '', '2020-03-13', 2, '22.50', '150.00'),
(23, '', '2020-03-13', 2, '67.50', '450.00'),
(24, '', '2020-03-13', 2, '45.00', '300.00'),
(25, '', '2020-03-13', 2, '45.00', '300.00'),
(26, '', '2020-03-13', 2, '52.50', '350.00'),
(27, '', '2020-03-13', 2, '52.50', '350.00'),
(28, '', '2020-03-13', 2, '82.50', '550.00'),
(29, '', '2020-03-13', 2, '75.00', '500.00'),
(30, '', '2020-03-13', 2, '60.00', '400.00'),
(31, 'Danny', '2020-03-13', 2, '112.50', '750.00'),
(32, '', '2020-03-13', 2, '22.50', '150.00'),
(33, '', '2020-03-13', 2, '30.00', '200.00'),
(34, '', '2020-03-13', 2, '22.50', '150.00'),
(35, '', '2020-03-15', 2, '172.50', '1150.00'),
(36, '', '2020-03-15', 2, '82.50', '550.00'),
(37, '', '2020-03-15', 2, '13.50', '90.00'),
(38, '', '2020-03-15', 2, '59.25', '395.00');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `formapago`
--

CREATE TABLE `formapago` (
  `id` smallint(5) UNSIGNED NOT NULL,
  `tipoVenta` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `formapago`
--

INSERT INTO `formapago` (`id`, `tipoVenta`) VALUES
(1, 'Efectivo'),
(2, 'Efectivo');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `gastos`
--

CREATE TABLE `gastos` (
  `id` int(10) UNSIGNED NOT NULL,
  `monto` decimal(11,2) UNSIGNED DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `descripcion` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `gastos`
--

INSERT INTO `gastos` (`id`, `monto`, `fecha`, `descripcion`) VALUES
(1, '150.00', '2020-02-28', 'pago de energia electrica'),
(2, '150.00', '2020-03-15', 'compra de cable electrico'),
(3, '3500.00', '2020-03-15', 'compra de mercaderia');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `laboratorios`
--

CREATE TABLE `laboratorios` (
  `id` mediumint(8) UNSIGNED NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `laboratorios`
--

INSERT INTO `laboratorios` (`id`, `nombre`, `descripcion`) VALUES
(1, 'bink', '');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productos`
--

CREATE TABLE `productos` (
  `id` mediumint(8) UNSIGNED NOT NULL,
  `codigoBarra` varchar(20) DEFAULT NULL,
  `nombre` varchar(50) NOT NULL,
  `precioCompra` decimal(11,2) UNSIGNED NOT NULL,
  `precioVenta` decimal(11,2) UNSIGNED NOT NULL,
  `fechaVencimiento` date NOT NULL,
  `stock` decimal(10,2) UNSIGNED NOT NULL,
  `categoria` smallint(5) UNSIGNED DEFAULT NULL,
  `laboratorio` mediumint(8) UNSIGNED DEFAULT NULL,
  `ubicacion` varchar(120) DEFAULT NULL,
  `descripcion` varchar(120) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `productos`
--

INSERT INTO `productos` (`id`, `codigoBarra`, `nombre`, `precioCompra`, `precioVenta`, `fechaVencimiento`, `stock`, `categoria`, `laboratorio`, `ubicacion`, `descripcion`) VALUES
(1, '452132569852', 'Gramoxone', '100.00', '150.00', '2020-02-28', '99.00', 1, 1, '', ''),
(2, '452132568', 'Hierbicida', '125.00', '200.00', '2020-03-13', '84.00', 1, 1, '', ''),
(3, '4521256325', 'Shampoo', '25.00', '45.00', '2021-04-15', '21.00', 2, 1, '', '');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id` smallint(5) UNSIGNED NOT NULL,
  `nombreUsuario` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `permiso` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id`, `nombreUsuario`, `password`, `permiso`) VALUES
(1, 'cesar', '4957', 'Administrador');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `categorias`
--
ALTER TABLE `categorias`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `detallefactura`
--
ALTER TABLE `detallefactura`
  ADD PRIMARY KEY (`id`),
  ADD KEY `factura` (`factura`),
  ADD KEY `producto` (`producto`);

--
-- Indices de la tabla `facturas`
--
ALTER TABLE `facturas`
  ADD PRIMARY KEY (`id`),
  ADD KEY `tipoVenta` (`tipoVenta`);

--
-- Indices de la tabla `formapago`
--
ALTER TABLE `formapago`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `gastos`
--
ALTER TABLE `gastos`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `laboratorios`
--
ALTER TABLE `laboratorios`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `productos`
--
ALTER TABLE `productos`
  ADD PRIMARY KEY (`id`),
  ADD KEY `categoria` (`categoria`),
  ADD KEY `laboratorio` (`laboratorio`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `categorias`
--
ALTER TABLE `categorias`
  MODIFY `id` smallint(5) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `detallefactura`
--
ALTER TABLE `detallefactura`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=57;

--
-- AUTO_INCREMENT de la tabla `facturas`
--
ALTER TABLE `facturas`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=39;

--
-- AUTO_INCREMENT de la tabla `formapago`
--
ALTER TABLE `formapago`
  MODIFY `id` smallint(5) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `gastos`
--
ALTER TABLE `gastos`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `laboratorios`
--
ALTER TABLE `laboratorios`
  MODIFY `id` mediumint(8) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `productos`
--
ALTER TABLE `productos`
  MODIFY `id` mediumint(8) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id` smallint(5) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `detallefactura`
--
ALTER TABLE `detallefactura`
  ADD CONSTRAINT `detallefactura_ibfk_1` FOREIGN KEY (`factura`) REFERENCES `facturas` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `detallefactura_ibfk_2` FOREIGN KEY (`producto`) REFERENCES `productos` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `detallefactura_ibfk_3` FOREIGN KEY (`factura`) REFERENCES `facturas` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `detallefactura_ibfk_4` FOREIGN KEY (`producto`) REFERENCES `productos` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Filtros para la tabla `facturas`
--
ALTER TABLE `facturas`
  ADD CONSTRAINT `facturas_ibfk_1` FOREIGN KEY (`tipoVenta`) REFERENCES `formapago` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `facturas_ibfk_2` FOREIGN KEY (`tipoVenta`) REFERENCES `formapago` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Filtros para la tabla `productos`
--
ALTER TABLE `productos`
  ADD CONSTRAINT `productos_ibfk_1` FOREIGN KEY (`categoria`) REFERENCES `categorias` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `productos_ibfk_2` FOREIGN KEY (`laboratorio`) REFERENCES `laboratorios` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `productos_ibfk_3` FOREIGN KEY (`categoria`) REFERENCES `categorias` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `productos_ibfk_4` FOREIGN KEY (`laboratorio`) REFERENCES `laboratorios` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
