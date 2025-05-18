package com.altice.domain.usecases.analytics;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.altice.domain.bo.ItemBO;
import com.altice.domain.bo.ProductBO;
import com.altice.domain.bo.ShoppingCartBO;
import com.altice.domain.dto.ProductStatusDTO;
import com.altice.domain.dto.TopItemsDTO;
import com.altice.domain.repositories.IShoppingCartRepository;

public class Analytics {

    private final IShoppingCartRepository cartRepository;

    public Analytics(IShoppingCartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public TopItemsDTO execute(int topCount) {
        List<ShoppingCartBO> allCarts = cartRepository.findAll();

        List<ShoppingCartBO> cartsWithItems = allCarts.stream()
                .filter(cart -> cart.getItems() != null && !cart.getItems().isEmpty())
                .toList();

        if (cartsWithItems.isEmpty()) {
            return createEmptyTopItems();
        }

        Map<UUID, ProductStatusDTO> productStatsMap = new HashMap<>();
        int totalItemsAnalyzed = 0;

        for (ShoppingCartBO cart : cartsWithItems) {
            for (ItemBO item : cart.getItems()) {
                UUID productId = item.getProduct().getId().getValue();

                ProductStatusDTO stats = productStatsMap.getOrDefault(productId,
                        new ProductStatusDTO(item.getProduct()));

                stats.addItem(item.getQuantity());
                productStatsMap.put(productId, stats);
                totalItemsAnalyzed += item.getQuantity();
            }
        }

        // List<TopItemsDTO> topItems = productStatsMap.values().stream()
        //         .sorted((a, b) -> Long.compare(b.totalQuantity, a.totalQuantity))
        //         .limit(topCount)
        //         .map(this::convertToTopItemInfo)
        //         .collect(Collectors.toList());

        TopItemsDTO result = new TopItemsDTO();
        // result.setTopItems(topItems);
        result.setTotalItemsAnalyzed(totalItemsAnalyzed);
        result.setAnalysisDate(LocalDateTime.now().toString());

        return result;
    }

    private TopItemsDTO createEmptyTopItems() {
        TopItemsDTO result = new TopItemsDTO();
        result.setTopItems(new ArrayList<>());
        result.setTotalItemsAnalyzed(0);
        result.setAnalysisDate(LocalDateTime.now().toString());
        return result;
    }

    private TopItemsDTO convertToTopItemInfo(ProductStatusDTO status) {
        TopItemsDTO itemInfo = new TopItemsDTO();
        ProductBO product = status.getProduct();
        // itemInfo.setProductId(product.getId().getValue().toString());
        // itemInfo.setProductName(product.getDescription());
        // itemInfo.setProductCode(product.getCode());
        // itemInfo.setCategory(product.getCategory().getValue());
        // itemInfo.setSubCategory(product.getSubCategory().getValue());
        // itemInfo.setTotalQuantity(status.getTotalQuantity());
        // itemInfo.setCartCount(status.getCartCount());
        // itemInfo.setAverageQuantityPerCart(
        //         Math.round((double) status.getTotalQuantity() / status.getCartCount() * 100.0) / 100.0);
        return itemInfo;
    }
}
