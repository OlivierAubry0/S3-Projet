package ca.usherbrooke.gegi.server.service;

import ca.usherbrooke.gegi.server.admin.Event;
import ca.usherbrooke.gegi.server.persistence.EventMapper;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.inject.Inject;
import java.sql.Date;
import java.sql.Time;

@Path("/api/events")
@Produces({"application/json"})
public class EventService {
    @Inject
    EventMapper eventMapper;

    @POST
    @Path("/api/events")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response createEvent(@FormParam("ename") String eventName,
                                @FormParam("edate") Date eventDate,
                                @FormParam("estart") Time eventStart,
                                @FormParam("eend") Time eventEnd,
                                @FormParam("studentAssociationId") int studentAssociationId) {
        Event event = new Event(eventName, eventDate, eventStart, eventEnd, studentAssociationId);
        eventMapper.insertEvent(event);
        return Response.status(Response.Status.CREATED).entity(event).build();
    }
}


