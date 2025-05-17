package com.altice.infra.data.panache.modal;

import java.math.BigDecimal;
import java.util.UUID;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "AT_PRODUCT")
public class ModalProduct extends PanacheEntityBase {
    
    @Id
    private UUID id;

    @Column(name = "CODE", unique = true)
    private String code;

    @Column(name = "DESCRIPTION", nullable = false, length = 200)
    private String description;

    @Column(name = "ACQUIRER_ID", length = 200)
    private String acquirerId;

    @Column(name = "STOCK_QUANTITY", nullable = false)
    private Long stockQuantity;

    @Column(name = "RESERVED_QUANTITY", nullable = false)
    private Long reservedQuantity;

    @Column(name = "PRICE_COST", nullable = false, precision = 15, scale = 2)
    private BigDecimal priceCost;

    @Column(name = "PRICE", nullable = false, precision = 15, scale = 2)
    private BigDecimal price;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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