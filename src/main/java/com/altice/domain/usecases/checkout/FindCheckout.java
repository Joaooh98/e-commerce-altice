package com.altice.domain.usecases.checkout;

import java.util.UUID;

import com.altice.domain.dto.CheckoutDTO;
import com.altice.domain.enums.EnumErrorCode;
import com.altice.domain.mappers.CheckoutDomainMapper;
import com.altice.domain.repositories.ICheckoutRepository;
import com.altice.domain.utils.StringUtils;
import com.altice.domain.utils.exception.AlticeException;

public class FindCheckout {

    private final ICheckoutRepository checkoutRepository;

    public FindCheckout(ICheckoutRepository checkoutRepository) {
        this.checkoutRepository = checkoutRepository;
    }

    public CheckoutDTO execute(String id) {
        if (StringUtils.isNullOrEmpty(id)) {
            throw new AlticeException(EnumErrorCode.REQUIRED_FIELD_FOR, "id", "find checkout");
        }

        var response = checkoutRepository.findById(UUID.fromString(id));

        if (response == null) {
            throw new AlticeException(EnumErrorCode.OBJECT_NOT_FOUND, "Checkout");
        }

        return CheckoutDomainMapper.toDTO(response);
    }
}