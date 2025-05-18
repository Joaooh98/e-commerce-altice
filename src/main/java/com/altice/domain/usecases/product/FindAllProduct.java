package com.altice.domain.usecases.product;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.altice.domain.bo.ProductBO;
import com.altice.domain.dto.ProductDTO;
import com.altice.domain.enums.EnumCategoryProduct;
import com.altice.domain.enums.EnumErrorCode;
import com.altice.domain.enums.EnumSubCategoryProduct;
import com.altice.domain.mappers.ProductDomainMapper;
import com.altice.domain.repositories.IProductRepository;
import com.altice.domain.utils.exception.AlticeException;

import io.netty.util.internal.StringUtil;

public class FindAllProduct {

    private final IProductRepository productRepository;

    public FindAllProduct(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDTO> execute(String category, String subCategory) {
        if (hasNoFilters(category, subCategory)) {
            return findAllProducts();
        }

        EnumCategoryProduct enumCategory = validateAndParseCategory(category);
        EnumSubCategoryProduct enumSubCategory = validateAndParseSubCategory(subCategory);

        return findProductsByParams(enumCategory, enumSubCategory);
    }

    private boolean hasNoFilters(String category, String subCategory) {
        return StringUtil.isNullOrEmpty(category) && StringUtil.isNullOrEmpty(subCategory);
    }

    private List<ProductDTO> findAllProducts() {
        List<ProductBO> response = productRepository.findAll();
        return convertToDTO(response);
    }

    private EnumCategoryProduct validateAndParseCategory(String category) {
        if (StringUtil.isNullOrEmpty(category)) {
            return null;
        }

        EnumCategoryProduct enumCategory = EnumCategoryProduct.parseByValue(category);
        if (enumCategory == null) {
            throw new AlticeException(EnumErrorCode.INVALID_FORMAT_FIELD,
                    String.format("Categoria inválida: '%s'. Valores válidos: %s",
                            category, getValidCategories()));
        }
        return enumCategory;
    }

    private EnumSubCategoryProduct validateAndParseSubCategory(String subCategory) {
        if (StringUtil.isNullOrEmpty(subCategory)) {
            return null;
        }

        EnumSubCategoryProduct enumSubCategory = EnumSubCategoryProduct.parseByValue(subCategory);
        if (enumSubCategory == null) {
            throw new AlticeException(EnumErrorCode.INVALID_FORMAT_FIELD,
                    String.format("Subcategoria inválida: '%s'. Valores válidos: %s",
                            subCategory, getValidSubCategories()));
        }
        return enumSubCategory;
    }

    private List<ProductDTO> findProductsByParams(EnumCategoryProduct category, EnumSubCategoryProduct subCategory) {
        List<ProductBO> response = productRepository.findAllByParams(category, subCategory);
        return convertToDTO(response);
    }

    private List<ProductDTO> convertToDTO(List<ProductBO> products) {
        return products.stream()
                .map(ProductDomainMapper::toDTO)
                .toList();
    }

    private String getValidCategories() {
        return Arrays.stream(EnumCategoryProduct.values())
                .map(EnumCategoryProduct::getValue)
                .collect(Collectors.joining(", "));
    }

    private String getValidSubCategories() {
        return Arrays.stream(EnumSubCategoryProduct.values())
                .map(subcategory -> "'" + subcategory.getValue() + "'")
                .collect(Collectors.joining(", "));
    }
}