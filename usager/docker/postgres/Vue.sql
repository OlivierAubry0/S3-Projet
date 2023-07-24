DROP VIEW IF EXISTS  evenement_programmes;
DROP VIEW IF EXISTS  places_reserves;
DROP VIEW IF EXISTS  les_benevoles;
DROP VIEW IF EXISTS  les_associations;
DROP VIEW IF EXISTS  MyEvents;
DROP VIEW IF EXISTS  les_assos;
DROP VIEW IF EXISTS  evenement_programmes_per_fac;

CREATE VIEW evenement_programmes AS
SELECT *
FROM base_de_donne.evenement;

CREATE VIEW evenement_programmes_per_fac AS
SELECT
    e.EvenementID,
    e.Evenement_Nom,
    e.Evenement_Date,
    e.Evenement_Debut,
    e.Evenement_Fin,
    e.Asso_EtudianteID,
    a.Asso_Etudiante_Nom,
    a.FaculteID,
    e.Nombre_Places,
    e.Allow_Guests,
    e.Description,
    e.filename
FROM
    EVENEMENT e
        JOIN
    ASSO_ETUDIANTE a ON e.Asso_EtudianteID = a.Asso_EtudianteID;

CREATE VIEW places_reserves AS
SELECT EvenementID, UsagerID, Telephone_Invite,Nom_Invite, Enregistration_Invite
FROM base_de_donne.reservation;


CREATE VIEW les_benevoles AS
SELECT UsagerID, privilegeid
FROM base_de_donne.usager_possede_privilege;

CREATE VIEW les_assos AS
SELECT Asso_EtudianteID, Asso_Etudiante_Nom, FaculteID
FROM base_de_donne.ASSO_ETUDIANTE;

CREATE VIEW les_associations AS
SELECT ae.Asso_EtudianteID, ae.Asso_Etudiante_Nom, f.Faculte_Nom, UsagerID
FROM base_de_donne.ASSO_ETUDIANTE ae
         JOIN base_de_donne.Faculte f ON ae.FaculteID = f.FaculteID;

CREATE VIEW MyEvents AS
SELECT UsagerID, reservation.EvenementID, Evenement_Nom,Evenement_Date, Nom_Invite, Enregistration_Invite
FROM base_de_donne.evenement JOIN base_de_donne.reservation ON evenement.evenementid = reservation.evenementid;
----------------------comment appeler la vue ? ---------------------------------------------
--SELECT * FROM evenement_programmes;--
--SELECT * FROM places_reserves;--