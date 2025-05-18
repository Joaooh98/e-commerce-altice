package com.altice.domain.dto;

public class ItemStatisticsDTO {
    
    private Integer maxItemsPerCart;
    private Double averageItemsPerCart;
    private Integer minItemsPerCart;
    private Long totalItemsInAllCarts;
    private Long cartsAnalyzed;

    public Integer getMaxItemsPerCart() {
        return maxItemsPerCart;
    }

    public void setMaxItemsPerCart(Integer maxItemsPerCart) {
        this.maxItemsPerCart = maxItemsPerCart;
    }

    public Double getAverageItemsPerCart() {
        return averageItemsPerCart;
    }

    public void setAverageItemsPerCart(Double averageItemsPerCart) {
        this.averageItemsPerCart = averageItemsPerCart;
    }

    public Integer getMinItemsPerCart() {
        return minItemsPerCart;
    }

    public void setMinItemsPerCart(Integer minItemsPerCart) {
        this.minItemsPerCart = minItemsPerCart;
    }

    public Long getTotalItemsInAllCarts() {
        return totalItemsInAllCarts;
    }

    public void setTotalItemsInAllCarts(Long totalItemsInAllCarts) {
        this.totalItemsInAllCarts = totalItemsInAllCarts;
    }

    public Long getCartsAnalyzed() {
        return cartsAnalyzed;
    }

    public void setCartsAnalyzed(Long cartsAnalyzed) {
        this.cartsAnalyzed = cartsAnalyzed;
    }
}