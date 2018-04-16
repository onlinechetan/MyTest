/**
 * Copyright header.
 */
package com.myretail.target.com.myretail.target.exception;

/**
 * Exception handling for product not found.
 */
public class ProductNotFound extends RuntimeException{
    public ProductNotFound(String errorMessage) {
        super(errorMessage);
    }
}
