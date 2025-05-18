package com.altice.domain.bo;

import java.math.BigDecimal;

import com.altice.domain.enums.EnumCategoryProduct;
import com.altice.domain.enums.EnumSubCategoryProduct;
import com.altice.domain.vo.UuidVO;

public class ProductBO {

    private final UuidVO id;
    private final String code;
    private final String description;
    private final String acquirerId;
    private final Integer stockQuantity;
    private final BigDecimal priceCost;
    private final BigDecimal price;
    private EnumCategoryProduct category;
    private EnumSubCategoryProduct subCategory;

    private ProductBO(Builder builder) {
        this.id = builder.id;
        this.code = builder.code;
        this.description = builder.description;
        this.acquirerId = builder.acquirerId;
        this.stockQuantity = builder.stockQuantity;
        this.priceCost = builder.priceCost;
        this.price = builder.price;
        this.category = builder.category;
        this.subCategory = builder.subCategory;
    }

    public UuidVO getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public String getAcquirerId() {
        return acquirerId;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public BigDecimal getPriceCost() {
        return priceCost;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public EnumCategoryProduct getCategory() {
        return category;
    }

    public EnumSubCategoryProduct getSubCategory() {
        return subCategory;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private UuidVO id;
        private String code;
        private String description;
        private String acquirerId;
        private Integer stockQuantity;
        private BigDecimal priceCost;
        private BigDecimal price;
        private EnumCategoryProduct category;
        private EnumSubCategoryProduct subCategory;

        public Builder id(UuidVO id) {
            this.id = id;
            return this;
        }

        public Builder code(String code) {
            this.code = code;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder acquirerId(String acquirerId) {
            this.acquirerId = acquirerId;
            return this;
        }

        public Builder stockQuantity(Integer stockQuantity) {
            this.stockQuantity = stockQuantity;
            return this;
        }

        public Builder priceCost(BigDecimal priceCost) {
            this.priceCost = priceCost;
            return this;
        }

        public Builder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public Builder category(EnumCategoryProduct category) {
            this.category = category;
            return this;
        }

        public Builder subCategory(EnumSubCategoryProduct subCategory) {
            this.subCategory = subCategory;
            return this;
        }

        public ProductBO build() {
            return new ProductBO(this);
        }
    }

}