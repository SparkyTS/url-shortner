package com.securityinnovation.urlshortner.service;
import com.securityinnovation.urlshortner.payload.response.UserAuthenticationResponse;

/**
 * <h1>AuthService</h1>
 * <p>
 *   This interface will defined methods required to implement users authentication related service.
 * </p>
 *
 * @author Tanay
 * @version 1.0
 */
public interface AuthService {

  UserAuthenticationResponse signInUser(String usernameOrEmail, String password);

  UserAuthenticationResponse signUpUser(String name, String username, String email, String password);

  UserAuthenticationResponse getRefreshToken(String refreshToken);
}
