package com.altice.domain.usecases.shoppingCart;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.altice.domain.bo.ItemBO;
import com.altice.domain.bo.ShoppingCartBO;
import com.altice.domain.dto.ItemDTO;
import com.altice.domain.dto.ShoppingCartDTO;
import com.altice.domain.enums.EnumErrorCode;
import com.altice.domain.mappers.ItemsDomainMapper;
import com.altice.domain.mappers.ShoppingCartDomainMapper;
import com.altice.domain.repositories.IShoppingCartRepository;
import com.altice.domain.utils.StringUtils;
import com.altice.domain.utils.exception.AlticeException;

public class AddQuantityItems {

    private final IShoppingCartRepository cartRepository;

    public AddQuantityItems(IShoppingCartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public ShoppingCartDTO execute(List<ItemDTO> items, String idCart) {

        if (items == null || items.isEmpty()) {
            throw new AlticeException(EnumErrorCode.REQUIRED_OBJECT_FOR, "items", "add quantity shopping Cart");
        }

        if (StringUtils.isNullOrEmpty(idCart)) {
            throw new AlticeException(EnumErrorCode.REQUIRED_FIELD_FOR, "id", "updated shopping Cart");
        }

        var cart = cartRepository.findById(UUID.fromString(idCart));

        if (cart == null) {
            throw new AlticeException(EnumErrorCode.OBJECT_NOT_FOUND, "Shopping Cart");
        }

        List<ItemDTO> itemsInternal = cart.getItems().stream().map(item -> ItemsDomainMapper.toDTO(item))
                .collect(Collectors.toList());

        for (ItemDTO itemInternal : itemsInternal) {
            int quantityAtualy = itemInternal.getQuantity();
            for (ItemDTO item : items) {
                if (item.getProductId().equals(itemInternal.getProduct().getId())) {
                    itemInternal.setQuantity(quantityAtualy + item.getQuantity());
                }
            }
        }
        
        List<ItemBO> itemsInternalUpdated = itemsInternal.stream().map(item -> ItemsDomainMapper.toBO(item))
                .collect(Collectors.toList());

        ShoppingCartBO response = cart.recalculate(itemsInternalUpdated);

        return ShoppingCartDomainMapper.toDTO(response);
    }
}
