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
  ENTER_VALID_URL("Please provide valid full url for shortening it !"),
  URL_MAPPINGS_RETRIEVED_SUCCESSFULLY("Url mappings retrieved successfully !"),
  URL_MAPPING_RETRIEVED_SUCCESSFULLY("Url mapping retrieved successfully !"),
  URL_MAPPING_UPDATED_SUCCESSFULLY("Shortened url updated successfully !"),
  URL_DELETED_SUCCESSFULLY("Url mapping record deleted successfully !");

  private String message;

  ShortenUrlMessage(String message) {
    this.message = message;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
