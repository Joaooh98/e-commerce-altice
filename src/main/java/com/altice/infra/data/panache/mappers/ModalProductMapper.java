package com.altice.infra.data.panache.mappers;

import com.altice.domain.bo.ProductBO;
import com.altice.domain.enums.EnumCategoryProduct;
import com.altice.domain.enums.EnumErrorCode;
import com.altice.domain.enums.EnumSubCategoryProduct;
import com.altice.domain.utils.exception.AlticeException;
import com.altice.domain.vo.UuidVO;
import com.altice.infra.data.panache.modal.ModalProduct;

public abstract class ModalProductMapper {

    public static ModalProduct toModal(ProductBO bo) {
        if (bo == null) {
            throw new AlticeException(EnumErrorCode.REQUIRED_OBJECT, "productBO");
        }

        ModalProduct modal = new ModalProduct();

        if (bo.getId() != null) {
            modal.setId(bo.getId().getValue());
        }
        modal.setCode(bo.getCode());
        modal.setDescription(bo.getDescription());
        modal.setAcquirerId(bo.getAcquirerId());
        modal.setStockQuantity(bo.getStockQuantity());
        modal.setPriceCost(bo.getPriceCost());
        modal.setPrice(bo.getPrice());
        modal.setCategory(bo.getCategory().getKey());
        modal.setSubCategory(bo.getSubCategory().getKey());

        return modal;
    }

    public static ProductBO toBO(ModalProduct modal) {
        if (modal == null) {
            throw new AlticeException(EnumErrorCode.REQUIRED_OBJECT, "modalProduct");
        }

        return new ProductBO.Builder()
                .id(modal.getId() != null ? new UuidVO(modal.getId().toString()) : null)
                .code(modal.getCode())
                .description(modal.getDescription())
                .acquirerId(modal.getAcquirerId())
                .stockQuantity(modal.getStockQuantity())
                .priceCost(modal.getPriceCost())
                .price(modal.getPrice())
                .category(EnumCategoryProduct.parseByKey(modal.getCategory()))
                .subCategory(EnumSubCategoryProduct.parseByKey(modal.getSubCategory()))
                .build();
    }
}
