DROP SCHEMA IF EXISTS BASE_DE_DONNE cascade;
CREATE SCHEMA BASE_DE_DONNE;
SET search_path = BASE_DE_DONNE, pg_catalog;

CREATE TABLE UNIVERSITE
(
    UniversiteID INT NOT NULL,
    Universite_Nom INT NOT NULL,
    PRIMARY KEY (UniversiteID)
);

CREATE TABLE ASSO_ETUDIANTE
(
    Asso_EtudianteID INT NOT NULL,
    _Asso_Etudiante__Nom INT NOT NULL,
    UniversiteID INT NOT NULL,
    PRIMARY KEY (Asso_EtudianteID),
    FOREIGN KEY (UniversiteID) REFERENCES UNIVERSITE(UniversiteID)
);

CREATE TABLE EVENEMENT
(
    EvenementID INT NOT NULL,
    Evenement_Nom INT NOT NULL,
    Evenement_Date INT NOT NULL,
    Evenement__Debut INT NOT NULL,
    Evenement_Fin INT NOT NULL,
    Asso_EtudianteID INT NOT NULL,
    PRIMARY KEY (EvenementID),
    FOREIGN KEY (Asso_EtudianteID) REFERENCES ASSO_ETUDIANTE(Asso_EtudianteID)
);

CREATE TABLE BILLET
(
    BilletID INT NOT NULL,
    Billet_Nom INT NOT NULL,
    PRIMARY KEY (BilletID)
);

CREATE TABLE INVITE
(
    InviteID INT NOT NULL,
    Invite_Nom INT NOT NULL,
    BilletID INT,
    PRIMARY KEY (InviteID),
    FOREIGN KEY (BilletID) REFERENCES BILLET(BilletID)
);

CREATE TABLE STATUT
(
    StatutID INT NOT NULL,
    Statut_Nom INT NOT NULL,
    PRIMARY KEY (StatutID)
);

CREATE TABLE PRIVILEGE
(
    PrivilegeID INT NOT NULL,
    _Privilege__Description INT NOT NULL,
    PRIMARY KEY (PrivilegeID)
);

CREATE TABLE VEND
(
    EvenementID INT NOT NULL,
    BilletID INT NOT NULL,
    PRIMARY KEY (EvenementID, BilletID),
    FOREIGN KEY (EvenementID) REFERENCES EVENEMENT(EvenementID),
    FOREIGN KEY (BilletID) REFERENCES BILLET(BilletID)
);

CREATE TABLE STATUT_PRIVILEGE
(
    StatutID INT NOT NULL,
    PrivilegeID INT NOT NULL,
    PRIMARY KEY (StatutID, PrivilegeID),
    FOREIGN KEY (StatutID) REFERENCES STATUT(StatutID),
    FOREIGN KEY (PrivilegeID) REFERENCES PRIVILEGE(PrivilegeID)
);

CREATE TABLE FACULTE
(
    FaculteID INT NOT NULL,
    Faculte_Nom INT NOT NULL,
    UniversiteID INT NOT NULL,
    PRIMARY KEY (FaculteID),
    FOREIGN KEY (UniversiteID) REFERENCES UNIVERSITE(UniversiteID)
);

CREATE TABLE USAGER
(
    UsagerID INT NOT NULL,
    Usager_Nom INT NOT NULL,
    Usager_Prenom INT NOT NULL,
    Usager_Photo INT NOT NULL,
    BillietID INT,
    InviteID INT,
    FaculteID INT,
    PRIMARY KEY (UsagerID),
    FOREIGN KEY (BillietID) REFERENCES BILLET(BilletID),
    FOREIGN KEY (InviteID) REFERENCES INVITE(InviteID),
    FOREIGN KEY (FaculteID) REFERENCES FACULTE(FaculteID)
);

CREATE TABLE ASSO_ETUDIANTE_FACULTE
(
    Asso_EtudanteID INT NOT NULL,
    FaculteID INT NOT NULL,
    PRIMARY KEY (Asso_EtudanteID, FaculteID),
    FOREIGN KEY (Asso_EtudanteID) REFERENCES ASSO_ETUDIANTE(Asso_EtudianteID),
    FOREIGN KEY (FaculteID) REFERENCES FACULTE(FaculteID)
);

CREATE TABLE USAGER_STATUT
(
    UsagerID INT NOT NULL,
    StatutID INT NOT NULL,
    PRIMARY KEY (UsagerID, StatutID),
    FOREIGN KEY (UsagerID) REFERENCES USAGER(UsagerID),
    FOREIGN KEY (StatutID) REFERENCES STATUT(StatutID)
);


