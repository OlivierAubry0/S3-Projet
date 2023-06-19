package ca.usherbrooke.gegi.server.admin;

public class Reservation {
    private String EvenementID;
    private String UsagerID;
    private String Telephone_Invite;
    private String Nom_Invite;


    public Reservation() {
        // Default constructor
    }

    public Reservation(String EvenementID, String UsagerID, String Telephone_Invite, String Nom_Invite) {
        this.EvenementID = EvenementID;
        this.UsagerID = UsagerID;
        this.Telephone_Invite = Telephone_Invite;
        this.Nom_Invite = Nom_Invite;
    }

    public void setEvenementID(String EvenementID) {
        this.EvenementID = EvenementID;
    }
}