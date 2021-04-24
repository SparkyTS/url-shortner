package com.securityinnovation.urlshortner.enums.messages.constraint;

public enum UrlConstraint {

  SHORT_URL_MAX_LENGTH(15); //Also update value in @class ShortenUrlRequest
  private int value;

  UrlConstraint(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }
}
