CREATE VIEW evenement_programmes AS
    SELECT *
FROM base_de_donne.evenement;


CREATE VIEW places_reserves AS
    SELECT EvenementID, UsagerID, Telephone_Invite,Nom_Invite, Enregistration_Invite
FROM base_de_donne.reservation;


CREATE VIEW les_benevoles AS
    SELECT UsagerID, privilegeid
FROM base_de_donne.usager_possede_privilege;



----------------------comment appeler la vue ? ---------------------------------------------
--SELECT * FROM evenement_programmes;--
--SELECT * FROM places_reserves;--