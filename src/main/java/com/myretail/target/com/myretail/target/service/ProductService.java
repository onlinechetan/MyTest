package com.myretail.target.com.myretail.target.service;

import com.myretail.target.com.myretail.target.dto.ProductDetail;

/**
 * Interface for Product service.
 */
public interface ProductService {
    /**
     * @param  productId - unique id of a product
     * @return
     */
    ProductDetail getProductDetails(String productId);
}
