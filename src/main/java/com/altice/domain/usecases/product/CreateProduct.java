package com.altice.domain.usecases.product;

import com.altice.domain.dto.ProductDTO;
import com.altice.domain.mappers.ProductDomainMapper;
import com.altice.domain.repositories.IProductRepository;

public class CreateProduct {

    private final IProductRepository productRepository;

    public CreateProduct(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductDTO execute(ProductDTO productDTO) {
        var response = productRepository.save(ProductDomainMapper.toBO(productDTO));
        return ProductDomainMapper.toDTO(response);
    }
}
