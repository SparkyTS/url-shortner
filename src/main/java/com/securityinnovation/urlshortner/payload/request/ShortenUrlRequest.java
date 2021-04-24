package com.securityinnovation.urlshortner.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.lang.Nullable;

/**
 * <h1>ShortenUrlRequest</h1>
 * <p>
 *   This class represents request for shortening the full url.
 * </p>
 *
 * @author Tanay
 * @version 1.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShortenUrlRequest {

  @NotBlank
  private String fullUrl;

  @Nullable
  @Size(max=15, message = "Shorten url can have maximum 15 letter")
  private String customShortUrl;

  public String getFullUrl() {
    return fullUrl;
  }

  public void setFullUrl(String fullUrl) {
    this.fullUrl = fullUrl;
  }

  @Nullable
  public String getCustomShortUrl() {
    return customShortUrl;
  }

  public void setCustomShortUrl(@Nullable String customShortUrl) {
    this.customShortUrl = customShortUrl;
  }
}
