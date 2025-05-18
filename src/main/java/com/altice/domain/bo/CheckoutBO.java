package com.altice.domain.bo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.altice.domain.enums.EnumCheckoutStatus;
import com.altice.domain.vo.UuidVO;

public class CheckoutBO {

    private final UuidVO id;
    private final UuidVO shoppingCartId;
    private final UuidVO userId;
    private final BigDecimal amount;
    private final EnumCheckoutStatus status;
    private final String paymentUrl;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    private CheckoutBO(Builder builder) {
        this.id = builder.id;
        this.shoppingCartId = builder.shoppingCartId;
        this.userId = builder.userId;
        this.amount = builder.amount;
        this.status = builder.status;
        this.paymentUrl = builder.paymentUrl;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
    }

    public UuidVO getId() {
        return id;
    }

    public UuidVO getShoppingCartId() {
        return shoppingCartId;
    }

    public UuidVO getUserId() {
        return userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public EnumCheckoutStatus getStatus() {
        return status;
    }

    public String getPaymentUrl() {
        return paymentUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private UuidVO id;
        private UuidVO shoppingCartId;
        private UuidVO userId;
        private BigDecimal amount;
        private EnumCheckoutStatus status;
        private String paymentUrl;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public Builder id(UuidVO id) {
            this.id = id;
            return this;
        }

        public Builder shoppingCartId(UuidVO shoppingCartId) {
            this.shoppingCartId = shoppingCartId;
            return this;
        }

        public Builder userId(UuidVO userId) {
            this.userId = userId;
            return this;
        }

        public Builder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public Builder status(EnumCheckoutStatus status) {
            this.status = status;
            return this;
        }

        public Builder paymentUrl(String paymentUrl) {
            this.paymentUrl = paymentUrl;
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

        public CheckoutBO build() {
            return new CheckoutBO(this);
        }
    }
}