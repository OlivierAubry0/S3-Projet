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

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
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

    public String getEventStart() {
        return eventStart;
    }

    public void setEventStart(String eventStart) {
        this.eventStart = eventStart;
    }

    public String getEventEnd() {
        return eventEnd;
    }

    public void setEventEnd(String eventEnd) {
        this.eventEnd = eventEnd;
    }

    public String getStudentAssociationId() {
        return studentAssociationId;
    }

    public void setStudentAssociationId(String studentAssociationId) {
        this.studentAssociationId = studentAssociationId;
    }

    public String getEventPlaces() {
        return eventPlaces;
    }

    public void setEventPlaces(String eventPlaces) {
        this.eventPlaces = eventPlaces;
    }

    public boolean isAllowGuests() {
        return allowGuests;
    }

    public void setAllowGuests(boolean allowGuests) {
        this.allowGuests = allowGuests;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }
}
