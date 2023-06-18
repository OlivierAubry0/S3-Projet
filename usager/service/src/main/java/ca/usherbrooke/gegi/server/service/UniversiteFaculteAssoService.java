package ca.usherbrooke.gegi.server.service;

import ca.usherbrooke.gegi.server.admin.Faculty;
import ca.usherbrooke.gegi.server.admin.University;
import ca.usherbrooke.gegi.server.persistence.FacultyMapper;
import ca.usherbrooke.gegi.server.persistence.UniversityMapper;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/api")
public class UniversiteFaculteAssoService {

    @Inject
    UniversityMapper universiteMapper;

    @Inject
    FacultyMapper faculteMapper;

    @GET
    @Path("/universities")
    public List<University> getAllUniversities() {
        return universiteMapper.getAllUniversities();
    }

    @GET
    @Path("/faculties")
    public List<Faculty> getFacultiesByUniversity(@QueryParam("universiteID") int universiteID) {
        return faculteMapper.getFacultiesByUniversity(universiteID);
    }
}
