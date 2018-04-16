package com.myretail.target.com.myretail.target.service;

import com.myretail.target.com.myretail.target.dto.Price;

/**
 * Interface for no sql database interactions.
 */
public interface NoSqlService {
    Price getProductPricing(String productId);
}
