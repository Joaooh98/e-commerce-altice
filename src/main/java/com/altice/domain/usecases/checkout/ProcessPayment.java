package com.altice.domain.usecases.checkout;

import java.time.LocalDateTime;
import java.util.UUID;

import com.altice.domain.bo.CheckoutBO;
import com.altice.domain.bo.ShoppingCartBO;
import com.altice.domain.dto.CheckoutDTO;
import com.altice.domain.dto.PaymentDTO;
import com.altice.domain.enums.EnumCheckoutStatus;
import com.altice.domain.enums.EnumErrorCode;
import com.altice.domain.enums.EnumPaymentType;
import com.altice.domain.enums.EnumShoppingStatus;
import com.altice.domain.mappers.CheckoutDomainMapper;
import com.altice.domain.repositories.ICheckoutRepository;
import com.altice.domain.repositories.IShoppingCartRepository;
import com.altice.domain.utils.StringUtils;
import com.altice.domain.utils.exception.AlticeException;

public class ProcessPayment {

    private final ICheckoutRepository checkoutRepository;
    private final IShoppingCartRepository cartRepository;

    public ProcessPayment(ICheckoutRepository checkoutRepository, IShoppingCartRepository cartRepository) {
        this.checkoutRepository = checkoutRepository;
        this.cartRepository = cartRepository;
    }

    public CheckoutDTO execute(PaymentDTO paymentDTO) {
        validatePaymentData(paymentDTO);

        CheckoutBO checkout = getAndValidateCheckout(paymentDTO.getCheckoutId());

        processPayment(paymentDTO);

        CheckoutBO updatedCheckout = updateCheckoutStatus(checkout, EnumCheckoutStatus.COMPLETED);

        updateCartStatus(checkout.getShoppingCartId().getValue());

        CheckoutBO savedCheckout = checkoutRepository.updated(updatedCheckout);

        return CheckoutDomainMapper.toDTO(savedCheckout);
    }

    private void validatePaymentData(PaymentDTO paymentDTO) {
        if (paymentDTO == null) {
            throw new AlticeException(EnumErrorCode.REQUIRED_OBJECT, "paymentDTO");
        }

        if (StringUtils.isNullOrEmpty(paymentDTO.getCheckoutId())) {
            throw new AlticeException(EnumErrorCode.REQUIRED_FIELD_FOR, "checkoutId", "payment");
        }

        if (StringUtils.isNullOrEmpty(paymentDTO.getPaymentType())) {
            throw new AlticeException(EnumErrorCode.REQUIRED_FIELD_FOR, "paymentType", "payment");
        }

        EnumPaymentType paymentType = EnumPaymentType.parseByValue(paymentDTO.getPaymentType());
        if (paymentType == null) {
            throw new AlticeException(EnumErrorCode.INVALID_PAYMENT_TYPE, paymentDTO.getPaymentType());
        }

        validatePaymentTypeData(paymentDTO, paymentType);
    }

    private void validatePaymentTypeData(PaymentDTO paymentDTO, EnumPaymentType paymentType) {
        switch (paymentType) {
            case CARD:
                if (StringUtils.isNullOrEmpty(paymentDTO.getCardNumber()) ||
                        StringUtils.isNullOrEmpty(paymentDTO.getCardHolderName()) ||
                        StringUtils.isNullOrEmpty(paymentDTO.getExpiryDate()) ||
                        StringUtils.isNullOrEmpty(paymentDTO.getCvv())) {
                    throw new AlticeException(EnumErrorCode.REQUIRED_FIELD_FOR, "card data", "payment");
                }
                break;
            case MBWAY:
                if (StringUtils.isNullOrEmpty(paymentDTO.getPhoneNumber())) {
                    throw new AlticeException(EnumErrorCode.REQUIRED_FIELD_FOR, "phoneNumber", "payment");
                }
                break;
        }
    }

    private CheckoutBO getAndValidateCheckout(String checkoutId) {
        CheckoutBO checkout = checkoutRepository.findById(UUID.fromString(checkoutId));

        if (checkout == null) {
            throw new AlticeException(EnumErrorCode.OBJECT_NOT_FOUND, "Checkout");
        }

        if (!EnumCheckoutStatus.PENDING.equals(checkout.getStatus())) {
            throw new AlticeException(EnumErrorCode.VALUE_NOT_ALLOWED, checkout.getStatus().getValue(),
                    "Checkout must be PENDING");
        }

        return checkout;
    }

    private void processPayment(PaymentDTO paymentDTO) {
        EnumPaymentType paymentType = EnumPaymentType.parseByValue(paymentDTO.getPaymentType());

        switch (paymentType) {
            case CARD:
                processCardPayment(paymentDTO);
                break;
            case MBWAY:
                processMbwayPayment(paymentDTO);
                break;
        }
    }

    private void processCardPayment(PaymentDTO paymentDTO) {
        String APPROVED_CARD = "1234567890123456";  
        String REJECTED_CARD = "0000000000000000";  
        
        String cardNumber = paymentDTO.getCardNumber();
        
        if (REJECTED_CARD.equals(cardNumber)) {
            throw new AlticeException(EnumErrorCode.PAYMENT_PROCESSING_FAILED, 
                "Card payment rejected - Card number: " + cardNumber);
        }
        
        if (APPROVED_CARD.equals(cardNumber)) {
            return;
        }
    }

    private void processMbwayPayment(PaymentDTO paymentDTO) {
        String APPROVED_PHONE = "912345678";  
        String REJECTED_PHONE = "900000000";   
        
        String phoneNumber = paymentDTO.getPhoneNumber();
        
        if (REJECTED_PHONE.equals(phoneNumber)) {
            throw new AlticeException(EnumErrorCode.PAYMENT_PROCESSING_FAILED, 
                "MBWay payment rejected - Phone: " + phoneNumber);
        }
        
        if (APPROVED_PHONE.equals(phoneNumber)) {
            return;
        }
        
    }

    private CheckoutBO updateCheckoutStatus(CheckoutBO checkout, EnumCheckoutStatus newStatus) {
        return CheckoutBO.builder()
                .id(checkout.getId())
                .shoppingCartId(checkout.getShoppingCartId())
                .userId(checkout.getUserId())
                .amount(checkout.getAmount())
                .status(newStatus)
                .paymentUrl(checkout.getPaymentUrl())
                .createdAt(checkout.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    private void updateCartStatus(UUID cartId) {
        ShoppingCartBO cart = cartRepository.findById(cartId);
        if (cart != null) {
            ShoppingCartBO updatedCart = ShoppingCartBO.builder()
                    .id(cart.getId())
                    .user(cart.getUser())
                    .items(cart.getItems())
                    .status(EnumShoppingStatus.CHECKED_OUT)
                    .createdAt(cart.getCreatedAt())
                    .updatedAt(LocalDateTime.now())
                    .amount(cart.getAmount())
                    .build();

            cartRepository.updated(updatedCart);
        }
    }
}