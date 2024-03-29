------------------------ Création de la fonction du trigger qui empeche le chevauchement des evenements-----------------------------
/*
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
        RAISE EXCEPTION 'Le meme evenement a deja ete cree! :(';
END IF;

RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER check_duplicate_evenement
    BEFORE INSERT ON Evenement
    FOR EACH ROW
    EXECUTE FUNCTION prevent_duplicate_evenement();*/
-----------------------Creation de la fonction qui regarde si le nombre de places est atteint--------------------------------------------
CREATE OR REPLACE FUNCTION prevent_nb_place()
    RETURNS TRIGGER
AS $$
DECLARE
people_count INT;
BEGIN
SELECT COUNT(usagerid)
INTO people_count
FROM BASE_DE_DONNE.RESERVATION WHERE evenementid=NEW.evenementid;
IF people_count >= (SELECT nombre_places FROM BASE_DE_DONNE.EVENEMENT WHERE evenementid=NEW.evenementid) THEN
        RAISE EXCEPTION 'Il ny a plus de places pour cet evenement! :(';
end if;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS check_prevent_nb_place ON BASE_DE_DONNE.RESERVATION;
CREATE TRIGGER check_prevent_nb_place
    BEFORE INSERT ON base_de_donne.RESERVATION
    FOR EACH ROW
    EXECUTE FUNCTION prevent_nb_place();
-----------------------Creation de la fonction qui regarde le nombre de places restantes--------------------------------------------
CREATE OR REPLACE FUNCTION placesLeft()
    RETURNS TRIGGER
AS $$
BEGIN
    UPDATE BASE_DE_DONNE.evenement
    SET Nombres_Places_Restantes = nombre_places - (SELECT COUNT(usagerid) FROM BASE_DE_DONNE.RESERVATION WHERE evenementid=NEW.evenementid)
    WHERE evenementid=new.evenementid;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS check_places_left ON BASE_DE_DONNE.reservation;
CREATE TRIGGER check_places_left
    AFTER INSERT OR DELETE OR UPDATE ON base_de_donne.reservation
    FOR EACH ROW
EXECUTE FUNCTION placesLeft();

-----------------------Creation de la fonction qui set le nombre de places restantes--------------------------------------------
CREATE OR REPLACE FUNCTION setplacesLeft()
    RETURNS TRIGGER
AS $$
BEGIN
    UPDATE BASE_DE_DONNE.evenement
    SET Nombres_Places_Restantes = nombre_places
    WHERE evenementid=new.evenementid;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS set_places_left ON BASE_DE_DONNE.evenement;
CREATE TRIGGER set_places_left
    AFTER INSERT ON base_de_donne.evenement
    FOR EACH ROW
EXECUTE FUNCTION setplacesLeft();
----------------------Creation de la fonction qui verifie si les invités sont acceptés---------------------------------
CREATE OR REPLACE FUNCTION accept_guests()
    RETURNS TRIGGER
AS $$
BEGIN
    IF (SELECT allow_guests FROM BASE_DE_DONNE.EVENEMENT WHERE evenementid=NEW.evenementid) = false THEN
        NEW.nom_invite := NULL;
        NEW.telephone_invite := NULL;
END IF;

RETURN NEW;
END;
$$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS check_guests ON BASE_DE_DONNE.RESERVATION;
CREATE TRIGGER check_guests
    BEFORE INSERT ON BASE_DE_DONNE.RESERVATION
    FOR EACH ROW
    EXECUTE FUNCTION accept_guests();

------------------------ Creation de la fonction qui regarde si l'invite apparait 2x ------------------------------------
CREATE OR REPLACE FUNCTION prevent_invitation()
    RETURNS TRIGGER
AS $$
DECLARE
invite_count INT;
BEGIN

SELECT COUNT(*)
INTO invite_count
FROM BASE_DE_DONNE.RESERVATION
WHERE Telephone_Invite = NEW.Telephone_Invite AND EvenementID = NEW.EvenementID;

    IF invite_count = 2 THEN
        UPDATE BASE_DE_DONNE.RESERVATION
        SET Enregistration_Invite = true
        WHERE Telephone_Invite = NEW.Telephone_Invite AND EvenementID = NEW.EvenementID;
    ELSIF invite_count > 2 THEN
        RAISE EXCEPTION 'Cette personne a deje ete invitee ! :)' ;
    ELSIF invite_count < 2 THEN
        UPDATE BASE_DE_DONNE.RESERVATION
        SET Enregistration_Invite = false
        WHERE Telephone_Invite = OLD.Telephone_Invite AND EvenementID = OLD.EvenementID;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS check_duplicate_invite ON BASE_DE_DONNE.RESERVATION;
CREATE TRIGGER check_duplicate_invite
    AFTER INSERT OR DELETE ON BASE_DE_DONNE.RESERVATION
    FOR EACH ROW
    EXECUTE FUNCTION prevent_invitation();
------------------------ Creation de la fonction qui regarde si le CIP transfert existe ------------------------------------
CREATE OR REPLACE FUNCTION prevent_transfer()
    RETURNS TRIGGER
AS $$
DECLARE
    user_existence INT;
BEGIN

    SELECT COUNT(*)
    INTO user_existence
    FROM BASE_DE_DONNE.usager
    WHERE usagerid = NEW.usagerid ;

    IF user_existence > 0 THEN
        RETURN NEW;
    ELSIF user_existence <= 0 THEN
        RAISE EXCEPTION 'Cette personne est inexistante :(' ;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS check_transfer_existence ON BASE_DE_DONNE.RESERVATION;
CREATE TRIGGER check_transfer_existence
    BEFORE UPDATE ON BASE_DE_DONNE.RESERVATION
    FOR EACH ROW
EXECUTE FUNCTION prevent_transfer();
-----------------Creation de la fonction qui assure qu'un usager ne peut pas reserver 2x pr un evenement---------------------------
/*CREATE OR REPLACE FUNCTION prevent_double_registration()
    RETURNS TRIGGER
AS $$
DECLARE
registration_count INT;
BEGIN

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
/* le trigger suivant est maintenant inutile
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
DROP TRIGGER IF EXISTS check_guest_infos ON BASE_DE_DONNE.RESERVATION;
CREATE TRIGGER check_guest_infos
    BEFORE INSERT ON BASE_DE_DONNE.RESERVATION
    FOR EACH ROW
    EXECUTE FUNCTION verify_guests_infos();
*/
