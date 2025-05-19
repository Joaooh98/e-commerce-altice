package com.altice.presentation.controller;

import java.util.List;

import com.altice.domain.dto.AddUserDTO;
import com.altice.domain.dto.ItemDTO;
import com.altice.domain.dto.ShoppingCartDTO;

import jakarta.validation.Valid;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
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

@Path("/api/v1/shopping-carts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Shopping Carts", description = "Shopping cart management operations")
public class ShoppingCartController extends AbstractController {

    @POST
    @Operation(summary = "Create new shopping cart", description = "Creates a new shopping cart. Cart can be created with or without items and user.")
    @APIResponse(responseCode = "200", description = "Shopping cart created successfully", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ShoppingCartDTO.class)))
    @APIResponse(responseCode = "400", description = "Invalid input data - validation errors")
    @APIResponse(responseCode = "500", description = "Internal server error")
    public Response createShoppingCart(
            @RequestBody(description = "Shopping cart data", required = true) @Valid ShoppingCartDTO cartDTO) {
        ShoppingCartDTO shoppingCart = shoppingCartService.create(cartDTO);
        return Response.ok(shoppingCart).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get shopping cart by ID", description = "Retrieves a specific shopping cart with all its items and user information")
    @APIResponse(responseCode = "200", description = "Shopping cart found", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ShoppingCartDTO.class)))
    @APIResponse(responseCode = "400", description = "Invalid cart ID format")
    @APIResponse(responseCode = "404", description = "Shopping cart not found")
    public Response findShoppingCartById(
            @Parameter(description = "Shopping cart unique identifier (UUID)", required = true, example = "123e4567-e89b-12d3-a456-426614174000") @PathParam("id") String id) {
        ShoppingCartDTO shoppingCart = shoppingCartService.findById(id);
        return Response.ok(shoppingCart).build();
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Update shopping cart", description = "Updates an existing shopping cart completely. This replaces all cart data.")
    @APIResponse(responseCode = "200", description = "Shopping cart updated successfully", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ShoppingCartDTO.class)))
    @APIResponse(responseCode = "400", description = "Invalid input data or ID format")
    @APIResponse(responseCode = "404", description = "Shopping cart not found")
    public Response updateShoppingCart(
            @Parameter(description = "Shopping cart unique identifier (UUID)", required = true, example = "123e4567-e89b-12d3-a456-426614174000") @PathParam("id") String id,

            @RequestBody(description = "Updated shopping cart data", required = true) @Valid ShoppingCartDTO cart) {
        ShoppingCartDTO updatedCart = shoppingCartService.updatedById(cart, id);
        return Response.ok(updatedCart).build();
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete shopping cart", description = "Permanently deletes a shopping cart and all its items")
    @APIResponse(responseCode = "200", description = "Shopping cart deleted successfully")
    @APIResponse(responseCode = "400", description = "Invalid cart ID format")
    @APIResponse(responseCode = "404", description = "Shopping cart not found")
    public Response deleteShoppingCart(
            @Parameter(description = "Shopping cart unique identifier (UUID)", required = true, example = "123e4567-e89b-12d3-a456-426614174000") @PathParam("id") String id) {
        shoppingCartService.deleteById(id);
        return Response.ok().build();
    }

    @PATCH
    @Path("/{id}/clear")
    @Operation(summary = "Clear shopping cart", description = "Removes all items from the shopping cart, keeping the cart itself")
    @APIResponse(responseCode = "200", description = "Shopping cart cleared successfully", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ShoppingCartDTO.class)))
    @APIResponse(responseCode = "400", description = "Invalid cart ID format")
    @APIResponse(responseCode = "404", description = "Shopping cart not found")
    public Response clearShoppingCart(
            @Parameter(description = "Shopping cart unique identifier (UUID)", required = true, example = "123e4567-e89b-12d3-a456-426614174000") @PathParam("id") String id) {
        ShoppingCartDTO clearedCart = shoppingCartService.clearCart(id);
        return Response.ok(clearedCart).build();
    }

    @PATCH
    @Path("/{id}/items")
    @Operation(summary = "Add items to cart", description = "Adds new items to the shopping cart. Duplicates are not allowed.")
    @APIResponse(responseCode = "200", description = "Items added successfully", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ShoppingCartDTO.class)))
    @APIResponse(responseCode = "400", description = "Invalid data or item already exists in cart")
    @APIResponse(responseCode = "404", description = "Shopping cart or product not found")
    public Response addItemsToCart(
            @Parameter(description = "Shopping cart unique identifier (UUID)", required = true, example = "123e4567-e89b-12d3-a456-426614174000") @PathParam("id") String id,

            @RequestBody(description = "List of items to add to cart", required = true) @Valid List<ItemDTO> items) {
        ShoppingCartDTO updatedCart = shoppingCartService.addItems(items, id);
        return Response.ok(updatedCart).build();
    }

    @PATCH
    @Path("/{id}/items/remove")
    @Operation(summary = "Remove items from cart", description = "Removes specified items from the shopping cart by product ID")
    @APIResponse(responseCode = "200", description = "Items removed successfully", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ShoppingCartDTO.class)))
    @APIResponse(responseCode = "400", description = "Invalid data format")
    @APIResponse(responseCode = "404", description = "Shopping cart not found")
    public Response removeItemsFromCart(
            @Parameter(description = "Shopping cart unique identifier (UUID)", required = true, example = "123e4567-e89b-12d3-a456-426614174000") @PathParam("id") String id,

            @RequestBody(description = "List of items to remove from cart", required = true) @Valid List<ItemDTO> items) {
        ShoppingCartDTO updatedCart = shoppingCartService.removeItems(items, id);
        return Response.ok(updatedCart).build();
    }

    @PATCH
    @Path("/{id}/items/quantity")
    @Operation(summary = "Update item quantities", description = "Updates the quantity of existing items in the shopping cart")
    @APIResponse(responseCode = "200", description = "Item quantities updated successfully", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ShoppingCartDTO.class)))
    @APIResponse(responseCode = "400", description = "Invalid data or insufficient stock")
    @APIResponse(responseCode = "404", description = "Shopping cart or item not found")
    public Response updateItemQuantities(
            @Parameter(description = "Shopping cart unique identifier (UUID)", required = true, example = "123e4567-e89b-12d3-a456-426614174000") @PathParam("id") String id,

            @RequestBody(description = "List of items with new quantities", required = true) @Valid List<ItemDTO> items) {
        ShoppingCartDTO updatedCart = shoppingCartService.addQuantityItems(items, id);
        return Response.ok(updatedCart).build();
    }

    @PATCH
    @Path("/{id}/user")
    @Operation(summary = "Associate user with cart", description = "Associates a user with an existing shopping cart for checkout purposes")
    @APIResponse(responseCode = "200", description = "User associated successfully", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ShoppingCartDTO.class)))
    @APIResponse(responseCode = "400", description = "Invalid user or cart ID")
    @APIResponse(responseCode = "404", description = "Shopping cart or user not found")
    public Response addUserToCart(
            @RequestBody(description = "User and cart association data", required = true) @Valid AddUserDTO inputAddUser) {
        ShoppingCartDTO updatedCart = shoppingCartService.addUser(inputAddUser);
        return Response.ok(updatedCart).build();
    }
}