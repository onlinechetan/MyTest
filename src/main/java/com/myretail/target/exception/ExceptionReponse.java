/*
  Copyright header.
 */
package com.myretail.target.exception;

/*
  Exception handler wrapper to communicate actual error response to the client.
 */
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionReponse {
    String errorMessage;
}
