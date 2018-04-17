/**
 * Copyright header.
 */
package com.myretail.target.com.myretail.target.exception;

/**
 * Exception handler wrapper to communicate actual error response to the client.
 */
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionReponse {
    String errorMessage;
}
