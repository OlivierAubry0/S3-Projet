

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
SELECT ae.Asso_EtudianteID, ae.Asso_Etudiante_Nom, f.Faculte_Nom, UsagerID
FROM base_de_donne.ASSO_ETUDIANTE ae
JOIN base_de_donne.Faculte f ON ae.FaculteID = f.FaculteID;

----------------------comment appeler la vue ? ---------------------------------------------
--SELECT * FROM evenement_programmes;--
--SELECT * FROM places_reserves;--