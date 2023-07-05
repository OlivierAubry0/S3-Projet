package ca.usherbrooke.gegi.server.service;

import ca.usherbrooke.gegi.server.admin.AssoEtudiante;
import ca.usherbrooke.gegi.server.persistence.AssoMapper;

import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

@Path("/api/Asso")
public class AssoService {

    @Inject
    JsonWebToken jwt;

    @Inject
    AssoMapper assomapper;
    @POST
    public Response createAsso(AssoEtudiante asso) {
        // Generate a UUID and set it as the eventId
        //asso.setAssoEtudianteID(UUID.randomUUID().toString());

        assomapper.insertAsso(asso);
        return Response.status(Response.Status.CREATED).entity(asso).build();
    }

    @GET
    @Path("/getAsso")
    @Produces(MediaType.APPLICATION_JSON)
    public List<AssoEtudiante> getAllAsso() {
        return assomapper.getAllAsso();
    }
}
