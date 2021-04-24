package com.securityinnovation.urlshortner.service.impl;

import com.securityinnovation.urlshortner.entity.Role;
import com.securityinnovation.urlshortner.entity.User;
import com.securityinnovation.urlshortner.enums.RoleName;
import com.securityinnovation.urlshortner.payload.response.UserAuthenticationResponse;
import com.securityinnovation.urlshortner.security.JWTTokenProvider;
import com.securityinnovation.urlshortner.security.UserPrincipal;
import com.securityinnovation.urlshortner.service.AuthService;
import com.securityinnovation.urlshortner.service.RoleService;
import com.securityinnovation.urlshortner.service.UserService;

import java.util.Collections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * <h1>AuthServiceImpl</h1>
 * <p>
 *   This class implements methods of {@link AuthService} interface.
 * </p>
 *
 * @author Tanay
 * @version 1.0
 */
@Service
public class AuthServiceImpl implements AuthService {

  private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

  final UserService userService;
  final RoleService roleService;
  final JWTTokenProvider tokenProvider;
  final PasswordEncoder passwordEncoder;
  final AuthenticationManager authenticationManager;

  public AuthServiceImpl(UserService userService, RoleService roleService, JWTTokenProvider tokenProvider, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
    this.userService = userService;
    this.roleService = roleService;
    this.tokenProvider = tokenProvider;
    this.passwordEncoder = passwordEncoder;
    this.authenticationManager = authenticationManager;
  }

  /**
   * <h1>signInUser</h1>
   * <p>
   *   If user has provided valid credentials sends back newly generated access and refresh token.
   * </p>
   * @param usernameOrEmail - username or email
   * @param password - user's password
   * @return {@link UserAuthenticationResponse} - response containing access token and refresh token
   */
  @Override
  public UserAuthenticationResponse signInUser(String usernameOrEmail, String password) {
    logger.trace("Signing in the user : {}", usernameOrEmail);
    Authentication authentication = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(usernameOrEmail, password));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    return tokenProvider.generateToken(((UserPrincipal) authentication.getPrincipal()).getId());
  }

  /**
   * <h1>signUpUser</h1>
   * <p>
   *   Signs up user if user and sends generated access and refresh token.
   * </p>
   *
   * @param name - name
   * @param username - username
   * @param email - email
   * @param password - password
   * @return {@link UserAuthenticationResponse} - response containing access token and refresh token
   */
  @Override
  public UserAuthenticationResponse signUpUser(String name, String username, String email, String password) {
    logger.trace("Signing up the user : {}", name);
    // checking if username or email is not already in use
    userService.validateUniqueEmail(email);
    userService.validateUniqueUsername(username);

    // Persisting user in db
    User user = new User(name, username, email, password);
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    Role userRole = roleService.findRoleByName(RoleName.ROLE_USER);
    user.setRoles(Collections.singleton(userRole));
    user = userService.saveUser(user);

    return tokenProvider.generateToken(user.getId());
  }

  /**
   * <h1>getRefreshToken</h1>
   * <p>
   *   This method will generate new set of access and refresh token using old valid refresh token
   * </p>
   *
   * @param refreshToken - current refresh token
   * @return - {@link UserAuthenticationResponse} - response containing new set of access and refresh token
   */
  @Override
  public UserAuthenticationResponse getRefreshToken(String refreshToken){
    logger.trace("Delegating request for generating new set of tokens to token provider");
    return tokenProvider.generateNewTokens(refreshToken);
  }
}
