package ca.usherbrooke.gegi.server.service;

import ca.usherbrooke.gegi.server.persistence.UserMapper;
import ca.usherbrooke.gegi.server.admin.Person;
import io.quarkus.security.User;

import javax.ws.rs.core.MediaType;


import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/api/users")
public class UserService {

    @Inject
    UserMapper userMapper;

    @GET
    @Path("/check")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkUser(String user) {
        Person existingUser = userMapper.selectUserByUsagerID(user);
        if (existingUser == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        else return Response.ok().build();
    }
    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(Person user){
        userMapper.insertUser(user);
        return Response.status(Response.Status.CREATED).entity(user).build();
    }


}
