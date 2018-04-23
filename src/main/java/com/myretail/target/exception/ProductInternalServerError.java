/*
  Copyright header.
 */
package com.myretail.target.exception;

/**
 * Exception handling for any server error.
 */
public class ProductInternalServerError extends RuntimeException{
    public ProductInternalServerError(String errorMessage) {
        super(errorMessage);
    }
}
