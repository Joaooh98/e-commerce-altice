package com.altice.service;

import com.altice.domain.dto.CartAnalyticsDTO;
import com.altice.domain.dto.ItemStatisticsDTO;
import com.altice.domain.dto.TopItemsDTO;
import com.altice.domain.usecases.analytics.Analytics;
import com.altice.domain.usecases.analytics.CartAnalytics;
import com.altice.domain.usecases.analytics.ItemStatistics;
import com.altice.presentation.dto.DashboardResponseDTO;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AnalyticService extends AbstractService {

    public CartAnalyticsDTO getCartAnalytics() {
        return new CartAnalytics(cartRepository).execute();
    }

    public ItemStatisticsDTO getItemStatistics() {
        return new ItemStatistics(cartRepository).execute();
    }

    public TopItemsDTO getTopItems(Integer topCount) {
        int count = (topCount != null && topCount > 0) ? topCount : 5;
        return new Analytics(cartRepository).execute(count);
    }

    public DashboardResponseDTO getDashboardAnalytic(Integer topCount) {
        CartAnalyticsDTO cartAnalytics = analyticService.getCartAnalytics();
        ItemStatisticsDTO itemStatistics = analyticService.getItemStatistics();
        TopItemsDTO topItems = analyticService.getTopItems(topCount);

        DashboardResponseDTO dashboard = new DashboardResponseDTO();
        dashboard.setCartAnalytics(cartAnalytics);
        dashboard.setItemStatistics(itemStatistics);
        dashboard.setTopItems(topItems);
        return dashboard;
    }

}