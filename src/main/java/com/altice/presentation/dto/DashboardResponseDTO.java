package com.altice.presentation.dto;

import com.altice.domain.dto.CartAnalyticsDTO;
import com.altice.domain.dto.ItemStatisticsDTO;
import com.altice.domain.dto.TopItemsDTO;

public class DashboardResponseDTO {

    private CartAnalyticsDTO cartAnalytics;

    private ItemStatisticsDTO itemStatistics;

    private TopItemsDTO topItems;

    public CartAnalyticsDTO getCartAnalytics() {
        return cartAnalytics;
    }

    public void setCartAnalytics(CartAnalyticsDTO cartAnalytics) {
        this.cartAnalytics = cartAnalytics;
    }

    public ItemStatisticsDTO getItemStatistics() {
        return itemStatistics;
    }

    public void setItemStatistics(ItemStatisticsDTO itemStatistics) {
        this.itemStatistics = itemStatistics;
    }

    public TopItemsDTO getTopItems() {
        return topItems;
    }

    public void setTopItems(TopItemsDTO topItems) {
        this.topItems = topItems;
    }

}
