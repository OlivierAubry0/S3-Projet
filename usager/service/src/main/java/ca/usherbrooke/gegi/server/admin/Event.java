package ca.usherbrooke.gegi.server.admin;

import java.sql.Date;
import java.sql.Time;

public class Event {
    private String eventId;
    private String eventName;
    private String eventDate;
    private String eventStart;
    private String eventEnd;
    private String studentAssociationId;
    private String eventPlaces;
    private boolean allowGuests;
    private String eventDescription;

    public Event() {
        // Default constructor
    }

    public Event(String eventId, String eventName, String eventDate, String eventStart, String eventEnd, String studentAssociationId, String nombrePlaces, boolean allowGuests, String description) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventStart = eventStart;
        this.eventEnd = eventEnd;
        this.studentAssociationId = studentAssociationId;
        this.eventPlaces = nombrePlaces;
        this.allowGuests = allowGuests;
        this.eventDescription = description;
    }

    public String getEventID() {
        return eventId;
    }

    public void setEventID(String eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventDebut() {
        return eventStart;
    }

    public void setEventDebut(String eventStart) {
        this.eventStart = eventStart;
    }

    public String getEventFin() {
        return eventEnd;
    }

    public void setEventFin(String eventEnd) {
        this.eventEnd = eventEnd;
    }

    public String getassoEtudianteID() {
        return studentAssociationId;
    }

    public void setassoEtudianteID(String studentAssociationId) {
        this.studentAssociationId = studentAssociationId;
    }

    public String getNombrePlaces() {
        return eventPlaces;
    }

    public void setNombresPlaces(String eventPlaces) {
        this.eventPlaces = eventPlaces;
    }

    public boolean isAllowGuests() {
        return allowGuests;
    }

    public void setAllowGuests(boolean allowGuests) {
        this.allowGuests = allowGuests;
    }

    public String getDescription() {
        return eventDescription;
    }

    public void setDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }
}
