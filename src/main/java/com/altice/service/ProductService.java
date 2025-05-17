package com.altice.service;

import java.util.List;

import com.altice.domain.dto.ProductDTO;
import com.altice.domain.enums.EnumErrorCode;
import com.altice.domain.usecases.product.CreateProduct;
import com.altice.domain.usecases.product.FindAllProduct;
import com.altice.domain.usecases.product.FindProduct;
import com.altice.domain.usecases.product.RemoveProduct;
import com.altice.domain.usecases.product.UpdatedProduct;
import com.altice.domain.utils.exception.AlticeException;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ProductService extends AbstractService {

    @Transactional
    public ProductDTO create(ProductDTO product) {
        if (product == null) {
            throw new AlticeException(EnumErrorCode.REQUIRED_OBJECT, product);
        }

        return new CreateProduct(productRepository).execute(product);
    }

    public List<ProductDTO> findAll() {
        return new FindAllProduct(productRepository).execute();
    }

    public ProductDTO findById(String id) {
        return new FindProduct(productRepository).execute(id);
    }

    @Transactional
    public void deleteById(String id) {
        new RemoveProduct(productRepository).execute(id);
    }

    @Transactional
    public ProductDTO updatedById(ProductDTO productUpdated, String id) {
        return new UpdatedProduct(productRepository).execute(productUpdated, id);

    }

}
