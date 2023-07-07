DROP VIEW IF EXISTS  evenement_programmes;
DROP VIEW IF EXISTS  places_reserves;
DROP VIEW IF EXISTS  les_benevoles;
DROP VIEW IF EXISTS  les_associations;
DROP VIEW IF EXISTS  MyEvents;

CREATE VIEW evenement_programmes AS
    SELECT *
FROM base_de_donne.evenement;


CREATE VIEW places_reserves AS
    SELECT EvenementID, UsagerID, Telephone_Invite,Nom_Invite, Enregistration_Invite
FROM base_de_donne.reservation;


CREATE VIEW les_benevoles AS
    SELECT UsagerID, privilegeid
FROM base_de_donne.usager_possede_privilege;

CREATE VIEW les_associations AS
SELECT Asso_EtudianteID, Asso_Etudiante_Nom, FaculteID
FROM base_de_donne.ASSO_ETUDIANTE;

CREATE VIEW MyEvents AS
    SELECT UsagerID, reservation.EvenementID, Evenement_Nom,Evenement_Date, Nom_Invite, Enregistration_Invite
FROM base_de_donne.evenement JOIN base_de_donne.reservation ON evenement.evenementid = reservation.evenementid;
----------------------comment appeler la vue ? ---------------------------------------------
--SELECT * FROM evenement_programmes;--
--SELECT * FROM places_reserves;--