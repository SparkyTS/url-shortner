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
  EMAIL_ALREADY_TAKEN("Email address is already registered !"),
  SOMETHING_WENT_WRONG("Something went wrong !"),
  SHORT_URL_NOT_AVAILABLE("The shorten url endpoint provided is already in use. Please try some different url endpoint."),
  SHORT_URL_NOT_VALID("Provided shorten url is not present. Please provide valid shortened url !"),
  URL_MAPPING_NOT_FOUND("Can not update as url mapping is not available"),
  SHORT_URL_LENGTH_EXCEEDED("Short url with more than 15 letters is not allowed"),
  INVALID_EMAIL("Please enter a valid email");

  private String message;

  ErrorMessages(String message) {
    this.message = message;
  }

  @Override
  public String getMessage() {
    return message;
  }
}