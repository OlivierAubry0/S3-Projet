package ca.usherbrooke.gegi.server.service;

import ca.usherbrooke.gegi.server.admin.Event;
import ca.usherbrooke.gegi.server.persistence.EventMapper;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

@Path("/api/events")
public class EventService {

    @Inject
    JsonWebToken jwt;

    @Inject
    EventMapper eventMapper;

    @POST
    public Response createEvent(Event event) {
        // Generate a UUID and set it as the eventId
        event.setEvenementID(UUID.randomUUID().toString());

        eventMapper.insertEvent(event);
        return Response.status(Response.Status.CREATED).entity(event).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Event> getAllEvents() {
        return eventMapper.getAllEvents();
    }
}
