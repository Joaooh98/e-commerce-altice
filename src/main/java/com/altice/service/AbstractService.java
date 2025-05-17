package com.altice.service;

import org.jboss.logging.Logger;

import com.altice.infra.data.panache.repositories.ProductRepository;

import jakarta.inject.Inject;

public abstract class AbstractService {
    // repositories

    @Inject
    ProductRepository productRepository;

    // services

    @Inject
    ProductService productService;

    @Inject
    Logger logger;

}
