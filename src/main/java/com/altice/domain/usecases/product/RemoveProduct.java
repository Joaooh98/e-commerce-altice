package com.altice.domain.usecases.product;

import java.util.UUID;

import com.altice.domain.enums.EnumErrorCode;
import com.altice.domain.repositories.IProductRepository;
import com.altice.domain.utils.StringUtils;
import com.altice.domain.utils.exception.AlticeException;

public class RemoveProduct {

    private final IProductRepository productRepository;

    public RemoveProduct(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void execute(String id) {
        
        if (StringUtils.isNullOrEmpty(id)) {
             throw new AlticeException(EnumErrorCode.REQUIRED_FIELD_FOR, "id", "remove product");
        }
        productRepository.remove(UUID.fromString(id));
    }
}
