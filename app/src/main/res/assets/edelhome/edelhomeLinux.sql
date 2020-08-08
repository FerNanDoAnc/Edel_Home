-- phpMyAdmin SQL Dump
-- version 2.11.11.2
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tiempo de generación: 08-08-2020 a las 15:00:30
-- Versión del servidor: 5.5.50
-- Versión de PHP: 5.4.16

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

--
-- Base de datos: `edelhome`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `grupo_familia`
--

CREATE TABLE IF NOT EXISTS `grupo_familia` (
  `group_id` smallint(5) NOT NULL AUTO_INCREMENT,
  `group_name` varchar(30) NOT NULL,
  PRIMARY KEY (`group_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Volcar la base de datos para la tabla `grupo_familia`
--

INSERT INTO `grupo_familia` (`group_id`, `group_name`) VALUES
(1, 'edelhome'),
(2, 'Superalumnos');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `switch`
--

CREATE TABLE IF NOT EXISTS `switch` (
  `switch_id` smallint(5) NOT NULL AUTO_INCREMENT,
  `place` varchar(20) NOT NULL,
  `bulb_state` tinyint(1) NOT NULL,
  `group_id` smallint(5) NOT NULL,
  PRIMARY KEY (`switch_id`),
  KEY `fk_group_id2` (`group_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Volcar la base de datos para la tabla `switch`
--

INSERT INTO `switch` (`switch_id`, `place`, `bulb_state`, `group_id`) VALUES
(1, 'Cuarto de admin', 0, 1),
(2, 'Cuarto de user', 1, 1),
(3, 'Cocina', 0, 1),
(4, 'Cuarto del grupo 2', 0, 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE IF NOT EXISTS `usuario` (
  `user_id` smallint(6) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) NOT NULL,
  `email` varchar(50) NOT NULL,
  `pass` varchar(50) NOT NULL,
  `administrador` tinyint(1) NOT NULL,
  `group_id` smallint(5) NOT NULL,
  `last_login` char(10) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`),
  KEY `fk_group_id` (`group_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

--
-- Volcar la base de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`user_id`, `username`, `email`, `pass`, `administrador`, `group_id`, `last_login`) VALUES
(1, 'admin', 'admin@edelhome.com', '123456', 1, 1, '05/08/2020'),
(2, 'user', 'user@edelhome.com', '123456', 0, 1, NULL),
(3, 'johnDoe', 'edelhome@gmail.com', '12345', 1, 1, NULL),
(4, 'admin2', 'admin@edelhome.com', '1234562', 1, 2, NULL),
(5, 'user2', 'user@edelhome.com', '1234562', 0, 2, NULL),
(6, 'linux', 'linux', '123456', 0, 1, NULL);

--
-- Filtros para las tablas descargadas (dump)
--

--
-- Filtros para la tabla `switch`
--
ALTER TABLE `switch`
  ADD CONSTRAINT `switch_ibfk_1` FOREIGN KEY (`group_id`) REFERENCES `grupo_familia` (`group_id`);

--
-- Filtros para la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD CONSTRAINT `usuario_ibfk_1` FOREIGN KEY (`group_id`) REFERENCES `grupo_familia` (`group_id`);

DELIMITER $$
--
-- Procedimientos
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `changePassword`(
  IN var_user_id SMALLINT(5),
  IN var_new_pass VARCHAR(50),
  IN var_actual_pass VARCHAR(50))
BEGIN
   UPDATE usuario SET pass = var_new_pass WHERE user_id = var_user_id AND pass = var_actual_pass;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `createGroup`(
  IN var_group_name VARCHAR (30)
)
BEGIN
   INSERT INTO grupo_familia(group_name) VALUES(var_group_name);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `createSwitch`(
  IN var_place VARCHAR (20),
  IN var_bulb_state BOOLEAN,
  IN var_group_id SMALLINT (5))
BEGIN
   INSERT INTO switch(place,bulb_state,group_id) VALUES(
     var_place,
     var_bulb_state,
     var_group_id
   );
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `createUser`(
  IN var_username VARCHAR (30),
  IN var_email VARCHAR (50),
  IN var_pass VARCHAR (50),
  IN var_administrador BOOLEAN,
  IN var_group_id SMALLINT (5))
BEGIN
   INSERT INTO usuario(username,email,pass,administrador,group_id) VALUES(var_username,var_email,var_pass,var_administrador,var_group_id);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteUser`(
  IN var_user_id VARCHAR (30)
)
BEGIN
  DELETE FROM usuario WHERE user_id = var_user_id;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `doLogin`(
  IN user_name VARCHAR (30),
  IN var_pass VARCHAR (50),
  IN var_last_login CHAR(10)
)
BEGIN
  SELECT * FROM usuario WHERE username = user_name AND pass = var_pass;
  UPDATE usuario SET last_login = var_last_login WHERE username = user_name AND pass = var_pass;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `editBulbState`(
  IN var_switch_id SMALLINT (5),
  IN var_bulb_state BOOLEAN)
BEGIN
   UPDATE switch SET bulb_state = var_bulb_state WHERE switch_id = var_switch_id;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `editSwitch`(
  IN var_switch_id SMALLINT (5),
  IN var_place VARCHAR (20))
BEGIN
   UPDATE switch SET place = var_place WHERE switch_id = var_switch_id;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getBulbState`(
  IN var_switch_id SMALLINT (5))
BEGIN
   SELECT bulb_state FROM switch WHERE switch_id = var_switch_id;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getGroupSwitches`(
  IN var_group_id SMALLINT(5))
BEGIN
   SELECT * FROM switch WHERE group_id = var_group_id;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getGroupUsers`(
  IN var_id SMALLINT(5))
BEGIN
   SELECT * FROM usuario WHERE group_id = var_id;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `updateUserData`(
  IN var_user_id SMALLINT (5),
  IN var_username VARCHAR (30),
  IN var_email VARCHAR (50),
  IN var_administrador BOOLEAN)
BEGIN
   UPDATE usuario
   SET username = var_username , email = var_email , administrador = var_administrador
   WHERE user_id = var_user_id;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `verificateUser`(
  IN var_user_id VARCHAR (30),
  IN var_pass VARCHAR (50)
)
BEGIN
  SELECT * FROM usuario WHERE user_id = var_user_id AND pass = var_pass;
END$$

DELIMITER ;
