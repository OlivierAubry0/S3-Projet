package ca.usherbrooke.gegi.server.service;

import ca.usherbrooke.gegi.server.admin.AssoEtudiante;
import ca.usherbrooke.gegi.server.persistence.AssoMapper;

import org.apache.ibatis.annotations.Delete;
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

    @DELETE
    public Response deleteAsso(@QueryParam("Asso_EtudianteID") String Asso_EtudianteID) {
        assomapper.deleteAsso(Asso_EtudianteID);
        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Path("/getAsso")
    @Produces(MediaType.APPLICATION_JSON)
    public List<AssoEtudiante> getAllAsso() {
        return assomapper.getAllAsso();
    }

    /*
        @POST
        @Path("/Asso_EtudianteID")
        @Produces(MediaType.APPLICATION_JSON)
        public Response updateAsso(@QueryParam("Asso_EtudianteID") String Asso_EtudianteID, AssoEtudiante updatedAsso) {
            AssoEtudiante asso = assomapper.getAssoById(Asso_EtudianteID);
            if (asso == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            // Update the values of the existing association
            asso.setAssoEtudianteNom(updatedAsso.getAssoEtudianteNom());
            asso.setFaculteID(updatedAsso.getFaculteID());
            asso.setUsagerID(updatedAsso.getUsagerID());

            assomapper.update_the_Asso(asso);
            return Response.status(Response.Status.OK).entity(asso).build();
        }
    */
    @POST
    @Path("/Asso_EtudianteID")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateAsso(@QueryParam("Asso_EtudianteID") String Asso_EtudianteID, @QueryParam("FaculteID") String FaculteID, @QueryParam("Asso_Etudiante_Nom") String Asso_Etudiante_Nom, @QueryParam("UsagerID") String UsagerID) {
        try {
            assomapper.update_the_Asso(Asso_EtudianteID, FaculteID, Asso_Etudiante_Nom, UsagerID);
            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            return Response.status(Response.Status.METHOD_NOT_ALLOWED).entity(Asso_Etudiante_Nom).build();
        }
    }
}
