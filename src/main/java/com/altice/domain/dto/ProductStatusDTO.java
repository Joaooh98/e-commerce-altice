package com.altice.domain.dto;

import com.altice.domain.bo.ProductBO;

public class ProductStatusDTO {
    final ProductBO product;
    long totalQuantity;
    int cartCount;

    public ProductStatusDTO(ProductBO product) {
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

    
}
