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
        CartAnalyticsDTO analytics = analyticService.getCartAnalytics();
        return Response.ok(analytics).build();
    }

    @GET
    @Path("/items/statistics")
    public Response getItemStatistics() {
        ItemStatisticsDTO statistics = analyticService.getItemStatistics();
        return Response.ok(statistics).build();
    }

    @GET
    @Path("/items/top")
    public Response getTopItems(@QueryParam("count") Integer count) {
        TopItemsDTO topItems = analyticService.getTopItems(count);
        return Response.ok(topItems).build();
    }

    @GET
    @Path("/items/top5")
    public Response getTop5Items() {
        TopItemsDTO topItems = analyticService.getTop5Items();
        return Response.ok(topItems).build();
    }

    @GET
    @Path("/items/top10")
    public Response getTop10Items() {
        TopItemsDTO topItems = analyticService.getTop10Items();
        return Response.ok(topItems).build();
    }

    @GET
    @Path("/dashboard")
    public Response getDashboard(@QueryParam("topItemsCount") Integer topItemsCount) {
        CartAnalyticsDTO cartAnalytics = analyticService.getCartAnalytics();
        ItemStatisticsDTO itemStatistics = analyticService.getItemStatistics();
        TopItemsDTO topItems = analyticService.getTopItems(topItemsCount);

        DashboardResponseDTO dashboard = new DashboardResponseDTO();
        dashboard.setCartAnalytics(cartAnalytics);
        dashboard.setItemStatistics(itemStatistics);
        dashboard.setTopItems(topItems);

        return Response.ok(dashboard).build();
    }
}
