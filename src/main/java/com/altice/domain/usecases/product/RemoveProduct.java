package com.altice.domain.usecases.product;

import java.util.UUID;

import com.altice.domain.repositories.IProductRepository;

public class RemoveProduct {

    private final IProductRepository productRepository;

    public RemoveProduct(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void execute(String id) {
        productRepository.remove(UUID.fromString(id));
    }
}
