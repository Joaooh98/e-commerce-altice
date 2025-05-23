package com.altice.service;

import java.util.UUID;

import com.altice.domain.enums.EnumErrorCode;
import com.altice.domain.utils.StringUtils;
import com.altice.domain.utils.exception.AlticeException;
import com.altice.infra.data.panache.repositories.CheckoutRepository;
import com.altice.infra.data.panache.repositories.ProductRepository;
import com.altice.infra.data.panache.repositories.ShoppingCartRepository;
import com.altice.infra.data.panache.repositories.UserRepository;

import jakarta.inject.Inject;

public abstract class AbstractService {
    // repositories

    @Inject
    ProductRepository productRepository;

    @Inject
    UserRepository userRepository;

    @Inject
    ShoppingCartRepository cartRepository;

    @Inject
    CheckoutRepository checkoutRepository;
    
    // services

    @Inject
    ShoppingCartService cartService;
    
    @Inject
    ProductService productService;

    @Inject
    UserService userService;

    @Inject
    CheckoutService checkoutService;

    @Inject
    AnalyticService analyticService;

    public static boolean isValidUuid(String uuidStr) {
        if (uuidStr == null || uuidStr.trim().isEmpty()) {
            return false;
        }

        try {
            UUID.fromString(uuidStr);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void validateUUID(String id) {
        if (StringUtils.isNullOrEmpty(id)) {
            throw new AlticeException(EnumErrorCode.REQUIRED_FIELD, "id");
        }

        if (!isValidUuid(id)) {
            throw new AlticeException(EnumErrorCode.INVALID_FORMAT_FIELD, id);
        }
    }
}
