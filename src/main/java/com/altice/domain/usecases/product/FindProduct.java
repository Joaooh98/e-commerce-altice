package com.altice.domain.usecases.product;

import java.util.UUID;

import com.altice.domain.dto.ProductDTO;
import com.altice.domain.enums.EnumErrorCode;
import com.altice.domain.mappers.ProductDomainMapper;
import com.altice.domain.repositories.IProductRepository;
import com.altice.domain.utils.StringUtils;
import com.altice.domain.utils.exception.AlticeException;

public class FindProduct {

    private final IProductRepository productRepository;

    public FindProduct(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductDTO execute(String id) {
        if (StringUtils.isNullOrEmpty(id)) {
            throw new AlticeException(EnumErrorCode.REQUIRED_FIELD_FOR, "id", "find product");
        }

        var response = productRepository.findById(UUID.fromString(id));

        if (response == null) {
            throw new AlticeException(EnumErrorCode.OBJECT_NOT_FOUND, "Product");
        }

        return ProductDomainMapper.toDTO(response);
    }
}
