DROP DATABASE IF EXISTS  edelhome;
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
  username VARCHAR (30) NOT NULL UNIQUE,
  email VARCHAR (50) NOT NULL,
  pass VARCHAR (50) NOT NULL,
  administrador BOOLEAN NOT NULL,
  group_id SMALLINT (5) NOT NULL,
  last_login CHAR(10),
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

DROP PROCEDURE IF EXISTS getGroupUsers;
DELIMITER //
CREATE PROCEDURE getGroupUsers(
  IN var_id SMALLINT(5))
BEGIN
   SELECT * FROM usuario WHERE group_id = var_id;
END//

DROP PROCEDURE IF EXISTS createUser;
DELIMITER //
CREATE PROCEDURE createUser(
  IN var_username VARCHAR (30),
  IN var_email VARCHAR (50),
  IN var_pass VARCHAR (50),
  IN var_administrador BOOLEAN,
  IN var_group_id SMALLINT (5))
BEGIN
   INSERT INTO usuario(username,email,pass,administrador,group_id) VALUES(
     var_username,
     var_email,
     var_pass,
     var_administrador,
     var_group_id
   );
END//

DROP PROCEDURE IF EXISTS createSwitch;
DELIMITER //
CREATE PROCEDURE createSwitch(
  IN var_place VARCHAR (20),
  IN var_bulb_state BOOLEAN,
  IN var_group_id SMALLINT (5))
BEGIN
   INSERT INTO switch(place,bulb_state,group_id) VALUES(
     var_place,
     var_bulb_state,
     var_group_id
   );
END//

DROP PROCEDURE IF EXISTS editSwitch;
DELIMITER //
CREATE PROCEDURE editSwitch(
  IN var_switch_id SMALLINT (5),
  IN var_place VARCHAR (20))
BEGIN
   UPDATE switch SET place = var_place WHERE switch_id = var_switch_id;
END//

DROP PROCEDURE IF EXISTS editBulbState;
DELIMITER //
CREATE PROCEDURE editBulbState(
  IN var_switch_id SMALLINT (5),
  IN var_bulb_state BOOLEAN)
BEGIN
   UPDATE switch SET bulb_state = var_bulb_state WHERE switch_id = var_switch_id;
END//

DROP PROCEDURE IF EXISTS getBulbState;
DELIMITER //
CREATE PROCEDURE getBulbState(
  IN var_switch_id SMALLINT (5))
BEGIN
   SELECT bulb_state FROM switch WHERE switch_id = var_switch_id;
END//

DROP PROCEDURE IF EXISTS changePassword;
DELIMITER //
CREATE PROCEDURE changePassword(
  IN var_user_id SMALLINT(5),
  IN var_new_pass VARCHAR(50),
  IN var_actual_pass VARCHAR(50))
BEGIN
   UPDATE usuario SET pass = var_new_pass WHERE user_id = var_user_id AND pass = var_actual_pass;
END//

DROP PROCEDURE IF EXISTS updateUserData;
DELIMITER //
CREATE PROCEDURE updateUserData(
  IN var_user_id SMALLINT (5),
  IN var_username VARCHAR (30),
  IN var_email VARCHAR (50),
  IN var_administrador BOOLEAN)
BEGIN
   UPDATE usuario
   SET username = var_username , email = var_email , administrador = var_administrador
   WHERE user_id = var_user_id;
END//

DROP PROCEDURE IF EXISTS doLogin;
DELIMITER //
CREATE PROCEDURE doLogin(
  IN user_name VARCHAR (30),
  IN var_pass VARCHAR (50),
  IN var_last_login CHAR(10)
)
BEGIN
  SELECT * FROM usuario WHERE username = user_name AND pass = var_pass;
  UPDATE usuario SET last_login = var_last_login WHERE username = user_name AND pass = var_pass;
END //

DROP PROCEDURE IF EXISTS verificateUser;
DELIMITER //
CREATE PROCEDURE verificateUser(
  IN var_user_id VARCHAR (30),
  IN var_pass VARCHAR (50)
)
BEGIN
  SELECT * FROM usuario WHERE user_id = var_user_id AND pass = var_pass;
END //

-- TO WORK --
DROP PROCEDURE IF EXISTS createGroup;
DELIMITER //
CREATE PROCEDURE createGroup()
BEGIN
   INSERT INTO grupo_familia VALUES ();
END//

-- CREANDO PRIMER USUARIO --


use edelhome;
call createGroup();
call createUser('admin','admin@edelhome.com','12345',true,1)
call createUser('user','user@edelhome.com','12345',false,1)