package ca.usherbrooke.gegi.server.admin;

public class EventShowedToStudents {
    public String evenementID;
    public String Evenement_Nom;
    public String Evenement_Date;
    public String Evenement_Debut;
    public String Evenement_Fin;
    public String Asso_EtudianteID;
    public int Nombre_Places;
    //private String nombrePlaces;
    public boolean Allow_Guests;
    public String Description;
    public String filename;
    public String Asso_Etudiante_Nom;
    public String FaculteID;
    public EventShowedToStudents() {
        // Default constructor
    }

    public EventShowedToStudents(String eventId, String eventName, String eventDate, String eventStart, String eventEnd, String studentAssociationId, String AssoName, String FacID, String nombrePlaces, boolean allowGuests, String description, String filename) {
        this.evenementID = eventId;
        this.Evenement_Nom = eventName;
        this.Evenement_Date = eventDate;
        this.Evenement_Debut = eventStart;
        this.Evenement_Fin = eventEnd;
        this.Asso_EtudianteID = studentAssociationId;
        this.Asso_Etudiante_Nom = AssoName;
        this.FaculteID = FacID;
        this.Nombre_Places = Integer.parseInt(nombrePlaces);
        this.Allow_Guests = allowGuests;
        this.Description = description;
        this.filename = filename;
    }

    public String getEvenementID() {
        return evenementID;
    }

    public void setEvenementID(String evenementID) {
        this.evenementID = evenementID;
    }

    public String getEvenementNom() {
        return Evenement_Nom;
    }

    public void setEvenementNom(String evenementNom) {
        this.Evenement_Nom = evenementNom;
    }

    public String getEvenementDate() {
        return Evenement_Date;
    }

    public void setEvenementDate(String evenementDate) {
        this.Evenement_Date = evenementDate;
    }

    public String getEvenementDebut() {
        return Evenement_Debut;
    }

    public void setEvenementDebut(String evenementDebut) {
        this.Evenement_Debut = evenementDebut;
    }

    public String getEvenementFin() {
        return Evenement_Fin;
    }

    public void setEvenementFin(String evenementFin) {
        this.Evenement_Fin = evenementFin;
    }

    public String getAssoEtudianteID() {
        return Asso_EtudianteID;
    }

    public void setAssoEtudianteID(String assoEtudianteID) {
        this.Asso_EtudianteID = assoEtudianteID;
    }
    /*
        public String getNombrePlaces() {
            return nombrePlaces;
        }

        public void setNombrePlaces(String nombrePlaces) {
            this.nombrePlaces = nombrePlaces;
        }*/
    public int getNombrePlaces() {
        return Nombre_Places;
    }

    public void setNombrePlaces(int nombrePlaces) {
        this.Nombre_Places = nombrePlaces;
    }
    public boolean isAllowGuests() {
        return Allow_Guests;
    }

    public void setAllowGuests(boolean Allow_Guests) {
        this.Allow_Guests = Allow_Guests;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        this.Description = description;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
