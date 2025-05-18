package com.altice.service;

import com.altice.domain.dto.CartAnalyticsDTO;
import com.altice.domain.dto.ItemStatisticsDTO;
import com.altice.domain.dto.TopItemsDTO;
import com.altice.domain.usecases.analytics.Analytics;
import com.altice.domain.usecases.analytics.CartAnalytics;
import com.altice.domain.usecases.analytics.ItemStatistics;

import io.quarkus.cache.CacheResult;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AnalyticService extends AbstractService {

    @CacheResult(cacheName = "cart-analytics")
    public CartAnalyticsDTO getCartAnalytics() {
        return new CartAnalytics(cartRepository).execute();
    }

    @CacheResult(cacheName = "item-statistics")
    public ItemStatisticsDTO getItemStatistics() {
        return new ItemStatistics(cartRepository).execute();
    }

    @CacheResult(cacheName = "top-items")
    public TopItemsDTO getTopItems(Integer topCount) {
        int count = (topCount != null && topCount > 0) ? topCount : 5;
        return new Analytics(cartRepository).execute(count);
    }

    public TopItemsDTO getTop5Items() {
        return getTopItems(5);
    }

    public TopItemsDTO getTop10Items() {
        return getTopItems(10);
    }
}