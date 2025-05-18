package com.altice.domain.usecases.analytics;

import java.util.List;
import java.util.OptionalDouble;
import java.util.OptionalInt;

import com.altice.domain.bo.ShoppingCartBO;
import com.altice.domain.dto.ItemStatisticsDTO;
import com.altice.domain.repositories.IShoppingCartRepository;

public class ItemStatistics {

    private final IShoppingCartRepository cartRepository;

    public ItemStatistics(IShoppingCartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public ItemStatisticsDTO execute() {
        List<ShoppingCartBO> allCarts = cartRepository.findAll();
        
        List<ShoppingCartBO> cartsWithItems = allCarts.stream()
                .filter(cart -> cart.getItems() != null && !cart.getItems().isEmpty())
                .toList();

        if (cartsWithItems.isEmpty()) {
            return createEmptyStatistics();
        }

        List<Integer> itemCounts = cartsWithItems.stream()
                .map(cart -> cart.getItems().size())
                .toList();

        OptionalInt maxItems = itemCounts.stream().mapToInt(Integer::intValue).max();
        OptionalInt minItems = itemCounts.stream().mapToInt(Integer::intValue).min();
        OptionalDouble avgItems = itemCounts.stream().mapToInt(Integer::intValue).average();

        long totalItems = cartsWithItems.stream()
                .flatMap(cart -> cart.getItems().stream())
                .mapToInt(item -> item.getQuantity())
                .sum();

        ItemStatisticsDTO statistics = new ItemStatisticsDTO();
        statistics.setMaxItemsPerCart(maxItems.orElse(0));
        statistics.setMinItemsPerCart(minItems.orElse(0));
        statistics.setAverageItemsPerCart(Math.round(avgItems.orElse(0.0) * 100.0) / 100.0);
        statistics.setTotalItemsInAllCarts(totalItems);
        statistics.setCartsAnalyzed((long) cartsWithItems.size());

        return statistics;
    }

    private ItemStatisticsDTO createEmptyStatistics() {
        ItemStatisticsDTO statistics = new ItemStatisticsDTO();
        statistics.setMaxItemsPerCart(0);
        statistics.setMinItemsPerCart(0);
        statistics.setAverageItemsPerCart(0.0);
        statistics.setTotalItemsInAllCarts(0L);
        statistics.setCartsAnalyzed(0L);
        return statistics;
    }
}
