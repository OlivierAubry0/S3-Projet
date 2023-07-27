package ca.usherbrooke.gegi.server.admin;

public class Reservation {
    public String EvenementID;
    public String UsagerID;
    public String Telephone_Invite;
    public String Nom_Invite;

    public boolean Enregistration_Invite;


    public Reservation() {
        // Default constructor
    }

    public Reservation(String EvenementID, String UsagerID, String Telephone_Invite, String Nom_Invite, boolean Enregistration_Invite) {
        this.EvenementID = EvenementID;
        this.UsagerID = UsagerID;
        this.Telephone_Invite = Telephone_Invite;
        this.Nom_Invite = Nom_Invite;
        this.Enregistration_Invite = Enregistration_Invite;
    }

    public void setEvenementID(String EvenementID) {
        this.EvenementID = EvenementID;
    }
    public void setUsagerID(String UsagerID) {
        this.UsagerID = UsagerID;
    }
    public void setTelephone_Invite(String Telephone_Invite) {
        this.Telephone_Invite = Telephone_Invite;
    }
    public void setNom_Invite(String Nom_Invite) {
        this.Nom_Invite = Nom_Invite;
    }
    public void setEnregistration_Invite(boolean Enregistration_Invite) {
        this.Enregistration_Invite = Enregistration_Invite;
    }

    public String getEvenementID() {
        return EvenementID;
    }

    public String getUsagerID() {
        return UsagerID;
    }

    public String getTelephone_Invite() {
        return Telephone_Invite;
    }

    public String getNom_Invite() {
        return Nom_Invite;
    }

    public boolean isEnregistration_Invite() {
        return Enregistration_Invite;
    }
}
