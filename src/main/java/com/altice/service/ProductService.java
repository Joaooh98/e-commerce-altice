package com.altice.service;

import java.util.List;

import com.altice.domain.dto.ProductDTO;
import com.altice.domain.usecases.product.CreateProduct;
import com.altice.domain.usecases.product.FindAllProduct;
import com.altice.domain.usecases.product.FindProduct;
import com.altice.domain.usecases.product.RemoveProduct;
import com.altice.domain.usecases.product.UpdatedProduct;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ProductService extends AbstractService {

    @Transactional
    public ProductDTO create(ProductDTO product) {
        return new CreateProduct(productRepository).execute(product);
    }

    public List<ProductDTO> findAll() {
        return new FindAllProduct(productRepository).execute();
    }

    public ProductDTO findById(String id) {
        validateUUID(id);
        return new FindProduct(productRepository).execute(id);
    }

    @Transactional
    public void deleteById(String id) {
        validateUUID(id);
        new RemoveProduct(productRepository).execute(id);
    }

    @Transactional
    public ProductDTO updatedById(ProductDTO productUpdated, String id) {
        validateUUID(id);
        return new UpdatedProduct(productRepository).execute(productUpdated, id);

    }

}
