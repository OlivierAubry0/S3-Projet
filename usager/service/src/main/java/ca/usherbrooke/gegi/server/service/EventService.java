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
    @Path("/{edate}/{estart}/{eend}/{studentAssociationId}")
    public Response createEvent(@PathParam("edate") Date eventDate,
                                @PathParam("estart") Time eventStart,
                                @PathParam("eend") Time eventEnd,
                                @PathParam("studentAssociationId") int studentAssociationId,
                                @FormParam("ename") String eventName) {
        Event event = new Event(eventName, eventDate, eventStart, eventEnd, studentAssociationId);
        eventMapper.insertEvent(event);
        return Response.status(Response.Status.CREATED).entity(event).build();
    }
}
