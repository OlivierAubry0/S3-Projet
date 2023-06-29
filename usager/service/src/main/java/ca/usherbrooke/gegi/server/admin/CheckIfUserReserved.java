package ca.usherbrooke.gegi.server.admin;

public class CheckIfUserReserved {
    public String UsagerID;
    public String Telephone_Invite;
    public String Nom_Invite;
    public String Enregistration_Invite;
    public CheckIfUserReserved(String UsagerID, String Telephone_Invite, String Nom_Invite, String Enregistration_Invite) {
        this.UsagerID = UsagerID;
        this.Telephone_Invite = Telephone_Invite;
        this.Nom_Invite = Nom_Invite;
        this.Enregistration_Invite = Enregistration_Invite;
    }
}
