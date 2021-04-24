package com.securityinnovation.urlshortner.enums.messages.user;

import com.securityinnovation.urlshortner.enums.messages.Message;

/**
 * <h1>UserMessage</h1>
 * <p>
 *   All success responses for user related request
 * </p>
 *
 * @author Tanay
 * @version 1.0
 */
public enum UserMessage implements Message {
  DETAILS_OBTAINED("User details obtained successfully !"),
  USERNAME_AVAILABLE("Yes, username is available !"),
  EMAIL_AVAILABLE("Yes, email is available !");

  private String message;

  UserMessage(String message) {
    this.message = message;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
