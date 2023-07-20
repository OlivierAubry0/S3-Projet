package ca.usherbrooke.gegi.server.service;

import ca.usherbrooke.gegi.server.admin.CheckIfUserReserved;
import ca.usherbrooke.gegi.server.persistence.ScanningMapper;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/api/scanning")
public class ScanningService {
    @Inject
    JsonWebToken jwt;

    @Inject
    ScanningMapper scanningMapper;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkEventExistence(@QueryParam("EvenementID") String EvenementID) {
        int eventCount = scanningMapper.checkEventExistence(EvenementID);

        if (eventCount > 0) {
            return Response.status(Response.Status.CREATED).entity("Evenement trouvé").build();
        } else {

            return Response.status(Response.Status.METHOD_NOT_ALLOWED).entity(EvenementID).build();
        }
    }

    @POST
    @Path("/checkUserReservation")
    @Produces(MediaType.APPLICATION_JSON)
    public Response CheckUserReservation(@QueryParam("EvenementID") String EvenementID, @QueryParam("UsagerID") String UsagerID) {
        List<CheckIfUserReserved> reservations = scanningMapper.CheckUserReservation(EvenementID, UsagerID);
        if (reservations.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT).entity(EvenementID).build();
        } else {
            return Response.status(Response.Status.CREATED).entity(reservations).build();
        }
    }

}