package com.altice.domain.repositories;

import java.util.List;
import java.util.UUID;

import com.altice.domain.bo.ShoppingCartBO;

public interface IShoppingCartRepository {

    ShoppingCartBO save(ShoppingCartBO bo);

    List<ShoppingCartBO> findAll();

    ShoppingCartBO findById(UUID fromString);

    void remove(UUID fromString);

    ShoppingCartBO updated(ShoppingCartBO bo);

}
