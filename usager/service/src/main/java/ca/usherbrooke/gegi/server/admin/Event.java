package ca.usherbrooke.gegi.server.admin;

import java.sql.Date;
import java.sql.Time;

public class Event {
    private int eventId;
    private String eventName;
    private Date eventDate;
    private Time eventStart;
    private Time eventEnd;
    private int studentAssociationId;

    public Event(String eventName, Date eventDate, Time eventStart, Time eventEnd, int studentAssociationId) {
    }
}


