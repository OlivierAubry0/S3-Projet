package ca.usherbrooke.gegi.server.admin;

public class CheckMyEvents {
    public String Evenement_Nom;
    public String Evenement_Date;
    public String Nom_Invite;
    public String Enregistration_Invite;
    public CheckMyEvents(String Evenement_Nom, String Evenement_Date, String Nom_Invite, String Enregistration_Invite) {
        this.Evenement_Nom = Evenement_Nom;
        this.Evenement_Date = Evenement_Date;
        this.Nom_Invite = Nom_Invite;
        this.Enregistration_Invite = Enregistration_Invite;
    }
}
