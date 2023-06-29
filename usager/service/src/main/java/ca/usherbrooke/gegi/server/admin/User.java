package ca.usherbrooke.gegi.server.admin;

import java.util.List;

public class User {

    private String UsagerID;
    private String Usager_Nom;
    private String Usager_Prenom;
    private String Usager_Role; // Assuming this is a list of roles
    private String FaculteID;

    // Getters and Setters

    public String getUsagerID() {
        return UsagerID;
    }

    public void setUsagerID(String UsagerID) {
        this.UsagerID = UsagerID;
    }

    public String getUsager_Nom() {
        return Usager_Nom;
    }

    public void setUsager_Nom(String Usager_Nom) {
        this.Usager_Nom = Usager_Nom;
    }

    public String getUsager_Prenom() {
        return Usager_Prenom;
    }

    public void setUsager_Prenom(String Usager_Prenom) {
        this.Usager_Prenom = Usager_Prenom;
    }

    public String getUsager_Role() {
        return Usager_Role;
    }

    public void setUsager_Role(String Usager_Role) {
        this.Usager_Role = Usager_Role;
    }

    public String getFaculteID() {
        return FaculteID;
    }

    public void setFaculteID(String FaculteID) {
        this.FaculteID = FaculteID;
    }
}
