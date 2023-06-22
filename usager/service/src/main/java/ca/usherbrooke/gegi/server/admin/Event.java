package ca.usherbrooke.gegi.server.admin;

public class Event {
    private String evenementID;
    private String evenementNom;
    private String evenementDate;
    private String evenementDebut;
    private String evenementFin;
    private String assoEtudianteID;
    private String nombrePlaces;
    private boolean allowGuests;
    private String description;
    private String filename;

    public Event() {
        // Default constructor
    }

    public Event(String eventId, String eventName, String eventDate, String eventStart, String eventEnd, String studentAssociationId, String nombrePlaces, boolean allowGuests, String description, String filename) {
        this.evenementID = eventId;
        this.evenementNom = eventName;
        this.evenementDate = eventDate;
        this.evenementDebut = eventStart;
        this.evenementFin = eventEnd;
        this.assoEtudianteID = studentAssociationId;
        this.nombrePlaces = nombrePlaces;
        this.allowGuests = allowGuests;
        this.description = description;
        this.filename = filename;
    }

    public String getEvenementID() {
        return evenementID;
    }

    public void setEvenementID(String evenementID) {
        this.evenementID = evenementID;
    }

    public String getEvenementNom() {
        return evenementNom;
    }

    public void setEvenementNom(String evenementNom) {
        this.evenementNom = evenementNom;
    }

    public String getEvenementDate() {
        return evenementDate;
    }

    public void setEvenementDate(String evenementDate) {
        this.evenementDate = evenementDate;
    }

    public String getEvenementDebut() {
        return evenementDebut;
    }

    public void setEvenementDebut(String evenementDebut) {
        this.evenementDebut = evenementDebut;
    }

    public String getEvenementFin() {
        return evenementFin;
    }

    public void setEvenementFin(String evenementFin) {
        this.evenementFin = evenementFin;
    }

    public String getAssoEtudianteID() {
        return assoEtudianteID;
    }

    public void setAssoEtudianteID(String assoEtudianteID) {
        this.assoEtudianteID = assoEtudianteID;
    }

    public String getNombrePlaces() {
        return nombrePlaces;
    }

    public void setNombrePlaces(String nombrePlaces) {
        this.nombrePlaces = nombrePlaces;
    }

    public boolean isAllowGuests() {
        return allowGuests;
    }

    public void setAllowGuests(boolean allowGuests) {
        this.allowGuests = allowGuests;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
