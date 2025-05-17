package com.altice.domain.repositories;

import java.util.List;
import java.util.UUID;

import com.altice.domain.bo.ProductBO;

public interface IProductRepository {

    ProductBO save(ProductBO bo);

    List<ProductBO> findAll();

    ProductBO findById(UUID fromString);

    void remove(UUID fromString);

    ProductBO updated(ProductBO bo);

}
