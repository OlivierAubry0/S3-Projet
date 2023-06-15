package ca.usherbrooke.gegi.server.service;

import ca.usherbrooke.gegi.server.admin.Event;
import ca.usherbrooke.gegi.server.persistence.EventMapper;
import ca.usherbrooke.gegi.server.persistence.ImageMapper;
import org.apache.commons.io.IOUtils;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.annotations.Param;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.sql.Blob;
import java.util.List;
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

    @POST
    @Path("/uploadImage")
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    public Response uploadImage(Blob imageData) {
        // Generate a UUID for the image ID
        String imageId = UUID.randomUUID().toString();
        // Insert the image into the database using the mapper
        imageMapper.insertImage(imageId, imageData);
        return Response.status(Response.Status.CREATED).entity("Image uploaded successfully.").build();
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Event> getAllEvents() {
        return eventMapper.getAllEvents();
    }
}
