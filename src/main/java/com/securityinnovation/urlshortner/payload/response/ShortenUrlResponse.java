package com.securityinnovation.urlshortner.payload.response;

/**
 * <h1>ShortenUrlResponse</h1>
 * <p>This class is used to send response with a shortened url</p>
 *
 * @author Tanay
 * @version 1.0
 */
public class ShortenUrlResponse {

  private Long id;

  private String fullUrl;

  private String shortUrl;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFullUrl() {
    return fullUrl;
  }

  public void setFullUrl(String fullUrl) {
    this.fullUrl = fullUrl;
  }

  public String getShortUrl() {
    return shortUrl;
  }

  public void setShortUrl(String shortUrl) {
    this.shortUrl = shortUrl;
  }
}
