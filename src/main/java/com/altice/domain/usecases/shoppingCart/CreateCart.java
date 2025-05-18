package com.altice.domain.usecases.shoppingCart;

import com.altice.domain.dto.ShoppingCartDTO;
import com.altice.domain.mappers.ShoppingCartDomainMapper;
import com.altice.domain.repositories.IShoppingCartRepository;

public class CreateCart {

    private final IShoppingCartRepository cartRepository;

    public CreateCart(IShoppingCartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public ShoppingCartDTO execute(ShoppingCartDTO cart) {
        var response = cartRepository.save(ShoppingCartDomainMapper.toBO(cart));
        return ShoppingCartDomainMapper.toDTO(response);
    }
}
