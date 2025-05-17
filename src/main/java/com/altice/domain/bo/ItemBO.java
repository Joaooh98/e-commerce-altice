package com.altice.domain.bo;

import com.altice.domain.vo.UuidVO;

public class ItemBO {

    private final UuidVO id;
    private final ShoppingCartBO shoppingCart;
    private final ProductBO product;
    private final Integer quantity;
    
    private ItemBO(Builder builder) {
        this.id = builder.id;
        this.shoppingCart = builder.shoppingCart;
        this.product = builder.product;
        this.quantity = builder.quantity;
    }
    
    public UuidVO getId() {
        return id;
    }
    
    public ShoppingCartBO getShoppingCart() {
        return shoppingCart;
    }
    
    public ProductBO getProduct() {
        return product;
    }
    
    public Integer getQuantity() {
        return quantity;
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    public static final class Builder {
        private UuidVO id;
        private ShoppingCartBO shoppingCart;
        private ProductBO product;
        private Integer quantity = 1; 
        
        public Builder id(UuidVO id) {
            this.id = id;
            return this;
        }
        
        public Builder shoppingCart(ShoppingCartBO shoppingCart) {
            this.shoppingCart = shoppingCart;
            return this;
        }
        
        public Builder product(ProductBO product) {
            this.product = product;
            return this;
        }
        
        public Builder quantity(Integer quantity) {
            this.quantity = quantity;
            return this;
        }
        
        public ItemBO build() {
            return new ItemBO(this);
        }
    }
}