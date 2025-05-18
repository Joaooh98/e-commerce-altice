package com.altice.domain.usecases.checkout;

import java.time.LocalDateTime;
import java.util.UUID;

import com.altice.domain.bo.CheckoutBO;
import com.altice.domain.bo.ShoppingCartBO;
import com.altice.domain.dto.CheckoutDTO;
import com.altice.domain.enums.EnumCheckoutStatus;
import com.altice.domain.enums.EnumErrorCode;
import com.altice.domain.enums.EnumShoppingStatus;
import com.altice.domain.mappers.CheckoutDomainMapper;
import com.altice.domain.repositories.ICheckoutRepository;
import com.altice.domain.repositories.IShoppingCartRepository;
import com.altice.domain.utils.StringUtils;
import com.altice.domain.utils.exception.AlticeException;
import com.altice.domain.vo.UuidVO;

public class CreateCheckout {

    private final IShoppingCartRepository cartRepository;
    private final ICheckoutRepository checkoutRepository;

    public CreateCheckout(IShoppingCartRepository cartRepository, ICheckoutRepository checkoutRepository) {
        this.cartRepository = cartRepository;
        this.checkoutRepository = checkoutRepository;
    }

    public CheckoutDTO execute(String shoppingCartId) {
        if (StringUtils.isNullOrEmpty(shoppingCartId)) {
            throw new AlticeException(EnumErrorCode.REQUIRED_FIELD_FOR, "shoppingCartId", "checkout");
        }

        ShoppingCartBO cart = getAndValidateCart(shoppingCartId);

        CheckoutBO checkout = createCheckout(cart);

        CheckoutBO savedCheckout = checkoutRepository.save(checkout);

        return CheckoutDomainMapper.toDTO(savedCheckout);
    }

    private ShoppingCartBO getAndValidateCart(String cartId) {
        ShoppingCartBO cart = cartRepository.findById(UUID.fromString(cartId));

        if (cart == null) {
            throw new AlticeException(EnumErrorCode.OBJECT_NOT_FOUND, "Shopping Cart");
        }

        if (cart.getItems() == null || cart.getItems().isEmpty()) {
            throw new AlticeException(EnumErrorCode.CART_IS_EMPTY);
        }

        if (cart.getUser() == null) {
            throw new AlticeException(EnumErrorCode.CART_MUST_HAVE_USER);
        }

        if (EnumShoppingStatus.CHECKED_OUT.equals(cart.getStatus())) {
            throw new AlticeException(EnumErrorCode.CART_ALREADY_CHECKED_OUT, cartId);
        }

        return cart;
    }

    private CheckoutBO createCheckout(ShoppingCartBO cart) {
        String checkoutId = UUID.randomUUID().toString();
        String paymentUrl = generatePaymentUrl(checkoutId);

        return CheckoutBO.builder()
                .id(new UuidVO(checkoutId))
                .shoppingCartId(cart.getId())
                .userId(cart.getUser().getId())
                .amount(cart.getAmount())
                .status(EnumCheckoutStatus.PENDING)
                .paymentUrl(paymentUrl)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    private String generatePaymentUrl(String checkoutId) {
        return "https://payment.altice.com/checkout/" + checkoutId + "/pay";
    }
}