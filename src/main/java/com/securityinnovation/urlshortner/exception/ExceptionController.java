package com.securityinnovation.urlshortner.exception;

import javax.validation.ConstraintViolationException;

import com.securityinnovation.urlshortner.payload.response.ApiResponse;
import java.util.List;
import java.util.Optional;
import org.modelmapper.spi.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * <h1>ExceptionController</h1>
 * <p>
 * This controller handles all the exception generated through out the application and
 * sends response with appropriate http status.
 * </p>
 *
 * @author Tanay
 * @version 1.0
 */
@ControllerAdvice
@EnableWebMvc
public class ExceptionController {

  /**
   * <h1>handleAppException</h1>
   * <p>
   * Handles app exception and returns valid error response
   * </p>
   *
   * @param appException - application exception
   * @return ResponseEntity<ApiResponse> - response with valid http status and error message
   */
  @ExceptionHandler(AppException.class)
  public ResponseEntity<ApiResponse> handleAppException(AppException appException) {
    return new ResponseEntity<>(new ApiResponse(Boolean.FALSE, appException.getMessage()),
      appException.getHttpStatus());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  ResponseEntity<ApiResponse> handleMethodArgumentNotValidException(
    MethodArgumentNotValidException methodArgumentNotValidException) {

    BindingResult result = methodArgumentNotValidException.getBindingResult();
    final List<FieldError> fieldErrors = result.getFieldErrors();
    Optional<ErrorMessage> errors = fieldErrors
      .stream()
      .map(fieldError -> new ErrorMessage("Field '" + fieldError.getField() + "' " + fieldError.getDefaultMessage()))
      .findFirst();
    String error = "Please make a valid request !";
    if(errors.isPresent())
      error = errors.get().getMessage();

    return new ResponseEntity<>(new ApiResponse(Boolean.FALSE, error),
      HttpStatus.BAD_REQUEST);
  }

  /**
   * <h1>ConstraintViolationException</h1>
   * <p>
   * Handles app exception and returns valid error response
   * </p>
   *
   * @param constraintViolationException - constraint violation exception
   * @return ResponseEntity<ApiResponse> - response with valid http status and error message
   */
  @ExceptionHandler(ConstraintViolationException.class)
  ResponseEntity<ApiResponse> handleConstraintViolationException(
    ConstraintViolationException constraintViolationException) {
    return new ResponseEntity<>(new ApiResponse(Boolean.FALSE,  constraintViolationException.getMessage()),
      HttpStatus.BAD_REQUEST);
  }

  /**
   * <h1>handleUserNotFoundException</h1>
   * <p>
   * Handles user not found exception and returns valid error response
   * </p>
   *
   * @param usernameNotFoundException - user not found exception
   * @return ResponseEntity<ApiResponse> - responds with 404 status code and user not found message
   */
  @ExceptionHandler(UsernameNotFoundException.class)
  ResponseEntity<ApiResponse> handleUserNotFoundException(UsernameNotFoundException usernameNotFoundException) {
    return new ResponseEntity<>(new ApiResponse(Boolean.FALSE, usernameNotFoundException.getMessage()),
      HttpStatus.NOT_FOUND);
  }

}
