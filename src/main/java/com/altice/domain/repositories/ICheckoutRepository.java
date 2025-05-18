package com.altice.domain.repositories;

import java.util.UUID;

import com.altice.domain.bo.CheckoutBO;

public interface ICheckoutRepository {
    
    CheckoutBO save(CheckoutBO bo);

    CheckoutBO findById(UUID id);

    CheckoutBO updated(CheckoutBO bo);
}
