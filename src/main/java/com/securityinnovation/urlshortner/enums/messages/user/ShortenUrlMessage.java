package com.securityinnovation.urlshortner.enums.messages.user;

import com.securityinnovation.urlshortner.enums.messages.Message;

/**
 * <h1>ShortenUrlMessage</h1>
 * <p>
 *   All success responses for shorten url related request
 * </p>
 *
 * @author Tanay
 * @version 1.0
 */
public enum ShortenUrlMessage implements Message {
  URL_SHORTENED("Url shortened successfully !"),
  ENTER_VALID_URL("Please provide valid url for shortening !");
  private String message;

  ShortenUrlMessage(String message) {
    this.message = message;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
