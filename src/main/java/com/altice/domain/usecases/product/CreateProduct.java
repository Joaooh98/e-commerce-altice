package com.altice.domain.usecases.product;

import com.altice.domain.dto.ProductDTO;
import com.altice.domain.enums.EnumErrorCode;
import com.altice.domain.mappers.ProductDomainMapper;
import com.altice.domain.repositories.IProductRepository;
import com.altice.domain.utils.exception.AlticeException;

public class CreateProduct {

    private final IProductRepository productRepository;

    public CreateProduct(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductDTO execute(ProductDTO productDTO) {
        if (productDTO == null) {
            throw new AlticeException(EnumErrorCode.REQUIRED_OBJECT, "productDTO", "create product");
        }

        var response = productRepository.save(ProductDomainMapper.toBO(productDTO));
        return ProductDomainMapper.toDTO(response);
    }
}
