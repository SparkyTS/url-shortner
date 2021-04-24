package com.securityinnovation.urlshortner.enums.messages.error;

import com.securityinnovation.urlshortner.enums.messages.Message;

/**
 * <h1>ErrorMessages</h1>
 * <p>
 *  This enum is used to manage all the error messages
 * </p>
 *
 * @author Tanay
 * @version 1.0
 */
public enum ErrorMessages implements Message {

  LOGIN_AGAIN("Please Log In Again !"),
  USERNAME_ALREADY_TAKEN("Username is already taken !"),
  EMAIL_ALREADY_TAKEN("Email address is already in use !"),
  SOMETHING_WENT_WRONG("Something went wrong !"),
  SHORT_URL_NOT_AVAILABLE("The shorten url end provided is not available. Please try some different url end."),
  SHORT_URL_NOT_VALID("Provided shorten url is not present. Please provide valid shortened url !");

  private String message;

  ErrorMessages(String message) {
    this.message = message;
  }

  @Override
  public String getMessage() {
    return message;
  }
}