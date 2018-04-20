package com.myretail.target.com.myretail.target.service;

import com.myretail.target.com.myretail.target.dto.Price;

import java.util.List;

/**
 * Interface for no sql database interactions.
 */
public interface NoSqlService {
    List<Price> getProductPricing(String productId);
}
