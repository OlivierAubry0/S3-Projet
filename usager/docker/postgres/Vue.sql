CREATE VIEW evenement_programmes AS
    SELECT Allow_Guests, Description, EvenementID, Asso_EtudianteID
FROM EVENEMENT;


CREATE VIEW places_reserves AS
    SELECT EvenementID, UsagerID, Telephone_Invite,Nom_Invite
FROM reservation;


CREATE VIEW les_benevoles AS
    SELECT UsagerID, privilegeid
FROM usager_possede_privilege;



----------------------comment appeler la vue ? ---------------------------------------------
--SELECT * FROM evenement_programmes;--
--SELECT * FROM places_reserves;--