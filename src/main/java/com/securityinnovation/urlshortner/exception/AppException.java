package com.securityinnovation.urlshortner.exception;

import com.securityinnovation.urlshortner.enums.messages.Message;
import org.springframework.http.HttpStatus;

/**
 * <h1>AppException</h1>
 * <p>
 *   This class is used to throw app related runtime exception.
 * </p>
 *
 * @author Tanay
 * @version 1.0
 */
public class AppException extends RuntimeException {

  private HttpStatus httpStatus;

  public AppException(Message errorMessages, HttpStatus httpStatus) {
    super(errorMessages.getMessage());
    this.httpStatus = httpStatus;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

}