package ca.usherbrooke.gegi.server.service;

import ca.usherbrooke.gegi.server.admin.CheckIfUserReserved;
import ca.usherbrooke.gegi.server.admin.Event;
import ca.usherbrooke.gegi.server.admin.Reservation;
import ca.usherbrooke.gegi.server.admin.Scanning;
import ca.usherbrooke.gegi.server.persistence.EventMapper;
import ca.usherbrooke.gegi.server.persistence.ImageMapper;
import ca.usherbrooke.gegi.server.persistence.ReservationMapper;
import ca.usherbrooke.gegi.server.persistence.ScanningMapper;
import org.apache.commons.io.IOUtils;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.annotations.Param;
import ca.usherbrooke.gegi.server.admin.CheckMyEvents;

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

    @GET
    @Path("/events4genie")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Event> getEventsGenie() {
        return eventMapper.getEventsGenie();
    }

    @POST
    @Path("/MyEvents")
    @Produces(MediaType.APPLICATION_JSON)
    public Response CheckMyEvents(@QueryParam("UsagerID") String UsagerID) {
        List<CheckMyEvents> MyEvents = eventMapper.CheckMyEvents(UsagerID);
        if(MyEvents.isEmpty()){
            return Response.status(Response.Status.METHOD_NOT_ALLOWED).entity(UsagerID).build();
        }
        else{
            return Response.status(Response.Status.CREATED).entity(MyEvents).build();
        }
    }



}
