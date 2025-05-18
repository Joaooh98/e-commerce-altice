package com.altice.domain.dto;

public class CartAnalyticsDTO {

    private Long totalCarts;
    private Long cartsWithItems;
    private Long emptyCarts;
    private Double cartsWithItemsPercentage;

    public Long getTotalCarts() {
        return totalCarts;
    }

    public void setTotalCarts(Long totalCarts) {
        this.totalCarts = totalCarts;
    }

    public Long getCartsWithItems() {
        return cartsWithItems;
    }

    public void setCartsWithItems(Long cartsWithItems) {
        this.cartsWithItems = cartsWithItems;
    }

    public Long getEmptyCarts() {
        return emptyCarts;
    }

    public void setEmptyCarts(Long emptyCarts) {
        this.emptyCarts = emptyCarts;
    }

    public Double getCartsWithItemsPercentage() {
        return cartsWithItemsPercentage;
    }

    public void setCartsWithItemsPercentage(Double cartsWithItemsPercentage) {
        this.cartsWithItemsPercentage = cartsWithItemsPercentage;
    }
}