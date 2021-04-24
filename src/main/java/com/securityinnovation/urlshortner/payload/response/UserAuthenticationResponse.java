package com.securityinnovation.urlshortner.payload.response;

/**
 * <h1>UserAuthenticationResponse</h1>
 * <p>
 *   It is used to send response after successful sign in or sign up
 * </p>
 *
 * @author Tanay
 * @version 1.0
 */
public class UserAuthenticationResponse {
  private String accessToken;
  private String refreshToken;
  private String tokenType = "Bearer";

  public UserAuthenticationResponse() {
  }

  public UserAuthenticationResponse(String accessToken, String refreshToken) {
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
  }

  public String getAccessToken() {
    return accessToken;
  }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}