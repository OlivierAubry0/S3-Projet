package ca.usherbrooke.gegi.server.admin;

public class AssoEtudiante {
    public String Asso_EtudianteID;
    public String Asso_Etudiante_Nom;
    public int FaculteID;

    public AssoEtudiante() {
        // Default constructor
    }

    public AssoEtudiante(String Asso_EtudianteID, String Asso_Etudiante_Nom, int FaculteID){
        this.Asso_EtudianteID = Asso_EtudianteID;
        this.Asso_Etudiante_Nom = Asso_Etudiante_Nom;
        this.FaculteID = FaculteID;
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

    public void setFaculteID(int FaculteID){
        this.FaculteID = FaculteID;
    }
    public int getFaculteID(){
        return FaculteID;
    }
}