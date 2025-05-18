package com.altice.presentation.controller;

import com.altice.domain.dto.CartAnalyticsDTO;
import com.altice.domain.dto.ItemStatisticsDTO;
import com.altice.domain.dto.TopItemsDTO;
import com.altice.presentation.dto.DashboardResponseDTO;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

@Path("/analytics")
public class AnalyticsController extends AbstractController {

    @GET
    @Path("/carts")
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
    public Response getTopItems(@QueryParam("count") Integer count) {
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
    public Response getDashboard(@QueryParam("topItemsCount") Integer topItemsCount) {
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
