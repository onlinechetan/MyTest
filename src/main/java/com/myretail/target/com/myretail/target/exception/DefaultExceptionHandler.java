package com.myretail.target.com.myretail.target.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Application exception handling scenarios.
 */
@RestControllerAdvice
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ProductNotFound.class)
    public ResponseEntity handleNotFoundException(ProductNotFound ex) {
        ExceptionReponse exceptionReponse= new ExceptionReponse(ex.getMessage());
        return new ResponseEntity(exceptionReponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductInternalServerError.class)
    public ResponseEntity handleInternalServerException(ProductInternalServerError ex) {
        ExceptionReponse exceptionReponse= new ExceptionReponse(ex.getMessage());
        return new ResponseEntity(exceptionReponse,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
