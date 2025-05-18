package com.altice.domain.usecases.shoppingCart;

import java.util.UUID;

import com.altice.domain.dto.ShoppingCartDTO;
import com.altice.domain.enums.EnumErrorCode;
import com.altice.domain.mappers.ShoppingCartDomainMapper;
import com.altice.domain.repositories.IShoppingCartRepository;
import com.altice.domain.utils.StringUtils;
import com.altice.domain.utils.exception.AlticeException;

public class FindCart {

    private final IShoppingCartRepository cartRepository;

    public FindCart(IShoppingCartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public ShoppingCartDTO execute(String id) {
        if (StringUtils.isNullOrEmpty(id)) {
            throw new AlticeException(EnumErrorCode.REQUIRED_FIELD_FOR, "id", "find shoppingCart");
        }

        var response = cartRepository.findById(UUID.fromString(id));

        if (response == null) {
            throw new AlticeException(EnumErrorCode.OBJECT_NOT_FOUND, "ShoppingCart");
        }

        return ShoppingCartDomainMapper.toDTO(response);
    }
}
