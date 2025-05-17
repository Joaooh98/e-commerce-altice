package com.altice.presentation.controller;

import com.altice.domain.dto.UserDTO;

import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

@Path("/user")
public class UserController extends AbstractController {

    @POST
    public Response persist(UserDTO userDTO) {
        UserDTO user = userService.create(userDTO);
        return Response.ok(user).build();
    }

    @GET
    public Response findById(@QueryParam("id") String id) {
        UserDTO user = userService.findById(id);
        return Response.ok(user).build();
    }

    @DELETE
    public Response deleteById(@QueryParam("id") String id) {
        userService.deleteById(id);
        return Response.ok().build();
    }

    @PATCH
    public Response updatedById(UserDTO user, @QueryParam("id") String id) {
        return Response.ok(userService.updatedById(user, id)).build();
    }
}
