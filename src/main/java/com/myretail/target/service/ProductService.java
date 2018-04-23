package com.myretail.target.service;

import com.myretail.target.dto.ProductResponse;

/**
 * Interface for Product service.
 */
public interface ProductService {
    /**
     * Interface for Product service.
     * @param  productId - unique id of a product
     * @return Data transfer object of ProductResponse
     */
    ProductResponse getProductDetail(String productId);
}
