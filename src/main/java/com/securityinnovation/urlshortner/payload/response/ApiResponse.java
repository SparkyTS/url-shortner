package com.securityinnovation.urlshortner.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.securityinnovation.urlshortner.enums.messages.Message;

/**
 * <h1>ApiResponse</h1>
 * <p>
 *   This is the only class which is utilized to send uniform response through out the application.
 * </p>
 *
 * @author Tanay
 * @version 1.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {
  private Boolean success;
  private String message;
  private Object data;

  public ApiResponse(Boolean success, Message message) {
    this.success = success;
    this.message = message.getMessage();
  }

  public ApiResponse(Boolean success, String message) {
    this.success = success;
    this.message = message;
  }

  public ApiResponse(Boolean success, Message message, Object data) {
    this.success = success;
    this.message = message.getMessage();
    this.data = data;
  }

  public Boolean getSuccess() {
    return success;
  }

  public void setSuccess(Boolean success) {
    this.success = success;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
  }
}