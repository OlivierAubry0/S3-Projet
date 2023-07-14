SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;


DROP SCHEMA IF EXISTS BASE_DE_DONNE CASCADE;
CREATE SCHEMA BASE_DE_DONNE;
ALTER SCHEMA BASE_DE_DONNE OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

SET search_path = BASE_DE_DONNE, pg_catalog;

CREATE TABLE UNIVERSITE
(
    UniversiteID INT NOT NULL PRIMARY KEY ,
    Universite_Nom VARCHAR(50) NOT NULL

);

CREATE TABLE EVENEMENT
(
    EvenementID      VARCHAR(50) NOT NULL,
    Evenement_Nom    VARCHAR(50),
    Evenement_Date   VARCHAR(50),
    Evenement_Debut  VARCHAR(50),
    Evenement_Fin    VARCHAR(50),
    Asso_EtudianteID VARCHAR(50),
    Nombre_Places INT,
    --Nombre_Places    VARCHAR(50),
    Allow_Guests     BOOLEAN,
    Description      VARCHAR(100),
    PRIMARY KEY (EvenementID)
);

CREATE TABLE images (
                        id VARCHAR(50) PRIMARY KEY,
                        data oid not null ,
                        created_at TIMESTAMPTZ DEFAULT NOW()
);

CREATE TABLE PRIVILEGE
(
    PrivilegeID INT NOT NULL,
    _Privilege__Description VARCHAR(100) NOT NULL,
    PRIMARY KEY (PrivilegeID)
);



CREATE TABLE FACULTE
(
    FaculteID VARCHAR(50) NOT NULL,
    Faculte_Nom VARCHAR(100) NOT NULL,
    UniversiteID INT NOT NULL,
    PRIMARY KEY (FaculteID),
    FOREIGN KEY (UniversiteID) REFERENCES UNIVERSITE(UniversiteID)
);

CREATE TABLE USAGER
(
    UsagerID VARCHAR(100) NOT NULL,
    Usager_Nom VARCHAR(100) NOT NULL,
    Usager_Prenom VARCHAR(100) NOT NULL,
    Usager_Photo VARCHAR(100) NOT NULL,
    CodeQR INT NOT NULL,
    FaculteID VARCHAR(50) NOT NULL,
    PRIMARY KEY (UsagerID),
    FOREIGN KEY (FaculteID) REFERENCES FACULTE(FaculteID)
);

CREATE TABLE ASSO_ETUDIANTE
(
    Asso_EtudianteID VARCHAR(50) NOT NULL,
    Asso_Etudiante_Nom VARCHAR(100) NOT NULL,
    FaculteID VARCHAR(50) NOT NULL,
    UsagerID VARCHAR(100) NOT NULL,
    PRIMARY KEY (Asso_EtudianteID),
    FOREIGN KEY (FaculteID) REFERENCES FACULTE(FaculteID),
    FOREIGN KEY (UsagerID) REFERENCES USAGER(UsagerID)
);

CREATE TABLE USAGER_POSSEDE_PRIVILEGE
(
    UsagerID VARCHAR(100) NOT NULL,
    PrivilegeID INT,
    PRIMARY KEY (UsagerID, PrivilegeID),
    FOREIGN KEY (UsagerID) REFERENCES USAGER(UsagerID),
    FOREIGN KEY (PrivilegeID) REFERENCES PRIVILEGE(PrivilegeID)
);

CREATE TABLE RESERVATION
(
    EvenementID VARCHAR(50),
    UsagerID VARCHAR(50),
    Telephone_Invite VARCHAR(11) NULL,
    Nom_Invite VARCHAR(50) NULL,
    Enregistration_Invite BOOLEAN,
    PRIMARY KEY (EvenementID, UsagerID),
   FOREIGN KEY (EvenementID) REFERENCES EVENEMENT(EvenementID)
   -- FOREIGN KEY (UsagerID) REFERENCES USAGER(UsagerID)
);

CREATE TABLE FACULTE_POSSEDE_ASSO
(
    Asso_EtudanteID VARCHAR(50) NOT NULL,
    FaculteID VARCHAR(50) NOT NULL,
    PRIMARY KEY (Asso_EtudanteID, FaculteID),
    FOREIGN KEY (Asso_EtudanteID) REFERENCES ASSO_ETUDIANTE(Asso_EtudianteID),
    FOREIGN KEY (FaculteID) REFERENCES FACULTE(FaculteID)
);
---------------------------------
------------------------ Création de la fonction du trigger qui empeche le chevauchement des evenements-----------------------------

-----------------------------------
INSERT INTO UNIVERSITE (UniversiteID, Universite_Nom)
VALUES (1, 'Université A'),
       (2, 'Université B'),
       (3, 'Université C');

INSERT INTO FACULTE (FaculteID, Faculte_Nom, UniversiteID)

VALUES (1, 'Science',1),
       (2, 'Medecine',1),
       (3, 'Droit',1);

INSERT INTO USAGER (UsagerID, Usager_Nom, Usager_Prenom, Usager_Photo, CodeQR, FaculteID)
VALUES  ('admin1', 'nomadmin', 'prenomadmin', 'photoadmin', 0102, 1);
