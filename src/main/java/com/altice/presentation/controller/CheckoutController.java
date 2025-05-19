package com.altice.presentation.controller;

import com.altice.domain.dto.CheckoutDTO;
import com.altice.domain.dto.PaymentDTO;

import jakarta.validation.Valid;
import jakarta.ws.rs.GET;
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

@Path("/api/v1/checkout")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Checkout", description = "Checkout and payment processing operations")
public class CheckoutController extends AbstractController {

    @POST
    @Path("/cart/{cartId}")
    @Operation(summary = "Create checkout", description = "Creates a checkout process for a shopping cart. The cart must have items and be associated with a user.")
    @APIResponse(responseCode = "200", description = "Checkout created successfully", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = CheckoutDTO.class)))
    @APIResponse(responseCode = "400", description = "Invalid cart ID, empty cart, or cart not associated with user")
    @APIResponse(responseCode = "404", description = "Shopping cart not found")
    @APIResponse(responseCode = "409", description = "Cart already checked out")
    public Response createCheckout(
            @Parameter(description = "Shopping cart unique identifier (UUID)", required = true, example = "123e4567-e89b-12d3-a456-426614174000") @PathParam("cartId") String shoppingCartId) {
        CheckoutDTO checkout = checkoutService.createCheckout(shoppingCartId);
        return Response.ok(checkout).build();
    }

    @POST
    @Path("/payment")
    @Operation(summary = "Process payment", description = "Processes payment for a checkout. Supports card and MBWay payment methods. On success, updates product stock and completes the order.")
    @APIResponse(responseCode = "200", description = "Payment processed successfully", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = CheckoutDTO.class)))
    @APIResponse(responseCode = "400", description = "Invalid payment data, checkout already completed, or insufficient stock")
    @APIResponse(responseCode = "404", description = "Checkout not found")
    @APIResponse(responseCode = "402", description = "Payment processing failed")
    public Response processPayment(
            @RequestBody(description = "Payment information including checkout ID and payment details", required = true, content = @Content(schema = @Schema(implementation = PaymentDTO.class))) @Valid PaymentDTO paymentDTO) {
        CheckoutDTO checkout = checkoutService.processPayment(paymentDTO);
        return Response.ok(checkout).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get checkout by ID", description = "Retrieves checkout information including status, amount, and payment URL")
    @APIResponse(responseCode = "200", description = "Checkout found", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = CheckoutDTO.class)))
    @APIResponse(responseCode = "400", description = "Invalid checkout ID format")
    @APIResponse(responseCode = "404", description = "Checkout not found")
    public Response findCheckoutById(
            @Parameter(description = "Checkout unique identifier (UUID)", required = true, example = "123e4567-e89b-12d3-a456-426614174000") @PathParam("id") String id) {
        CheckoutDTO checkout = checkoutService.findById(id);
        return Response.ok(checkout).build();
    }
}