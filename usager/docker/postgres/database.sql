DROP SCHEMA IF EXISTS BASE_DE_DONNE CASCADE;
CREATE SCHEMA BASE_DE_DONNE;
ALTER SCHEMA BASE_DE_DONNE OWNER TO postgres;

SET search_path = BASE_DE_DONNE, pg_catalog;

CREATE TABLE UNIVERSITE
(
    UniversiteID INT NOT NULL PRIMARY KEY ,
    Universite_Nom VARCHAR(50) NOT NULL

);


CREATE TABLE ASSO_ETUDIANTE
(
    Asso_EtudianteID VARCHAR(50) NOT NULL,
    _Asso_Etudiante__Nom VARCHAR(100) NOT NULL,
    UniversiteID INT NOT NULL,
    PRIMARY KEY (Asso_EtudianteID),
    FOREIGN KEY (UniversiteID) REFERENCES UNIVERSITE(UniversiteID)
);


CREATE TABLE evenement
(
    EvenementID VARCHAR(50) NOT NULL,
    Evenement_Nom VARCHAR(50) NOT NULL,
    Evenement_Debut VARCHAR(50) NOT NULL,
    Evenement_Fin VARCHAR(50) NOT NULL,
    Asso_EtudianteID VARCHAR(50) NOT NULL,
    Nombre_Places VARCHAR(50) NOT NULL,
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
    EvenementID VARCHAR(50) NOT NULL,
    UsagerID INT NOT NULL,
    Telephone_Invite INT ,
    Nom_Invite VARCHAR(100),
    FOREIGN KEY (EvenementID) REFERENCES EVENEMENT(EvenementID),
    FOREIGN KEY (UsagerID) REFERENCES USAGER(UsagerID)
);

CREATE TABLE FACULTE_POSSEDE_ASSO
(
    Asso_EtudanteID VARCHAR(50) NOT NULL,
    FaculteID INT NOT NULL,
    PRIMARY KEY (Asso_EtudanteID, FaculteID),
    FOREIGN KEY (Asso_EtudanteID) REFERENCES ASSO_ETUDIANTE(Asso_EtudianteID),
    FOREIGN KEY (FaculteID) REFERENCES FACULTE(FaculteID)
);
---------------------------------
------------------------ Création de la fonction du trigger qui empeche le chevauchement des evenements-----------------------------

CREATE OR REPLACE FUNCTION prevent_duplicate_evenement()
    RETURNS TRIGGER
AS $$
DECLARE
    evenement_count INT;
BEGIN
    -- Vérifier si la plage horaire est déjà réservée
    SELECT COUNT(*)
    INTO evenement_count
    FROM EVENEMENT
    WHERE Evenement_Nom = NEW.Evenement_Nom
      AND (NEW.Evenement_Debut, NEW.Evenement_Fin) OVERLAPS (Evenement_Debut, Evenement_Fin);

    IF evenement_count > 0 THEN
        RAISE EXCEPTION 'Le même événement a déjà été créé! :(';
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;



CREATE TRIGGER check_duplicate_evenement
    BEFORE INSERT ON Evenement
    FOR EACH ROW
EXECUTE FUNCTION prevent_duplicate_evenement();
-----------------------Creation de la fonction qui regarde si le nombre de places est atteint--------------------------------------------
CREATE OR REPLACE FUNCTION prevent_nb_place()
    RETURNS TRIGGER
AS $$
DECLARE
    people_count INT;
BEGIN
    SELECT COUNT(usagerid)
    INTO people_count
    FROM RESERVATION WHERE evenementid=NEW.evenementid;
    IF people_count >= (SELECT nombre_places FROM evenement WHERE evenementid=NEW.evenementid) THEN
        RAISE EXCEPTION 'Il ny a plus de places pour cet evenement! :(';
    end if;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER check_prevent_nb_place
    BEFORE INSERT ON RESERVATION
    FOR EACH ROW
EXECUTE FUNCTION prevent_nb_place();

----------------------Creation de la fonction qui verifie si les invités sont acceptés---------------------------------
CREATE OR REPLACE FUNCTION accept_guests()
    RETURNS TRIGGER
AS $$
BEGIN
    IF (SELECT allow_guests FROM evenement WHERE evenementid=NEW.evenementid) = false THEN
        NEW.nom_invite := NULL;
        NEW.telephone_invite := NULL;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER check_guests
    BEFORE INSERT ON RESERVATION
    FOR EACH ROW
EXECUTE FUNCTION accept_guests();

------------------------ Creation de la fonction qui regarde si l'invite apparait 2x ------------------------------------
CREATE OR REPLACE FUNCTION prevent_invitation()
    RETURNS TRIGGER
AS $$
DECLARE
    invite_count INT;
BEGIN
    -- Vérifier si la plage horaire est déjà réservée
    SELECT COUNT(*)
    INTO invite_count
    FROM reservation
    WHERE Telephone_Invite = NEW.Telephone_Invite;

    IF invite_count >= 2 THEN
        RAISE EXCEPTION 'linvite a ete enregistre! :) ';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER check_duplicate_evenement
    AFTER INSERT ON reservation
    FOR EACH ROW
EXECUTE FUNCTION prevent_invitation();

-----------------Creation de la fonction qui assure qu'un usager ne peut pas reserver 2x pr un evenement---------------------------
CREATE OR REPLACE FUNCTION prevent_double_registration()
    RETURNS TRIGGER
AS $$
DECLARE
    registration_count INT;
BEGIN
    -- Vérifier si la plage horaire est déjà réservée
    SELECT COUNT(*)
    INTO registration_count
    FROM reservation
    WHERE UsagerId = NEW.UsagerID;

    IF registration_count >= 1 THEN
        RAISE EXCEPTION 'Vous etes deja inscrit pour cet evenement! :) ';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER check_double_registration
    BEFORE INSERT ON reservation
    FOR EACH ROW
EXECUTE FUNCTION prevent_double_registration();

----------------------Creation de la fonction qui verifie si les données de l'invité sont completes---------------------------------
CREATE OR REPLACE FUNCTION verify_guests_infos()
    RETURNS TRIGGER
AS $$
BEGIN
    IF NEW.nom_invite IS NULL AND NEW.telephone_invite IS NOT NULL THEN
        RAISE EXCEPTION 'Veuillez remplir les deux champs svp! :)';
    ELSIF NEW.nom_invite IS NOT NULL AND NEW.telephone_invite IS NULL THEN
        RAISE EXCEPTION 'Veuillez remplir les deux champs svp! :)';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER check_guest_infos
    BEFORE INSERT ON RESERVATION
    FOR EACH ROW
EXECUTE FUNCTION verify_guests_infos();

-----------------------------------
INSERT INTO UNIVERSITE (UniversiteID, Universite_Nom)
VALUES (1, 'Université A'),
       (2, 'Université B'),
       (3, 'Université C');

INSERT INTO ASSO_ETUDIANTE (Asso_EtudianteID, _Asso_Etudiante__Nom, UniversiteID)
VALUES (1, 'Association 1', 1),
       (2, 'Association 2', 2),
       (3, 'Association 3', 3);
