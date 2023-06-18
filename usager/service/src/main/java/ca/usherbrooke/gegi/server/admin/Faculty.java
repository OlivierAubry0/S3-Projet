package ca.usherbrooke.gegi.server.admin;

public class Faculty {
    private int FaculteID;
    private String Faculte_Nom;
    private int UniversiteID;

    public void setUniversiteID(int universiteID) {
        UniversiteID = universiteID;
    }

    public int getFaculteID() {
        return FaculteID;
    }

    public int getUniversiteID() {
        return UniversiteID;
    }

    public String getFaculte_Nom() {
        return Faculte_Nom;
    }

    public void setFaculte_Nom(String faculte_Nom) {
        Faculte_Nom = faculte_Nom;
    }

    public void setFaculteID(int faculteID) {
        FaculteID = faculteID;
    }
}
