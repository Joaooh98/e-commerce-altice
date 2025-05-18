package com.altice.domain.mappers;

import com.altice.domain.bo.ProductBO;
import com.altice.domain.dto.ProductDTO;
import com.altice.domain.enums.EnumCategoryProduct;
import com.altice.domain.enums.EnumErrorCode;
import com.altice.domain.enums.EnumSubCategoryProduct;
import com.altice.domain.utils.exception.AlticeException;
import com.altice.domain.vo.UuidVO;

public abstract class ProductDomainMapper {

    public static ProductBO toBO(ProductDTO productDTO) {
        if (productDTO == null) {
            throw new AlticeException(EnumErrorCode.REQUIRED_OBJECT, "productDTO");
        }

        return ProductBO.builder()
                .id(new UuidVO(productDTO.getId()))
                .code(productDTO.getCode())
                .description(productDTO.getDescription())
                .acquirerId(productDTO.getAcquirerId())
                .stockQuantity(productDTO.getStockQuantity())
                .priceCost(productDTO.getPriceCost())
                .price(productDTO.getPrice())
                .category(EnumCategoryProduct.parseByValue(productDTO.getCategory()))
                .subCategory(EnumSubCategoryProduct.parseByValue(productDTO.getSubCategory()))
                .build();
    }

    public static ProductDTO toDTO(ProductBO productBO) {
        if (productBO == null) {
            throw new AlticeException(EnumErrorCode.REQUIRED_OBJECT, "productBO");
        }

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(productBO.getId().getValue().toString());
        productDTO.setCode(productBO.getCode());
        productDTO.setDescription(productBO.getDescription());
        productDTO.setAcquirerId(productBO.getAcquirerId());
        productDTO.setStockQuantity(productBO.getStockQuantity());
        productDTO.setPriceCost(productBO.getPriceCost());
        productDTO.setPrice(productBO.getPrice());
        productDTO.setCategory(productBO.getCategory().getValue());
        productDTO.setSubCategory(productBO.getSubCategory().getValue());

        return productDTO;
    }
}