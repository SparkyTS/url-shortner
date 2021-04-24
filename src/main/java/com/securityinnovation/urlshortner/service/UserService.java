package com.securityinnovation.urlshortner.service;

import com.securityinnovation.urlshortner.entity.User;

/**
 * <h1>UserService</h1>
 * <p>
 *   This interface will defined methods required to implement user related service.
 * </p>
 *
 * @author Tanay
 * @version 1.0
 */
public interface UserService {

  void validateUniqueUsername(String username);

  void validateUniqueEmail(String email);

  User saveUser(User user);
}
