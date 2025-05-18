package com.altice.service;

import java.util.List;

import com.altice.domain.dto.ItemDTO;
import com.altice.domain.dto.ProductDTO;
import com.altice.domain.enums.EnumErrorCode;
import com.altice.domain.usecases.product.CreateProduct;
import com.altice.domain.usecases.product.FindAllProduct;
import com.altice.domain.usecases.product.FindProduct;
import com.altice.domain.usecases.product.RemoveProduct;
import com.altice.domain.usecases.product.UpdatedProduct;
import com.altice.domain.utils.exception.AlticeException;

import io.quarkus.cache.CacheResult;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ProductService extends AbstractService {

    @Transactional
    public List<ProductDTO> createProducts(List<ProductDTO> products) {
        CreateProduct createProduct = new CreateProduct(productRepository);
        products.forEach(product -> {
            var productNew = createProduct.execute(product);
            product.setId(productNew.getId());
        });
        return products;
    }

    @CacheResult(cacheName = "products")
    public List<ProductDTO> findAll(String category, String subCategory) {
        return new FindAllProduct(productRepository).execute(category, subCategory);
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

    public void movimentStock(List<ItemDTO> items) {
        try {
            items.forEach(item -> {
                int quantity = item.getQuantity();
                ProductDTO product = item.getProduct();
                Integer stockQuantity = product.getStockQuantity();
                product.setStockQuantity(stockQuantity - quantity);
    
                updatedById(product, product.getId());
            });
            
        } catch (Exception e) {
            throw new AlticeException(EnumErrorCode.INTERNAL_ERROR,"error updating product stock");
        }
    }

}
