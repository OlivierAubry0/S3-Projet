package ca.usherbrooke.gegi.server.admin;

public class CheckMyEvents {
    public String EvenementID;
    public String Evenement_Nom;
    public String Evenement_Date;
    public String Nom_Invite;
    public boolean Enregistration_Invite;
    public CheckMyEvents(String EvenementID,String Evenement_Nom, String Evenement_Date, String Nom_Invite, boolean Enregistration_Invite) {
        this.EvenementID = EvenementID;
        this.Evenement_Nom = Evenement_Nom;
        this.Evenement_Date = Evenement_Date;
        this.Nom_Invite = Nom_Invite;
        this.Enregistration_Invite = Enregistration_Invite;
    }
}
