package com.altice.domain.usecases.shoppingCart;

import java.util.UUID;

import com.altice.domain.bo.ShoppingCartBO;
import com.altice.domain.dto.ShoppingCartDTO;
import com.altice.domain.enums.EnumErrorCode;
import com.altice.domain.mappers.ShoppingCartDomainMapper;
import com.altice.domain.repositories.IShoppingCartRepository;
import com.altice.domain.utils.StringUtils;
import com.altice.domain.utils.exception.AlticeException;

public class UpdatedCart {

    private final IShoppingCartRepository cartRepository;

    public UpdatedCart(IShoppingCartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public ShoppingCartDTO execute(ShoppingCartDTO cartUpdated, String id) {

        if (cartUpdated == null) {
            throw new AlticeException(EnumErrorCode.REQUIRED_OBJECT_FOR, "shoppingCart", "updated shoppingCart");
        }

        if (StringUtils.isNullOrEmpty(id)) {
            throw new AlticeException(EnumErrorCode.REQUIRED_FIELD_FOR, "id", "updated shoppingCart");
        }

        var internalCart = cartRepository.findById(UUID.fromString(id));

        if (internalCart == null) {
            throw new AlticeException(EnumErrorCode.OBJECT_NOT_FOUND, "ShoppingCart");
        }

        cartUpdated.setId(internalCart.getId().getValue().toString());
        ShoppingCartBO response = cartRepository.updated(ShoppingCartDomainMapper.toBO(cartUpdated));

        return ShoppingCartDomainMapper.toDTO(response);
    }
}
