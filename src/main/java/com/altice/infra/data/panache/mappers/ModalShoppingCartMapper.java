package com.altice.infra.data.panache.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.altice.domain.bo.ItemBO;
import com.altice.domain.bo.ShoppingCartBO;
import com.altice.domain.enums.EnumErrorCode;
import com.altice.domain.enums.EnumShoppingStatus;
import com.altice.domain.utils.exception.AlticeException;
import com.altice.domain.vo.UuidVO;
import com.altice.infra.data.panache.modal.ModalProductCart;
import com.altice.infra.data.panache.modal.ModalShoppingCart;
import com.altice.infra.data.utils.ListUtilModal;

public abstract class ModalShoppingCartMapper {

    public static ModalShoppingCart toModal(ShoppingCartBO bo) {
        if (bo == null) {
            throw new AlticeException(EnumErrorCode.REQUIRED_OBJECT, "shoppingCartBO");
        }

        ModalShoppingCart modal = new ModalShoppingCart();

        if (bo.getId() != null) {
            modal.setId(bo.getId().getValue());
        }

        if (bo.getUser() != null) {
            modal.setUser(ModalUserMapper.toModal(bo.getUser()));
        }

        if (bo.getItems() != null && !bo.getItems().isEmpty()) {
            List<ModalProductCart> collect = bo.getItems().stream()
                    .map(item -> ModalProductCartMapper.toModal(item))
                    .collect(Collectors.toList());
            modal.setProductCart(collect);
            
            ListUtilModal.setEntityInList(modal.getProductCart(), p -> p.setCart(modal));
        }

        modal.setStatus(bo.getStatus().getKey());
        modal.setAmount(bo.getAmount());

        return modal;
    }

    public static ShoppingCartBO toBO(ModalShoppingCart modal) {
        if (modal == null) {
            throw new AlticeException(EnumErrorCode.REQUIRED_OBJECT, "modalShoppingCart");
        }

        ShoppingCartBO.Builder builder = ShoppingCartBO.builder();

        if (modal.getId() != null) {
            builder.id(new UuidVO(modal.getId().toString()));
        }

        if (modal.getUser() != null) {
            builder.user(ModalUserMapper.toBO(modal.getUser()));
        }

        if (modal.getProductCart() != null && !modal.getProductCart().isEmpty()) {
            List<ItemBO> items = modal.getProductCart().stream()
                    .map(ModalProductCartMapper::toBO)
                    .collect(Collectors.toList());

            builder.items(items);
        } else {
            builder.items(new ArrayList<>());
        }

        builder.amount(modal.getAmount());
        builder.createdAt(modal.getCreatedAt());
        builder.updatedAt(modal.getUpdatedAt());
        builder.status(EnumShoppingStatus.parseByKey(modal.getStatus()));

        return builder.build();
    }

}