package com.altice.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.altice.domain.dto.AddUserDTO;
import com.altice.domain.dto.ItemDTO;
import com.altice.domain.dto.ProductDTO;
import com.altice.domain.dto.ShoppingCartDTO;
import com.altice.domain.dto.UserDTO;
import com.altice.domain.enums.EnumErrorCode;
import com.altice.domain.enums.EnumShoppingStatus;
import com.altice.domain.usecases.shoppingCart.AddItemsCart;
import com.altice.domain.usecases.shoppingCart.AddQuantityItems;
import com.altice.domain.usecases.shoppingCart.CreateCart;
import com.altice.domain.usecases.shoppingCart.FindCart;
import com.altice.domain.usecases.shoppingCart.RemoveCart;
import com.altice.domain.usecases.shoppingCart.RemoveItemsCart;
import com.altice.domain.usecases.shoppingCart.UpdatedCart;
import com.altice.domain.utils.StringUtils;
import com.altice.domain.utils.exception.AlticeException;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ShoppingCartService extends AbstractService {

    @Transactional
    public ShoppingCartDTO create(ShoppingCartDTO cart) {
        defineParamsCart(cart, true);
        return new CreateCart(cartRepository).execute(cart);
    }

    public ShoppingCartDTO findById(String id) {
        validateUUID(id);
        return new FindCart(cartRepository).execute(id);
    }

    @Transactional
    public void deleteById(String id) {
        validateUUID(id);
        new RemoveCart(cartRepository).execute(id);
    }

    @Transactional
    public ShoppingCartDTO updatedById(ShoppingCartDTO cart, String id, boolean updatedItems) {
        validateUUID(id);
        defineParamsCart(cart, updatedItems);
        return new UpdatedCart(cartRepository).execute(cart, id);
    }

    public ShoppingCartDTO clearCart(String id) {
        ShoppingCartDTO cart = findById(id);
        cart.setItems(new ArrayList<>());
        return updatedById(cart, id, true);
    }

    public ShoppingCartDTO addItems(List<ItemDTO> items, String idCart) {
        validateUUID(idCart);
        defineItemsCart(items);
        ShoppingCartDTO response = new AddItemsCart(cartRepository).execute(items, idCart);
        return updatedById(response, idCart, true);
    }

    public ShoppingCartDTO removeItems(List<ItemDTO> items, String idCart) {
        validateUUID(idCart);
        defineItemsCart(items);
        ShoppingCartDTO response = new RemoveItemsCart(cartRepository).execute(items, idCart);
        return updatedById(response, idCart, false);
    }

    public ShoppingCartDTO addUser(AddUserDTO inputAddUser) {
        ShoppingCartDTO cart = findById(inputAddUser.getIdCart());
        validateUUID(inputAddUser.getIdUser());
        cart.setUserId(inputAddUser.getIdUser());
        return updatedById(cart, inputAddUser.getIdCart(), false);
    }

    public void defineParamsCart(ShoppingCartDTO cart, boolean updatedItems) {
        if (cart == null) {
            throw new AlticeException(EnumErrorCode.REQUIRED_OBJECT, "ShoppingCart");
        }
        if (updatedItems) {
            solveItems(cart);
        }
        determineStatus(cart);
        defineUserCart(cart);
    }

    public void solveItems(ShoppingCartDTO cart) {
        if (cart.getItems() != null) {
            defineItemsCart(cart.getItems());
            calculateAmountCart(cart);
        } else {
            cart.setAmount(BigDecimal.ZERO);
        }
    }

    public void calculateAmountCart(ShoppingCartDTO cart) {
        BigDecimal totalAmount = cart.getItems().stream()
                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        cart.setAmount(totalAmount);
    }

    public void defineItemsCart(List<ItemDTO> items) {
        items.forEach(
                item -> {
                    if (item.getProduct() == null) {
                        ProductDTO product = productService.findById(item.getProductId());
                        item.setProduct(product);
                    }
                });
    }

    public void defineUserCart(ShoppingCartDTO cart) {
        if (StringUtils.isNotNullOrEmpty(cart.getUserId())) {
            UserDTO user = userService.findById(cart.getUserId());
            cart.setUser(user);
        }
    }

    private static void determineStatus(ShoppingCartDTO cart) {
        if (cart.getItems() == null) {
            cart.setStatus(EnumShoppingStatus.EMPTY.getValue());
        } else {
            cart.setStatus(EnumShoppingStatus.ACTIVE.getValue());
        }
    }

    public ShoppingCartDTO addQuantityItems(List<ItemDTO> items, String id) {
        validateUUID(id);
        ShoppingCartDTO response = new AddQuantityItems(cartRepository).execute(items, id);
        return updatedById(response, id, false);
    }

}
