package com.altice.domain.repositories;

import java.util.List;
import java.util.UUID;

import com.altice.domain.bo.ProductBO;
import com.altice.domain.enums.EnumCategoryProduct;
import com.altice.domain.enums.EnumSubCategoryProduct;

public interface IProductRepository {

    ProductBO save(ProductBO bo);

    List<ProductBO> findAll();

    List<ProductBO> findAllByParams(EnumCategoryProduct category, EnumSubCategoryProduct subCategory);

    ProductBO findById(UUID fromString);

    void remove(UUID fromString);

    ProductBO updated(ProductBO bo);

}
