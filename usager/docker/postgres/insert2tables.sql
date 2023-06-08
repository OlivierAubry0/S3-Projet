INSERT INTO UNIVERSITE (UniversiteID, Universite_Nom)
VALUES (1, 'Université A'),
       (2, 'Université B'),
       (3, 'Université C');

INSERT INTO ASSO_ETUDIANTE (Asso_EtudianteID, _Asso_Etudiante__Nom, UniversiteID)
VALUES (1, 'Association 1', 1),
       (2, 'Association 2', 2),
       (3, 'Association 3', 3);

INSERT INTO EVENEMENT (EvenementID, Evenement_Nom, Evenement_Debut, Evenement_Fin, Asso_EtudianteID, Nombre_Places, Allow_Guests, Description)
VALUES (1, 'Événement 1', '2023-06-07 10:00:00', '2023-06-07 12:00:00', 1, 50, true, 'Description événement 1'),
       (2, 'Événement 2', '2023-06-08 14:00:00', '2023-06-08 16:00:00', 2, 30, false, 'Description événement 2'),
       (3, 'Événement 3', '2023-06-09 18:00:00', '2023-06-09 20:00:00', 3, 20, true, 'Description événement 3');

INSERT INTO PRIVILEGE (PrivilegeID, _Privilege__Description)
VALUES (1, 'Privilege 1'),
       (2, 'Privilege 2'),
       (3, 'Privilege 3');

INSERT INTO FACULTE (FaculteID, Faculte_Nom, UniversiteID)
VALUES (1, 'Faculté A', 1),
       (2, 'Faculté B', 2),
       (3, 'Faculté C', 3);

INSERT INTO USAGER (UsagerID, Usager_Nom, Usager_Prenom, Usager_Photo, CodeQR, FaculteID)
VALUES (1, 'Nom 1', 'Prénom 1', 'photo1.jpg', 12345, 1),
       (2, 'Nom 2', 'Prénom 2', 'photo2.jpg', 67890, 2),
       (3, 'Nom 3', 'Prénom 3', 'photo3.jpg', 54321, 3);

INSERT INTO USAGER_POSSEDE_PRIVILEGE (UsagerID, PrivilegeID)
VALUES (1, 1),
       (2, 2),
       (3, 3);

INSERT INTO RESERVATION (EvenementID, UsagerID, Telephone_Invite, Nom_Invite)
VALUES (1, 1, Null, Null),
       (2, 2, 987654321, 'Invité 2'),
       (3, 3, 987654321, 'Invité 2');

INSERT INTO FACULTE_POSSEDE_ASSO (Asso_EtudanteID, FaculteID)
VALUES (1, 1),
       (2, 2),
       (3, 3);