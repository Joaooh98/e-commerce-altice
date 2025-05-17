package com.altice.domain.mappers;

import com.altice.domain.bo.ProductBO;
import com.altice.domain.dto.ProductDTO;
import com.altice.domain.enums.EnumErrorCode;
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
                .reservedQuantity(productDTO.getReservedQuantity())
                .priceCost(productDTO.getPriceCost())
                .price(productDTO.getPrice())
                .build();
    }

    public static ProductDTO toDTO(ProductBO productBO) {
        if (productBO == null) {
            throw new AlticeException(EnumErrorCode.REQUIRED_OBJECT, "productBO");
        }

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(productBO.getId() != null ? productBO.getId().getValue().toString() : null);
        productDTO.setCode(productBO.getCode());
        productDTO.setDescription(productBO.getDescription());
        productDTO.setAcquirerId(productBO.getAcquirerId());
        productDTO.setStockQuantity(productBO.getStockQuantity());
        productDTO.setReservedQuantity(productBO.getReservedQuantity());
        productDTO.setPriceCost(productBO.getPriceCost());
        productDTO.setPrice(productBO.getPrice());

        return productDTO;
    }
}