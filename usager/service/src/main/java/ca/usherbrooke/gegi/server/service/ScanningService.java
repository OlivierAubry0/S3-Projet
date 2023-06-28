package ca.usherbrooke.gegi.server.service;

import ca.usherbrooke.gegi.server.admin.Scanning;
import ca.usherbrooke.gegi.server.persistence.ScanningMapper;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.*;
import java.util.List;

@Path("/api/scanning")
public class ScanningService {
    @Inject
    JsonWebToken jwt;

    @Inject
    ScanningMapper scanningMapper;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkEventExistence(Scanning enterEventID) {
        String evenementID = enterEventID.getEvenementID();
        List<Scanning> eventsID = scanningMapper.checkEventExistence(evenementID);

        if (!eventsID.isEmpty()) {
            return Response.status(Response.Status.OK).entity("Evenement trouvé").build();
        } else {
            return Response.status(Response.Status.METHOD_NOT_ALLOWED).entity("Evenement non trouvé").build();
        }
    }
}