package com.altice.domain.bo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.altice.domain.enums.EnumShoppingStatus;
import com.altice.domain.vo.UuidVO;

public class ShoppingCartBO {

    private final UuidVO id;
    private final UserBO user;
    private final List<ItemBO> items;
    private final EnumShoppingStatus status;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final BigDecimal amount;
    
    private ShoppingCartBO(Builder builder) {
        this.id = builder.id;
        this.user = builder.user;
        this.items = builder.items;
        this.status = builder.status;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
        this.amount = builder.amount;
    }
    
    public UuidVO getId() {
        return id;
    }
    
    public UserBO getUser() {
        return user;
    }
    
    public List<ItemBO> getItems() {
        return items != null ? new ArrayList<>(items) : new ArrayList<>();
    }
    
    public EnumShoppingStatus getStatus() {
        return status;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public BigDecimal getAmount() {
        return amount;
    }
    
    public static Builder builder() {
        return new Builder();
    }
        
    public static final class Builder {
        private UuidVO id;
        private UserBO user;
        private List<ItemBO> items;
        private EnumShoppingStatus status;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private BigDecimal amount;
        
        private Builder() {
        }
        
        public Builder id(UuidVO id) {
            this.id = id;
            return this;
        }
        
        public Builder user(UserBO user) {
            this.user = user;
            return this;
        }
        
        public Builder items(List<ItemBO> items) {
            this.items = items != null ? new ArrayList<>(items) : null;
            return this;
        }
        
        public Builder status(EnumShoppingStatus status) {
            this.status = status;
            return this;
        }
        
        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }
        
        public Builder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }
        
        public Builder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }
        
        public ShoppingCartBO build() {
            return new ShoppingCartBO(this);
        }
    }
}