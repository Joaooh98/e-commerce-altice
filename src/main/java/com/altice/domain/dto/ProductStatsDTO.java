package com.altice.domain.dto;

import com.altice.domain.bo.ProductBO;

public class ProductStatsDTO {
    private final ProductBO product;
    private long totalQuantity;
    private int cartCount;

    public ProductStatsDTO(ProductBO product) {
        this.product = product;
        this.totalQuantity = 0;
        this.cartCount = 0;
    }

    public void addItem(int quantity) {
        this.totalQuantity += quantity;
        this.cartCount++;
    }

    public ProductBO getProduct() {
        return product;
    }

    public long getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(long totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public int getCartCount() {
        return cartCount;
    }

    public void setCartCount(int cartCount) {
        this.cartCount = cartCount;
    }

    public double getAverageQuantityPerCart() {
        if (cartCount == 0) {
            return 0.0;
        }
        return Math.round((double) totalQuantity / cartCount * 100.0) / 100.0;
    }
}