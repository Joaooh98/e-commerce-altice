package com.altice.presentation.controller;

import java.util.List;

import com.altice.domain.dto.ProductDTO;

import jakarta.validation.Valid;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Path("/api/v1/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Products", description = "Product management operations")
public class ProductController extends AbstractController {

    @POST
    @Operation(summary = "Create multiple products", description = "Creates multiple products in a single request. All products must have valid data.")
    @APIResponse(responseCode = "200", description = "Products created successfully", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ProductDTO.class)))
    @APIResponse(responseCode = "400", description = "Invalid input data - validation errors")
    @APIResponse(responseCode = "500", description = "Internal server error")
    public Response createProducts(
            @RequestBody(description = "List of products to create", required = true) @Valid List<ProductDTO> products) {
        List<ProductDTO> response = productService.createProducts(products);
        return Response.ok(response).build();
    }

    @GET
    @Operation(summary = "Get all products", description = "Retrieves all products with optional category and subcategory filtering. Results are cached for better performance.")
    @APIResponse(responseCode = "200", description = "Products retrieved successfully", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ProductDTO.class)))
    @APIResponse(responseCode = "400", description = "Invalid category or subcategory")
    public Response findAllProducts(
            @Parameter(description = "Filter by category (e.g., 'mobile phones', 'gaming', 'computing', 'televisions')", example = "mobile phones") @QueryParam("category") String category,

            @Parameter(description = "Filter by subcategory (e.g., 'smartphones', 'cases and covers')", example = "smartphones") @QueryParam("subCategory") String subCategory) {
        List<ProductDTO> products = productService.findAll(category, subCategory);
        return Response.ok(products).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get product by ID", description = "Retrieves a specific product by its unique identifier")
    @APIResponse(responseCode = "200", description = "Product found", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ProductDTO.class)))
    @APIResponse(responseCode = "400", description = "Invalid product ID format")
    @APIResponse(responseCode = "404", description = "Product not found")
    public Response findProductById(
            @Parameter(description = "Product unique identifier (UUID)", required = true, example = "123e4567-e89b-12d3-a456-426614174000") @PathParam("id") String id) {
        ProductDTO product = productService.findById(id);
        return Response.ok(product).build();
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Update product", description = "Updates an existing product with new information")
    @APIResponse(responseCode = "200", description = "Product updated successfully", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ProductDTO.class)))
    @APIResponse(responseCode = "400", description = "Invalid input data or ID format")
    @APIResponse(responseCode = "404", description = "Product not found")
    public Response updateProduct(
            @Parameter(description = "Product unique identifier (UUID)", required = true, example = "123e4567-e89b-12d3-a456-426614174000") @PathParam("id") String id,

            @RequestBody(description = "Updated product data", required = true) @Valid ProductDTO productDto) {
        ProductDTO updatedProduct = productService.updatedById(productDto, id);
        return Response.ok(updatedProduct).build();
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete product", description = "Permanently deletes a product from the system")
    @APIResponse(responseCode = "200", description = "Product deleted successfully")
    @APIResponse(responseCode = "400", description = "Invalid product ID format")
    @APIResponse(responseCode = "404", description = "Product not found")
    public Response deleteProduct(
            @Parameter(description = "Product unique identifier (UUID)", required = true, example = "123e4567-e89b-12d3-a456-426614174000") @PathParam("id") String id) {
        productService.deleteById(id);
        return Response.ok().build();
    }
}