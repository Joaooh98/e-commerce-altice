package com.altice.domain.usecases.product;

import java.util.UUID;

import com.altice.domain.dto.ProductDTO;
import com.altice.domain.enums.EnumErrorCode;
import com.altice.domain.mappers.ProductDomainMapper;
import com.altice.domain.repositories.IProductRepository;
import com.altice.domain.utils.StringUtils;
import com.altice.domain.utils.exception.AlticeException;

public class UpdatedProduct {

    private final IProductRepository productRepository;

    public UpdatedProduct(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductDTO execute(ProductDTO productUpdated, String id) {
        if (productUpdated == null) {
            throw new AlticeException(EnumErrorCode.REQUIRED_OBJECT_FOR, "product", "updated product");
        }

        if (StringUtils.isNullOrEmpty(id)) {
            throw new AlticeException(EnumErrorCode.REQUIRED_FIELD_FOR, "id", "updated product");
        }

        var internalProduct = productRepository.findById(UUID.fromString(id));

        if (internalProduct == null) {
            throw new AlticeException(EnumErrorCode.OBJECT_NOT_FOUND, "Product");
        }

        productUpdated.setId(internalProduct.getId().getValue().toString());

        var response = productRepository.updated(ProductDomainMapper.toBO(productUpdated));
        return ProductDomainMapper.toDTO(response);
    }
}
