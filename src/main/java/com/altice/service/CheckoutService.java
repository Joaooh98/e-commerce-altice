package com.altice.service;

import com.altice.domain.dto.CheckoutDTO;
import com.altice.domain.dto.PaymentDTO;
import com.altice.domain.usecases.checkout.CreateCheckout;
import com.altice.domain.usecases.checkout.FindCheckout;
import com.altice.domain.usecases.checkout.ProcessPayment;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class CheckoutService extends AbstractService {

    @Transactional
    public CheckoutDTO createCheckout(String shoppingCartId) {
        validateUUID(shoppingCartId);
        return new CreateCheckout(cartRepository, checkoutRepository).execute(shoppingCartId);
    }

    @Transactional
    public CheckoutDTO processPayment(PaymentDTO paymentDTO) {
        return new ProcessPayment(checkoutRepository, cartRepository).execute(paymentDTO);
    }

    public CheckoutDTO findById(String id) {
        validateUUID(id);
        return new FindCheckout(checkoutRepository).execute(id);
    }

}