package com.altice.domain.usecases.analytics;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import com.altice.domain.bo.ItemBO;
import com.altice.domain.bo.ProductBO;
import com.altice.domain.bo.ShoppingCartBO;
import com.altice.domain.dto.ItemDTO;
import com.altice.domain.dto.ProductStatsDTO;
import com.altice.domain.dto.TopItemsDTO;
import com.altice.domain.mappers.ProductDomainMapper;
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

        Map<UUID, ProductStatsDTO> productStatsMap = new HashMap<>();
        int totalItemsAnalyzed = 0;

        for (ShoppingCartBO cart : cartsWithItems) {
            for (ItemBO item : cart.getItems()) {
                UUID productId = item.getProduct().getId().getValue();

                ProductStatsDTO stats = productStatsMap.getOrDefault(productId,
                        new ProductStatsDTO(item.getProduct()));

                stats.addItem(item.getQuantity());
                productStatsMap.put(productId, stats);
                totalItemsAnalyzed += item.getQuantity();
            }
        }

        List<ItemDTO> topItems = productStatsMap.values().stream()
                .sorted((a, b) -> Long.compare(b.getTotalQuantity(), a.getTotalQuantity()))
                .limit(topCount)
                .map(this::convertToItemDTO)
                .collect(Collectors.toList());

        TopItemsDTO result = new TopItemsDTO();
        result.setTopItems(topItems);
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

    private ItemDTO convertToItemDTO(ProductStatsDTO status) {
        ItemDTO itemDTO = new ItemDTO();
        ProductBO product = status.getProduct();

        itemDTO.setProduct(ProductDomainMapper.toDTO(product));
        itemDTO.setQuantity((int) status.getTotalQuantity());
        itemDTO.setId(UUID.randomUUID().toString());

        return itemDTO;
    }
}