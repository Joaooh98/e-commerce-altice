package com.altice.presentation.controller;

import com.altice.domain.dto.CheckoutDTO;
import com.altice.domain.dto.PaymentDTO;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

@Path("/checkout")
public class CheckoutController extends AbstractController {

    @POST
    public Response createCheckout(@QueryParam("cart-id") String shoppingCartId) {
        CheckoutDTO checkout = checkoutService.createCheckout(shoppingCartId);
        return Response.ok(checkout).build();
    }

    @POST
    @Path("/payment")
    public Response processPayment(PaymentDTO paymentDTO) {
        CheckoutDTO checkout = checkoutService.processPayment(paymentDTO);
        return Response.ok(checkout).build();
    }

    @GET
    public Response findById(@QueryParam("id") String id) {
        CheckoutDTO checkout = checkoutService.findById(id);
        return Response.ok(checkout).build();
    }

}