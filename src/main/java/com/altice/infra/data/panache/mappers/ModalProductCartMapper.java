package com.altice.infra.data.panache.mappers;

import com.altice.domain.bo.ItemBO;
import com.altice.domain.vo.UuidVO;
import com.altice.infra.data.panache.modal.ModalProductCart;

public class ModalProductCartMapper {

    public static ModalProductCart toModal(ItemBO bo) {
        ModalProductCart modal = new ModalProductCart();
        modal.setId(bo.getId().getValue());
        modal.setProduct(ModalProductMapper.toModal(bo.getProduct()));
        modal.setQuantity(bo.getQuantity());
        return modal;
    }

    public static ItemBO toBO(ModalProductCart modal) {
        return new ItemBO.Builder()
                .id(new UuidVO(modal.getId().toString()))
                .product(ModalProductMapper.toBO(modal.getProduct()))
                .quantity(modal.getQuantity())
                .build();
    }

}
