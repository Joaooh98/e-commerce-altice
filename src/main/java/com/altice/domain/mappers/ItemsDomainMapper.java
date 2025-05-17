package com.altice.domain.mappers;

import com.altice.domain.bo.ItemBO;
import com.altice.domain.dto.ItemDTO;
import com.altice.domain.vo.UuidVO;

public class ItemsDomainMapper {

    public static ItemBO toBO(ItemDTO itemDTO) {
        return ItemBO.builder()
                .id(new UuidVO(itemDTO.getId()))
                .shoppingCart(ShoppingCartDomainMapper.toBO(itemDTO.getShoppingCart()))
                .product(ProductDomainMapper.toBO(itemDTO.getProduct()))
                .quantity(itemDTO.getQuantity())
                .build();
    }

    public static ItemDTO toDTO(ItemBO itemBO) {
        ItemDTO itemDTO = new ItemDTO();

        itemDTO.setId(itemBO.getId() != null ? itemBO.getId().getValue().toString() : null);
        itemDTO.setShoppingCart(ShoppingCartDomainMapper.toDTO(itemBO.getShoppingCart()));
        itemDTO.setProduct(ProductDomainMapper.toDTO(itemBO.getProduct()));
        itemDTO.setQuantity(itemBO.getQuantity() != null ? itemBO.getQuantity() : 0);

        return itemDTO;
    }
}
