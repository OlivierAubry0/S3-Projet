package ca.usherbrooke.gegi.server.admin;

public class AssoEtudiante {
    public String Asso_EtudianteID;
    public String Asso_Etudiante_Nom;
    public String FaculteID;

    public String Faculte_Nom;
    public String UsagerID;

    public AssoEtudiante() {
        // Default constructor
    }

    public AssoEtudiante(String Asso_EtudianteID, String Asso_Etudiante_Nom, String FaculteID, String Faculte_Nom, String UsagerID){
        this.Asso_EtudianteID = Asso_EtudianteID;
        this.Asso_Etudiante_Nom = Asso_Etudiante_Nom;
        this.FaculteID = FaculteID;
        this.Faculte_Nom = Faculte_Nom;
        this.UsagerID = UsagerID;
    }

    public void setAssoEtudianteID(String Asso_EtudianteID) {
        this.Asso_EtudianteID = Asso_EtudianteID;
    }

    public String getAssoEtudianteID() {
        return Asso_EtudianteID;
    }
    public void setAssoEtudianteNom(String Asso_Etudiante_Nom) {
        this.Asso_Etudiante_Nom = Asso_Etudiante_Nom;
    }

    public String getAssoEtudianteNom() {
        return Asso_Etudiante_Nom;
    }

    public void setFaculteID(String FaculteID){
        this.FaculteID = FaculteID;
    }
    public String getFaculteID(){
        return FaculteID;
    }

    public void setUsagerID(String UsagerID) {
        this.UsagerID = UsagerID;
    }
    public String getUsagerID(){
        return UsagerID;
    }
}