package com.altice.domain.usecases.shoppingCart;

import java.util.UUID;

import com.altice.domain.enums.EnumErrorCode;
import com.altice.domain.repositories.IShoppingCartRepository;
import com.altice.domain.utils.StringUtils;
import com.altice.domain.utils.exception.AlticeException;

public class RemoveCart {

    private final IShoppingCartRepository cartRepository;

    public RemoveCart(IShoppingCartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public void execute(String id) {

        if (StringUtils.isNullOrEmpty(id)) {
            throw new AlticeException(EnumErrorCode.REQUIRED_FIELD_FOR, "id", "remove shoppingCart");
        }
        cartRepository.remove(UUID.fromString(id));
    }
}
