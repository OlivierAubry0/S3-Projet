package ca.usherbrooke.gegi.server.service;
import ca.usherbrooke.gegi.server.admin.CheckIfUserReserved;
import ca.usherbrooke.gegi.server.admin.Reservation;
import ca.usherbrooke.gegi.server.persistence.ReservationMapper;
import org.apache.ibatis.annotations.Param;
import org.eclipse.microprofile.jwt.JsonWebToken;


import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

@Path("/api/reservations")
public class ReservationService {

    @Inject
    JsonWebToken jwt;

    @Inject
    ReservationMapper reservationMapper;

    @POST
    public Response createReservation(Reservation reservation) {
        reservationMapper.insertReservation(reservation);
        return Response.status(Response.Status.CREATED).entity(reservation).build();
    }

    @DELETE
    @Path("/deleteReservation")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteReservation(@QueryParam("EvenementID") String EvenementID, @QueryParam("UsagerID") String UsagerID) {
        try {
            reservationMapper.deleteReservation(EvenementID, UsagerID);
            return Response.status(Response.Status.CREATED).entity("La réservation a été supprimée avec succès.").build();
        } catch (Exception e) {
            return Response.status(Response.Status.METHOD_NOT_ALLOWED).entity(e).build();
        }
    }
    @POST
    @Path("/updateReservation")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateReservation(@QueryParam("NewUsagerID") String NewUsagerID, @QueryParam("EvenementID") String EvenementID, @QueryParam("UsagerID") String UsagerID) {
        try {
            reservationMapper.updateReservation(NewUsagerID,EvenementID, UsagerID);
            return Response.status(Response.Status.CREATED).entity("La réservation a été mise à jour avec succès.").build();
        } catch (Exception e) {
            return Response.status(Response.Status.METHOD_NOT_ALLOWED).entity(e).build();
        }
    }
}