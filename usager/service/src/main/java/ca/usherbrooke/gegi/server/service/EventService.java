//EventService
package ca.usherbrooke.gegi.server.service;

import ca.usherbrooke.gegi.server.admin.CheckIfUserReserved;
import ca.usherbrooke.gegi.server.admin.Event;
import ca.usherbrooke.gegi.server.admin.EventShowedToStudents;
import ca.usherbrooke.gegi.server.persistence.EventMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.annotations.Param;
import ca.usherbrooke.gegi.server.admin.CheckMyEvents;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@javax.ws.rs.Path("/api/events")
public class EventService {


    @ConfigProperty(name = "upload.directory") // Specify the directory to store the images
    String uploadDirectory;

    private static final String IMAGE_BASE_URL = "http://localhost:8080/";

    @Inject
    JsonWebToken jwt;

    @Inject
    EventMapper eventMapper;

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response createEvent(MultipartFormDataInput input) {
        Map<String, List<InputPart>> formDataMap = input.getFormDataMap();

        try {
            // Parse the event data from JSON
            String eventData = formDataMap.get("eventData").get(0).getBodyAsString();
            ObjectMapper objectMapper = new ObjectMapper();
            Event event = objectMapper.readValue(eventData, Event.class);


            // Generate a UUID and set it as the eventId
            event.setEvenementID(UUID.randomUUID().toString());
            String fileName = UUID.randomUUID().toString() + ".jpg";

            Path filePath = Path.of(uploadDirectory, fileName);

            List<InputPart> imageParts = formDataMap.get("image");
            if (imageParts != null && !imageParts.isEmpty()) {
                InputPart imagePart = imageParts.get(0);
                // Save the image file with the given name
                imagePart.getBody(InputStream.class, null).transferTo(Files.newOutputStream(filePath));

                // Extract the filename from the filePath
                Path filenamePath = filePath.getFileName();
                String filename = filenamePath.toString();

                // Assign the filename to the event object
                event.setFilename(filename);
            }

            // Insert the event and perform any necessary operations
            eventMapper.insertEvent(event);

            return Response.status(Response.Status.CREATED).entity(event).build();
        } catch (IOException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Event> getAllEvents() {
        List<Event> events = eventMapper.getAllEvents();
        return events;
    }

    @POST
    @javax.ws.rs.Path("/events4genie")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEvents(@QueryParam("FaculteID") String FaculteID) {
        List<EventShowedToStudents> Events = eventMapper.getEvents(FaculteID);
        return Response.status(Response.Status.CREATED).entity(Events).build();
    }
    @POST
    @javax.ws.rs.Path("/MyEvents")
    @Produces(MediaType.APPLICATION_JSON)
    public Response CheckMyEvents(@QueryParam("UsagerID") String UsagerID) {
        List<CheckMyEvents> MyEvents = eventMapper.CheckMyEvents(UsagerID);
        return Response.status(Response.Status.CREATED).entity(MyEvents).build();
    }
}

