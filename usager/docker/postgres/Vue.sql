CREATE VIEW evenement_programmes AS
    SELECT *
FROM BASE_DE_DONNE.EVENEMENT;


CREATE VIEW places_reserves AS
    SELECT EvenementID, UsagerID, Telephone_Invite,Nom_Invite, Enregistration_Invite
FROM BASE_DE_DONNE.RESERVATION;


CREATE VIEW les_benevoles AS
    SELECT UsagerID, privilegeid
FROM BASE_DE_DONNE.USAGER_POSSEDE_PRIVILEGE;

CREATE OR REPLACE VIEW les_associations AS
SELECT ae.Asso_EtudianteID, ae.Asso_Etudiante_Nom, f.Faculte_Nom, ae.UsagerID
FROM BASE_DE_DONNE.ASSO_ETUDIANTE ae
JOIN BASE_DE_DONNE.FACULTE f ON ae.FaculteID = f.FaculteID;


----------------------comment appeler la vue ? ---------------------------------------------
--SELECT * FROM evenement_programmes;--
--SELECT * FROM places_reserves;--