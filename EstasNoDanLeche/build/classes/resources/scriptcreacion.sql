CREATE DATABASE PROYECTOAD;

USE PROYECTOAD;

CREATE TABLE PIEZA(
	COD_PIE VARCHAR(6) NOT NULL PRIMARY KEY,
	NOMBRE VARCHAR(20) NOT NULL,
	PRECIO FLOAT(7, 2) NOT NULL,
	DESCRIPCION TEXT
);

#INSERT INTOS DE PIEZAS
INSERT INTO PIEZA VALUES("A00001", "PIEZA 1", 12000.32, "DESCRIPCION DE LA PIEZA 1");
INSERT INTO PIEZA VALUES("A00002", "PIEZA 2", 12000.32, "DESCRIPCION DE LA PIEZA 2");
INSERT INTO PIEZA VALUES("A00003", "PIEZA 3", 55000.53, "DESCRIPCION DE LA PIEZA 3");
INSERT INTO PIEZA VALUES("A00004", "PIEZA 4", 72000.77, "DESCRIPCION DE LA PIEZA 4");

CREATE TABLE PROVEEDOR(
	COD_PROV VARCHAR(6) NOT NULL PRIMARY KEY,
	NOMBRE VARCHAR(20) NOT NULL,
	APELLIDOS VARCHAR(30) NOT NULL, 
	DIRECCION VARCHAR(40) NOT NULL
);

#INSERT INTOS DE PROVEEDORES
INSERT INTO PROVEEDOR VALUES("B00001", "NOM_PROV 1", "AP_PROV 1", "DIR_PROV 1");
INSERT INTO PROVEEDOR VALUES("B00002", "NOM_PROV 2", "AP_PROV 2", "DIR_PROV 2");
INSERT INTO PROVEEDOR VALUES("B00003", "NOM_PROV 3", "AP_PROV 3", "DIR_PROV 3");
INSERT INTO PROVEEDOR VALUES("B00004", "NOM_PROV 4", "AP_PROV 4", "DIR_PROV 4");

CREATE TABLE PROYECTO(
	COD_PROY VARCHAR(6) NOT NULL PRIMARY KEY,
	NOMBRE VARCHAR(40) NOT NULL,
	CIUDAD VARCHAR(40)
);

#INSERT INTOS DE PROYECTOS
INSERT INTO PROYECTO VALUES("C00001", "PROYECTO 1", "CIUDAD 1");
INSERT INTO PROYECTO VALUES("C00002", "PROYECTO 2", "CIUDAD 2");
INSERT INTO PROYECTO VALUES("C00003", "PROYECTO 3", "CIUDAD 3");
INSERT INTO PROYECTO VALUES("C00004", "PROYECTO 4", "CIUDAD 4");

CREATE TABLE GESTION(
	COD_PROV VARCHAR(6) NOT NULL,
	COD_PIE VARCHAR(6) NOT NULL,
	COD_PROY VARCHAR(6) NOT NULL,
	CANTIDAD FLOAT(7,2) NOT NULL,
	PRIMARY KEY(COD_PROV, COD_PIE, COD_PROY),
	FOREIGN KEY(COD_PROV) REFERENCES PROVEEDOR(COD_PROV),
	FOREIGN KEY(COD_PIE) REFERENCES PIEZA(COD_PIE),
	FOREIGN KEY(COD_PROY) REFERENCES PROYECTO(COD_PROY)
);

#INSERT INTOS DE GESTION
INSERT INTO GESTION VALUES("B00001", "A00001", "C00003", 1);
INSERT INTO GESTION VALUES("B00001", "A00002", "C00003", 2);
INSERT INTO GESTION VALUES("B00001", "A00003", "C00002", 4);
INSERT INTO GESTION VALUES("B00002", "A00001", "C00004", 32);
INSERT INTO GESTION VALUES("B00002", "A00002", "C00002", 44);
INSERT INTO GESTION VALUES("B00002", "A00003", "C00001", 3);
INSERT INTO GESTION VALUES("B00003", "A00001", "C00002", 13);
INSERT INTO GESTION VALUES("B00003", "A00002", "C00003", 4);
INSERT INTO GESTION VALUES("B00003", "A00004", "C00003", 23);
INSERT INTO GESTION VALUES("B00004", "A00001", "C00003", 1);
INSERT INTO GESTION VALUES("B00004", "A00004", "C00004", 6);