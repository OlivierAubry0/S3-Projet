package ca.usherbrooke.gegi.server.admin;

public class University {
    private int UniversiteID;
    private String Universite_Nom;

    public int getUniversiteID(){
        return UniversiteID;
    }

    public String getUniversite_Nom(){
        return Universite_Nom;
    }

    public void setUniversite_Nom(String universite_Nom) {
        Universite_Nom = universite_Nom;
    }

    public void setUniversiteID(int universiteID) {
        UniversiteID = universiteID;
    }
}


