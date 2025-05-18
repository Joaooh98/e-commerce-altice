package com.altice.presentation.controller;

import java.util.List;

import com.altice.domain.dto.ProductDTO;

import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

@Path("/product")
public class ProductController extends AbstractController {

    @POST
    public Response persist(List<ProductDTO> products) {
        List<ProductDTO> response = productService.createProducts(products);
        return Response.ok(response).build();
    }

    @GET
    public Response findAll(@QueryParam("category") String category, @QueryParam("sub-category") String subCategory) {
        List<ProductDTO> products = productService.findAll(category, subCategory);
        return Response.ok(products).build();
    }

    @GET
    @Path("/id")
    public Response findById(@QueryParam("id") String id) {
        ProductDTO product = productService.findById(id);
        return Response.ok(product).build();
    }

    @DELETE
    @Path("/id")
    public Response deleteById(@QueryParam("id") String id) {
        productService.deleteById(id);
        return Response.ok().build();
    }

    @PATCH
    @Path("/id")
    public Response updatedById(ProductDTO productDto, @QueryParam("id") String id) {
        return Response.ok(productService.updatedById(productDto, id)).build();
    }

}
