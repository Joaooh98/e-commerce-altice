package com.altice.presentation.controller;

import java.util.List;

import com.altice.domain.dto.AddUserDTO;
import com.altice.domain.dto.ItemDTO;
import com.altice.domain.dto.ShoppingCartDTO;

import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

@Path("/shopping-cart")
public class ShoppingCartController extends AbstractController {

    @POST
    public Response persist(ShoppingCartDTO userDTO) {
        ShoppingCartDTO shoppingCart = shoppingCartService.create(userDTO);
        return Response.ok(shoppingCart).build();
    }

    @GET
    public Response findById(@QueryParam("id") String id) {
        ShoppingCartDTO shoppingCart = shoppingCartService.findById(id);
        return Response.ok(shoppingCart).build();
    }

    @DELETE
    public Response deleteById(@QueryParam("id") String id) {
        shoppingCartService.deleteById(id);
        return Response.ok().build();
    }

    @PATCH
    public Response updatedCard(ShoppingCartDTO cart, @QueryParam("id") String id, @QueryParam("updatedItems") boolean updatedItems) {
        return Response.ok(shoppingCartService.updatedById(cart, id, updatedItems)).build();
    }

    @PATCH
    @Path("clear")
    public Response clearCart(@QueryParam("id") String id) {
        return Response.ok(shoppingCartService.clearCart(id)).build();
    }

    @PATCH
    @Path("remove-item")
    public Response removeItems(List<ItemDTO> items, @QueryParam("id-cart") String id) {
        return Response.ok(shoppingCartService.removeItems(items, id)).build();
    }

    @PATCH
    @Path("add")
    public Response addItems(List<ItemDTO> items, @QueryParam("id-cart") String id) {
        return Response.ok(shoppingCartService.addItems(items, id)).build();
    }

    @PATCH
    @Path("add-quantity")
    public Response addQuantityItems(List<ItemDTO> items, @QueryParam("id-cart") String id) {
        return Response.ok(shoppingCartService.addQuantityItems(items, id)).build();
    }

    @PATCH
    @Path("add-user")
    public Response addUser(AddUserDTO inputAddUser) {
        return Response.ok(shoppingCartService.addUser(inputAddUser)).build();
    }
}
