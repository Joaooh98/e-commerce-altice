package com.altice.presentation.controller;

import com.altice.domain.dto.UserDTO;

import jakarta.validation.Valid;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Path("/api/v1/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Users", description = "User management operations")
public class UserController extends AbstractController {

    @POST
    @Operation(summary = "Create new user", description = "Creates a new user account with validation for email format and required fields")
    @APIResponse(responseCode = "200", description = "User created successfully", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = UserDTO.class)))
    @APIResponse(responseCode = "400", description = "Invalid input data - validation errors or duplicate email")
    @APIResponse(responseCode = "500", description = "Internal server error")
    public Response createUser(
            @RequestBody(description = "User data to create", required = true, content = @Content(schema = @Schema(implementation = UserDTO.class))) @Valid UserDTO userDTO) {
        try {
            UserDTO user = userService.create(userDTO);
            return Response.ok(user).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error create user")
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get user by ID", description = "Retrieves a specific user by their unique identifier")
    @APIResponse(responseCode = "200", description = "User found", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = UserDTO.class)))
    @APIResponse(responseCode = "400", description = "Invalid user ID format")
    @APIResponse(responseCode = "404", description = "User not found")
    public Response findUserById(
            @Parameter(description = "User unique identifier (UUID)", required = true, example = "123e4567-e89b-12d3-a456-426614174000") @PathParam("id") String id) {
        UserDTO user = userService.findById(id);
        return Response.ok(user).build();
    }

    @PATCH
    @Path("/{id}")
    @Operation(summary = "Update user", description = "Updates an existing user's information. Only provided fields will be updated.")
    @APIResponse(responseCode = "200", description = "User updated successfully", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = UserDTO.class)))
    @APIResponse(responseCode = "400", description = "Invalid input data or ID format")
    @APIResponse(responseCode = "404", description = "User not found")
    public Response updateUser(
            @Parameter(description = "User unique identifier (UUID)", required = true, example = "123e4567-e89b-12d3-a456-426614174000") @PathParam("id") String id,

            @RequestBody(description = "Updated user data", required = true, content = @Content(schema = @Schema(implementation = UserDTO.class))) @Valid UserDTO user) {
        UserDTO updatedUser = userService.updatedById(user, id);
        return Response.ok(updatedUser).build();
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete user", description = "Permanently deletes a user account from the system")
    @APIResponse(responseCode = "200", description = "User deleted successfully")
    @APIResponse(responseCode = "400", description = "Invalid user ID format")
    @APIResponse(responseCode = "404", description = "User not found")
    public Response deleteUser(
            @Parameter(description = "User unique identifier (UUID)", required = true, example = "123e4567-e89b-12d3-a456-426614174000") @PathParam("id") String id) {
        userService.deleteById(id);
        return Response.ok().build();
    }
}