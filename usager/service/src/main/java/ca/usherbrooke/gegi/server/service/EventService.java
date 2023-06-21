package ca.usherbrooke.gegi.server.service;

import ca.usherbrooke.gegi.server.admin.Event;
import ca.usherbrooke.gegi.server.persistence.EventMapper;
import ca.usherbrooke.gegi.server.persistence.ImageMapper;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.UUID;


@Path("/api/events")
public class EventService {

    @Inject
    JsonWebToken jwt;

    @Inject
    EventMapper eventMapper;

    @Inject
    private ImageMapper imageMapper;

    @POST
    public Response createEvent(Event event) {
        // Generate a UUID and set it as the eventId
        event.setEvenementID(UUID.randomUUID().toString());

        eventMapper.insertEvent(event);
        return Response.status(Response.Status.CREATED).entity(event).build();
    }
}

