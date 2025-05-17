package com.altice.domain.dto;

import java.math.BigDecimal;

public class ProductDTO {

    private String id;

    private String code;

    private String description;

    private String acquirerId;

    private Long stockQuantity;

    private Long reservedQuantity;

    private BigDecimal priceCost;

    private BigDecimal price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAcquirerId() {
        return acquirerId;
    }

    public void setAcquirerId(String acquirerId) {
        this.acquirerId = acquirerId;
    }

    public Long getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Long stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public Long getReservedQuantity() {
        return reservedQuantity;
    }

    public void setReservedQuantity(Long reservedQuantity) {
        this.reservedQuantity = reservedQuantity;
    }

    public BigDecimal getPriceCost() {
        return priceCost;
    }

    public void setPriceCost(BigDecimal priceCost) {
        this.priceCost = priceCost;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    
}
