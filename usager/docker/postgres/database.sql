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
    UniversiteID VARCHAR(50) NOT NULL PRIMARY KEY ,
    Universite_Nom VARCHAR(50) NOT NULL

);

CREATE TABLE EVENEMENT
(
    EvenementID      VARCHAR(50) NOT NULL,
    Evenement_Nom    VARCHAR(50),
    Evenement_Date   VARCHAR(50) NOT NULL,
    Evenement_Debut  VARCHAR(50),
    Evenement_Fin    VARCHAR(50),
    Asso_EtudianteID VARCHAR(50),
    Nombre_Places INT CHECK ( Nombre_Places>=0 ),
    Nombres_Places_Restantes INT,
    Allow_Guests     BOOLEAN,
    Description      VARCHAR(2550),
    filename        VARCHAR(255),
    PRIMARY KEY (EvenementID)
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
    UniversiteID VARCHAR(50) NOT NULL,
    PRIMARY KEY (FaculteID),
    FOREIGN KEY (UniversiteID) REFERENCES UNIVERSITE(UniversiteID)
);

CREATE TABLE USAGER
(
    UsagerID VARCHAR(100) NOT NULL,
    Usager_Nom VARCHAR(100) NOT NULL,
    Usager_Prenom VARCHAR(100) NOT NULL,
    Usager_Photo VARCHAR(100),
    Usager_Role VARCHAR(100),
    CodeQR INT,
    FaculteID VARCHAR(50) NULL,
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
    UsagerID varchar(100) NOT NULL,
    PrivilegeID INT,
    PRIMARY KEY (UsagerID, PrivilegeID),
    FOREIGN KEY (UsagerID) REFERENCES USAGER(UsagerID),
    FOREIGN KEY (PrivilegeID) REFERENCES PRIVILEGE(PrivilegeID)
);

CREATE TABLE RESERVATION
(
    EvenementID VARCHAR(50),
    UsagerID VARCHAR(50),
    Telephone_Invite VARCHAR(10) NULL,
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
VALUES (1, 'Université de sd'),
       (2, 'Université sd');


INSERT INTO FACULTE (FaculteID, Faculte_Nom, UniversiteID)
VALUES (4028, 'Faculte de Genie', 1),
       (4038, 'Ecole de gestion', 1),
       (4029, 'Droit', 1),
       (4039, 'Education', 1),
       (4030, 'Lettres et sciences humaines', 1),
       (4040, 'Medecine et sciences de la sante', 1),
       (4031, 'Faculte des Sciences', 1),
       (4041, 'Sciences de lactivite physique', 1);

INSERT INTO USAGER (UsagerID, Usager_Nom, Usager_Prenom, Usager_Photo, CodeQR, FaculteID)
VALUES ('admin1', 'nomadmin', 'prenomadmin', 'photoadmin', 0102, 4028),
       ('admin2', 'nomadmin2', 'prenomadmin2', 'photoadmin2', 0103, 4028),
       ('admin3', 'nomadmin3', 'prenomadmin3', 'photoadmin3', 0104, 4028);


INSERT INTO ASSO_ETUDIANTE (Asso_EtudianteID, Asso_Etudiante_Nom, FaculteID, UsagerID)
VALUES (1, 'Association 1', 4028, 'admin1'),
       (2, 'Association 2', 4028, 'admin1'),
       (3, 'Association 3', 4028, 'admin1');

delete from UNIVERSITE where Universite_Nom='Université de Sherbrooke';

SELECT pg_encoding_to_char(encoding) FROM pg_database WHERE datname = 'base_de_donne';

