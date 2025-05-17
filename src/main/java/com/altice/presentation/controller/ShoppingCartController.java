package com.altice.presentation.controller;

import com.altice.domain.dto.ProductDTO;
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
    @Path("/id")
    public Response deleteById(@QueryParam("id") String id) {
        shoppingCartService.deleteById(id);
        return Response.ok().build();
    }

    @PATCH
    @Path("/add-item")
    public Response addItem(ProductDTO productDto, @QueryParam("id_shopping_cart") String idShoppingCart) {
        return Response.ok(shoppingCartService.updatedById(productDto, idShoppingCart)).build();
    }
}
