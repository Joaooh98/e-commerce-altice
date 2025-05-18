package com.altice.presentation.controller;

import com.altice.service.AnalyticService;
import com.altice.service.CheckoutService;
import com.altice.service.ProductService;
import com.altice.service.ShoppingCartService;
import com.altice.service.UserService;

import jakarta.inject.Inject;

public class AbstractController {

    @Inject
    ProductService productService;

    @Inject
    UserService userService;

    @Inject
    ShoppingCartService shoppingCartService;

    @Inject
    CheckoutService checkoutService;

    @Inject
    AnalyticService analyticService;

}
