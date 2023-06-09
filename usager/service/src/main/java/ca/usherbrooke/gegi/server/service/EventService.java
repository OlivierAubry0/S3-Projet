package ca.usherbrooke.gegi.server.service;

import ca.usherbrooke.gegi.server.admin.Event;
import ca.usherbrooke.gegi.server.persistence.EventMapper;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Date;
import java.sql.Time;

@Path("/api/events")
public class EventService {

    @Inject
    JsonWebToken jwt;

    @Inject
    EventMapper eventMapper;

    @POST
    public Response createEvent(Event event) {
        eventMapper.insertEvent(event);
        return Response.status(Response.Status.CREATED).entity(event).build();
    }
}



