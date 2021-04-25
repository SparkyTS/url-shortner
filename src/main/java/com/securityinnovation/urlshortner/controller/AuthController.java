package com.securityinnovation.urlshortner.controller;

import javax.validation.Valid;

import com.securityinnovation.urlshortner.enums.messages.user.UserMessage;
import com.securityinnovation.urlshortner.payload.request.UserLoginRequest;
import com.securityinnovation.urlshortner.payload.request.UserSignUpRequest;
import com.securityinnovation.urlshortner.payload.response.ApiResponse;
import com.securityinnovation.urlshortner.payload.response.UserAuthenticationResponse;
import com.securityinnovation.urlshortner.repository.UserRepository;
import com.securityinnovation.urlshortner.security.JWTTokenProvider;
import com.securityinnovation.urlshortner.service.AuthService;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h1>AuthController</h1>
 * <p>
 *   This controller will handle all the request related to user's authentication.
 * </p>
 *
 * @author Tanay
 * @version 1.0
 */
@RestController
@RequestMapping("/api/v1/auth")
@Validated
@Api(tags = "auth")
public class AuthController {

  final AuthService authService;
  final JWTTokenProvider tokenProvider;
  final UserRepository userRepository;

  public AuthController(UserRepository userRepository, AuthService authService, JWTTokenProvider tokenProvider) {
    this.userRepository = userRepository;
    this.authService = authService;
    this.tokenProvider = tokenProvider;
  }

  /**
   * @param loginRequest - details required for user sign in process
   * @return access token and refresh token for further authentication purpose
   */
  @PostMapping("signIn")
  public ResponseEntity<ApiResponse> signInUser(@Valid @RequestBody UserLoginRequest loginRequest) {
    UserAuthenticationResponse userAuthenticationResponse = authService.signInUser(loginRequest.getUsernameOrEmail(), loginRequest.getPassword());
    return ResponseEntity.ok(new ApiResponse(Boolean.TRUE, UserMessage.LOGIN_SUCCESSFUL, userAuthenticationResponse));
  }

  /**
   * @param signUpRequest - details required to perform user sign up process
   * @return access token and refresh token for further authentication purpose
   */
  @PostMapping("signUp")
  public ResponseEntity<ApiResponse> signUpUser(@Valid @RequestBody UserSignUpRequest signUpRequest) {
    UserAuthenticationResponse userAuthenticationResponse = authService.signUpUser(signUpRequest.getName(), signUpRequest.getUsername(), signUpRequest.getEmail(),
      signUpRequest.getPassword());
    return new ResponseEntity<>(new ApiResponse(Boolean.TRUE, UserMessage.SIGNUP_SUCCESSFUL, userAuthenticationResponse),HttpStatus.CREATED);
  }

  /**
   * @param userAuthenticationResponse - will contain refresh token
   * @return new set of access token and refresh token for further authentication process
   */
  @PostMapping("refreshToken")
  public ResponseEntity<ApiResponse> getRefreshToken(
    @RequestBody UserAuthenticationResponse userAuthenticationResponse) {
    UserAuthenticationResponse userAuthenticationResponse1 = authService.getRefreshToken(userAuthenticationResponse.getRefreshToken());
    return new ResponseEntity<>(new ApiResponse(Boolean.TRUE, UserMessage.REFRESH_CREDS, userAuthenticationResponse1),HttpStatus.CREATED);
  }
}