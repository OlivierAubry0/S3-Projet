DROP SCHEMA IF EXISTS BASE_DE_DONNE CASCADE;
CREATE SCHEMA BASE_DE_DONNE;
SET search_path = BASE_DE_DONNE, pg_catalog;

CREATE TABLE UNIVERSITE
(
    UniversiteID INT NOT NULL PRIMARY KEY ,
    Universite_Nom VARCHAR(50) NOT NULL

);


CREATE TABLE ASSO_ETUDIANTE
(
    Asso_EtudianteID INT NOT NULL,
    _Asso_Etudiante__Nom VARCHAR(100) NOT NULL,
    UniversiteID INT NOT NULL,
    PRIMARY KEY (Asso_EtudianteID),
    FOREIGN KEY (UniversiteID) REFERENCES UNIVERSITE(UniversiteID)
);


CREATE TABLE EVENEMENT
(
    EvenementID INT NOT NULL,
    Evenement_Nom VARCHAR(50) NOT NULL,
    Evenement_Debut timestamp NOT NULL,
    Evenement_Fin timestamp NOT NULL,
    Asso_EtudianteID INT NOT NULL,
    Nombre_Places INT NOT NULL,
    Allow_Guests BOOLEAN NOT NULL,
    Description VARCHAR(100),
    PRIMARY KEY (EvenementID),
    FOREIGN KEY (Asso_EtudianteID) REFERENCES ASSO_ETUDIANTE(Asso_EtudianteID)
);

CREATE TABLE PRIVILEGE
(
    PrivilegeID INT NOT NULL,
    _Privilege__Description VARCHAR(100) NOT NULL,
    PRIMARY KEY (PrivilegeID)
);



CREATE TABLE FACULTE
(
    FaculteID INT NOT NULL,
    Faculte_Nom VARCHAR(100) NOT NULL,
    UniversiteID INT NOT NULL,
    PRIMARY KEY (FaculteID),
    FOREIGN KEY (UniversiteID) REFERENCES UNIVERSITE(UniversiteID)
);

CREATE TABLE USAGER
(
    UsagerID INT NOT NULL,
    Usager_Nom VARCHAR(100) NOT NULL,
    Usager_Prenom VARCHAR(100) NOT NULL,
    Usager_Photo VARCHAR(100) NOT NULL,
    CodeQR INT NOT NULL,
    FaculteID INT NOT NULL,
    PRIMARY KEY (UsagerID),
    FOREIGN KEY (FaculteID) REFERENCES FACULTE(FaculteID)
);

CREATE TABLE USAGER_POSSEDE_PRIVILEGE
(
    UsagerID INT NOT NULL,
    PrivilegeID INT,
    PRIMARY KEY (UsagerID, PrivilegeID),
    FOREIGN KEY (UsagerID) REFERENCES USAGER(UsagerID),
    FOREIGN KEY (PrivilegeID) REFERENCES PRIVILEGE(PrivilegeID)
);

CREATE TABLE RESERVATION
(
    EvenementID INT NOT NULL,
    UsagerID INT NOT NULL,
    Telephone_Invite INT ,
    Nom_Invite VARCHAR(100),
    FOREIGN KEY (EvenementID) REFERENCES EVENEMENT(EvenementID),
    FOREIGN KEY (UsagerID) REFERENCES USAGER(UsagerID)
);

CREATE TABLE FACULTE_POSSEDE_ASSO
(
    Asso_EtudanteID INT NOT NULL,
    FaculteID INT NOT NULL,
    PRIMARY KEY (Asso_EtudanteID, FaculteID),
    FOREIGN KEY (Asso_EtudanteID) REFERENCES ASSO_ETUDIANTE(Asso_EtudianteID),
    FOREIGN KEY (FaculteID) REFERENCES FACULTE(FaculteID)
);
