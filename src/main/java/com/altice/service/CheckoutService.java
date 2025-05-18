package com.altice.service;

import java.util.List;

import com.altice.domain.dto.CheckoutDTO;
import com.altice.domain.dto.ItemDTO;
import com.altice.domain.dto.PaymentDTO;
import com.altice.domain.dto.ShoppingCartDTO;
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
        CheckoutDTO reponse = new ProcessPayment(checkoutRepository, cartRepository).execute(paymentDTO);
        ShoppingCartDTO cart = cartService.findById(reponse.getShoppingCartId());

        List<ItemDTO> items = cart.getItems();
        productService.movimentStock(items);

        return reponse;
    }

    public CheckoutDTO findById(String id) {
        validateUUID(id);
        return new FindCheckout(checkoutRepository).execute(id);
    }

}