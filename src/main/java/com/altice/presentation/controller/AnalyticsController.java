package com.altice.presentation.controller;

import com.altice.domain.dto.CartAnalyticsDTO;
import com.altice.domain.dto.ItemStatisticsDTO;
import com.altice.domain.dto.TopItemsDTO;
import com.altice.presentation.dto.DashboardResponseDTO;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Path("/api/v1/analytics")
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Analytics", description = "Business analytics and reporting operations")
public class AnalyticsController extends AbstractController {

    @GET
    @Path("/carts")
    @Operation(summary = "Get cart analytics", description = "Retrieves comprehensive analytics about shopping carts including total count, active carts, and conversion rates")
    @APIResponse(responseCode = "200", description = "Cart analytics retrieved successfully", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = CartAnalyticsDTO.class)))
    @APIResponse(responseCode = "500", description = "Internal server error while generating analytics")
    public Response getCartAnalytics() {
        try {
            CartAnalyticsDTO analytics = analyticService.getCartAnalytics();
            return Response.ok(analytics).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error getting cart analysis: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/items/statistics")
    @Operation(summary = "Get item statistics", description = "Retrieves statistical information about items in shopping carts including maximum, minimum, and average items per cart")
    @APIResponse(responseCode = "200", description = "Item statistics retrieved successfully", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ItemStatisticsDTO.class)))
    @APIResponse(responseCode = "500", description = "Internal server error while generating statistics")
    public Response getItemStatistics() {
        try {
            ItemStatisticsDTO statistics = analyticService.getItemStatistics();
            return Response.ok(statistics).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error getting item stats: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/items/top")
    @Operation(summary = "Get top selling items", description = "Retrieves the most popular items based on total quantity in shopping carts across all users")
    @APIResponse(responseCode = "200", description = "Top items retrieved successfully", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = TopItemsDTO.class)))
    @APIResponse(responseCode = "400", description = "Invalid count parameter")
    @APIResponse(responseCode = "500", description = "Internal server error while generating top items")
    public Response getTopItems(
            @Parameter(description = "Number of top items to retrieve (default: 5, maximum: 50)", example = "10") @QueryParam("count") Integer count) {
        try {
            TopItemsDTO topItems = analyticService.getTopItems(count);
            return Response.ok(topItems).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error getting top items: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/dashboard")
    @Operation(summary = "Get dashboard analytics", description = "Retrieves comprehensive dashboard data including cart analytics, item statistics, and top items in a single response")
    @APIResponse(responseCode = "200", description = "Dashboard analytics retrieved successfully", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = DashboardResponseDTO.class)))
    @APIResponse(responseCode = "400", description = "Invalid topItemsCount parameter")
    @APIResponse(responseCode = "500", description = "Internal server error while generating dashboard")
    public Response getDashboard(
            @Parameter(description = "Number of top items to include in dashboard (default: 5, maximum: 20)", example = "10") @QueryParam("topItemsCount") Integer topItemsCount) {
        try {
            DashboardResponseDTO dashboard = analyticService.getDashboardAnalytic(topItemsCount);
            return Response.ok(dashboard).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error getting dashboard: " + e.getMessage())
                    .build();
        }
    }
}