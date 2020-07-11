CREATE DATABASE edelhome;
USE edelhome;

DROP TABLE IF EXISTS usuario;
CREATE TABLE usuario (
  id INT(6) AUTO_INCREMENT PRIMARY KEY,
  username varchar(30) NOT NULL,
  email varchar(50) NOT NULL,
  admin boolean NOT NULL
);

DROP TABLE IF EXISTS switch;
CREATE TABLE switch (
  id INT(6) AUTO_INCREMENT PRIMARY KEY,
  place VARCHAR(20) NOT NULL
);

DROP TABLE IF EXISTS bulb;
CREATE TABLE bulb (
  id INT(6) AUTO_INCREMENT PRIMARY KEY,
  bulb_state boolean NOT NULL
);