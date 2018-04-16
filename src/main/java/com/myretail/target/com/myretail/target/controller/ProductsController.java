/**
 * Copyright header.
 */
package com.myretail.target.com.myretail.target.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myretail.target.com.myretail.target.dto.Price;
import com.myretail.target.com.myretail.target.dto.ProductDetail;
import com.myretail.target.com.myretail.target.dto.ProductResponse;
import com.myretail.target.com.myretail.target.service.NoSqlService;
import com.myretail.target.com.myretail.target.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * Resource controller for working with products.
 */
@RestController
@RequestMapping("/products")
public class ProductsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductsController.class);
    private final ProductService productService;
    private NoSqlService noSqlService;

    /**
     * Constructor that initializes parameters.
     * @param productService
     * @param noSqlService
     */
    @Autowired
    public ProductsController(ProductService productService, NoSqlService noSqlService) {
        this.productService = productService;
        this.noSqlService = noSqlService;
    }

    /**
     * Get details of a given product id. Will return information not found if the product id is not found.
     */
    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse product(@PathVariable(value="id") String id) {
        LOGGER.info("getting product details for {}", id);
        ProductResponse productResponse = new ProductResponse();
        //get product response
        ProductDetail productDetail = productService.getProductDetails(id);
        LOGGER.debug("executed product service {}", productDetail.getId());
        productResponse.setId(productDetail.getId());
        productResponse.setName(productDetail.getTitle());
        //get price details from no sql
        Price price = noSqlService.getProductPricing(id);
        LOGGER.debug("Executed no sql service {} ", id);
        price.setValue(price.getValue());
        price.setCurrency_code(price.getCurrency_code());
        productResponse.setCurrency_price(price);
        return productResponse;
    }
}
