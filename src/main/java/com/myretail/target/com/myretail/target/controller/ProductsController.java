/*
  Copyright header.
 */
package com.myretail.target.com.myretail.target.controller;

import com.myretail.target.com.myretail.target.dto.ProductResponse;
import com.myretail.target.com.myretail.target.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Resource controller for working with products.
 */
@RestController
@RequestMapping("/products")
public class ProductsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductsController.class);
    private final ProductService productService;

    /**
     * Constructor that initializes parameters.
     * @param productService instance of product service
     */
    @Autowired
    ProductsController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Get details of a given product id. Will return information not found if the product id is not found.
     */
    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse product(@PathVariable(value="id") String id) {
        LOGGER.info("getting product details for {}", id);
        return  productService.getProductDetail(id);
    }
}
