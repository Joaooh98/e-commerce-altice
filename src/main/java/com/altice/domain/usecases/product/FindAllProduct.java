package com.altice.domain.usecases.product;

import java.util.List;

import com.altice.domain.bo.ProductBO;
import com.altice.domain.dto.ProductDTO;
import com.altice.domain.mappers.ProductDomainMapper;
import com.altice.domain.repositories.IProductRepository;

public class FindAllProduct {

    private final IProductRepository productRepository;

    public FindAllProduct(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDTO> execute() {
        List<ProductBO> response = productRepository.findAll();

        return response.stream().map(product -> ProductDomainMapper.toDTO(product)).toList();
    }
}
