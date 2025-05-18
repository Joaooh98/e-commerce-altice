package com.altice.domain.usecases.analytics;

import java.util.List;

import com.altice.domain.bo.ShoppingCartBO;
import com.altice.domain.dto.CartAnalyticsDTO;
import com.altice.domain.repositories.IShoppingCartRepository;

public class CartAnalytics {

    private final IShoppingCartRepository cartRepository;

    public CartAnalytics(IShoppingCartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public CartAnalyticsDTO execute() {
        List<ShoppingCartBO> allCarts = cartRepository.findAll();

        long totalCarts = allCarts.size();
        long cartsWithItems = allCarts.stream()
                .filter(cart -> cart.getItems() != null && !cart.getItems().isEmpty())
                .count();

        long emptyCarts = totalCarts - cartsWithItems;

        double cartsWithItemsPercentage = totalCarts > 0
                ? (double) cartsWithItems / totalCarts * 100
                : 0.0;

        CartAnalyticsDTO analytics = new CartAnalyticsDTO();
        analytics.setTotalCarts(totalCarts);
        analytics.setCartsWithItems(cartsWithItems);
        analytics.setEmptyCarts(emptyCarts);
        analytics.setCartsWithItemsPercentage(Math.round(cartsWithItemsPercentage * 100.0) / 100.0);

        return analytics;
    }
}
