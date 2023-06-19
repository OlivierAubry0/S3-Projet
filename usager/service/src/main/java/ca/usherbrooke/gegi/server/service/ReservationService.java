package ca.usherbrooke.gegi.server.service;
import ca.usherbrooke.gegi.server.admin.Reservation;
import ca.usherbrooke.gegi.server.persistence.ReservationMapper;
import org.eclipse.microprofile.jwt.JsonWebToken;


import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.UUID;

@Path("/api/reservations")
public class ReservationService {

    @Inject
    JsonWebToken jwt;

    @Inject
    ReservationMapper reservationMapper;

    @POST
    public Response createReservation(Reservation reservation) {
        // Generate a UUID and set it as the eventId
        reservation.setEvenementID(UUID.randomUUID().toString());

        reservationMapper.insertReservation(reservation);
        return Response.status(Response.Status.CREATED).entity(reservation).build();
    }
}