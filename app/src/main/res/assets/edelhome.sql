DROP DATABASE IF EXISTS edelhome;
CREATE DATABASE edelhome;
USE edelhome;
DROP TABLE IF EXISTS usuario;
DROP TABLE IF EXISTS switch;
DROP TABLE IF EXISTS grupo_familia;

CREATE TABLE grupo_familia (
  group_id SMALLINT(5) AUTO_INCREMENT PRIMARY KEY NOT NULL
);

DROP TABLE IF EXISTS usuario;
CREATE TABLE usuario (
  user_id SMALLINT(6) AUTO_INCREMENT PRIMARY KEY NOT NULL,
  username VARCHAR (30) NOT NULL,
  email VARCHAR (50) NOT NULL,
  pass VARCHAR (50) NOT NULL,
  administrador BOOLEAN NOT NULL,
  group_id SMALLINT (5) NOT NULL,
  CONSTRAINT FOREIGN KEY fk_group_id(group_id)
    REFERENCES grupo_familia (group_id)
);

DROP TABLE IF EXISTS switch;
CREATE TABLE switch (
  switch_id SMALLINT (5) AUTO_INCREMENT PRIMARY KEY NOT NULL,
  place VARCHAR(20) NOT NULL,
  bulb_state BOOLEAN NOT NULL,
  group_id SMALLINT (5) NOT NULL,
  CONSTRAINT FOREIGN KEY fk_group_id2(group_id)
    REFERENCES grupo_familia (group_id)
);

------------ SELECCIONAR USUARIOS POR GRUPOS -----------
DROP PROCEDURE IF EXISTS getGroupUsers;
DELIMITER //
CREATE PROCEDURE getGroupUsers(
  IN id SMALLINT(5))
BEGIN
   SELECT * FROM usuario WHERE group_id = id;
END//

------------ CREACION DE USUARIO -----------
DROP PROCEDURE IF EXISTS createUser;
DELIMITER //
CREATE PROCEDURE createUser(
  IN var_username VARCHAR (30),
  IN var_email VARCHAR (50),
  IN var_pass VARCHAR (50),
  IN var_administrador BOOLEAN,
  IN var_group_id SMALLINT (5))
BEGIN
   INSERT INTO usuario VALUES(
     var_username,
     var_email,
     var_pass,
     var_administrador,
     var_group_id
   );
END//

------------ CREACION DE SWITCH -----------
DROP PROCEDURE IF EXISTS createSwitch;
DELIMITER //
CREATE PROCEDURE createSwitch(
  IN var_place VARCHAR (20),
  IN bulb_state BOOLEAN,
  IN group_id SMALLINT (5))
BEGIN
   INSERT INTO switch VALUES(
     var_place,
     bulb_state,
     var_group_id
   );
END//

------------ EDICIÓN DE SWITCH -----------
DROP PROCEDURE IF EXISTS editSwitch;
DELIMITER //
CREATE PROCEDURE editSwitch(
  IN var_switch_id SMALLINT (5),
  IN var_place VARCHAR (20))
BEGIN
   UPDATE switch SET place = var_place WHERE switch_id = var_switch_id;
END//

------------ CAMBIAR ESTADO DEL FOCO -----------
DROP PROCEDURE IF EXISTS editBulbState;
DELIMITER //
CREATE PROCEDURE editBulbState(
  IN var_switch_id SMALLINT (5),
  IN var_bulb_state BOOLEAN)
BEGIN
   UPDATE switch SET bulb_state = var_bulb_state WHERE switch_id = var_switch_id;
END//

------------ LEER ESTADO DEL FOCO -----------
DROP PROCEDURE IF EXISTS readBulbState;
DELIMITER //
CREATE PROCEDURE readBulbState(
  IN var_switch_id SMALLINT (5))
BEGIN
   SELECT bulb_state FROM switch WHERE switch_id = var_switch_id;
END//

------------ LEER ESTADO DEL FOCO -----------
DROP PROCEDURE IF EXISTS createGroup;
DELIMITER //
CREATE PROCEDURE createGroup()
BEGIN
   INSERT INTO grupo_familia VALUES ();
   SELECT last_insert_id();
END//

------------ CAMBIAR CONTRASEÑA USUARIO -----------
DROP PROCEDURE IF EXISTS changePassword;
DELIMITER //
CREATE PROCEDURE changePassword(
  IN var_user_id SMALLINT(5),
  IN new_pass VARCHAR(50))
BEGIN
   UPDATE usuario SET pass = new_pass WHERE user_id = var_user_id;
END//

------------ CAMBIAR DATOS USUARIO -----------
DROP PROCEDURE IF EXISTS updateUserData;
DELIMITER //
CREATE PROCEDURE updateUserData(
  IN var_user_id SMALLINT (5),
  IN var_username VARCHAR (30),
  IN var_email VARCHAR (50),
  IN var_administrador BOOLEAN)
BEGIN
   UPDATE usuario SET username = var_username AND
   email = var_email AND administrador = var_administrador
   WHERE user_id = var_user_id;
END//
