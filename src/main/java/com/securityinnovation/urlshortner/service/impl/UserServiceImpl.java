package com.securityinnovation.urlshortner.service.impl;

import com.securityinnovation.urlshortner.entity.User;
import com.securityinnovation.urlshortner.enums.messages.error.ErrorMessages;
import com.securityinnovation.urlshortner.exception.AppException;
import com.securityinnovation.urlshortner.repository.UserRepository;
import com.securityinnovation.urlshortner.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * <h1>UserServiceImpl</h1>
 * <p>
 *   This class implements method(s) of {@link UserService} interface.
 * </p>
 *
 * @author Tanay
 * @version 1.0
 */
@Service
public class UserServiceImpl implements UserService {

  private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

  private final UserRepository userRepository;

  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /**
   * <h1>validateUniqueUsername</h1>
   * <p>
   *   This methods checks if user name is already present in the database or not
   *   If already present then throws the exception
   * </p>
   * @param username - username to check
   */
  @Override
  public void validateUniqueUsername(String username) {
    if (userRepository.existsByUsername(username)) {
      throw new AppException(ErrorMessages.USERNAME_ALREADY_TAKEN, HttpStatus.BAD_REQUEST);
    }
  }

  /**
   * <h1>validateUniqueEmail</h1>
   * <p>
   *   This methods checks if user email is already present in the database or not
   *   If already present then throws the exception
   * </p>
   * @param email - email to check
   */
  @Override
  public void validateUniqueEmail(String email) {
    if (userRepository.existsByEmail(email)) {
      throw new AppException(ErrorMessages.EMAIL_ALREADY_TAKEN, HttpStatus.BAD_REQUEST);
    }
  }

  /**
   * <h1>saveUser</h1>
   * <p>
   *   Saves user into the database
   * </p>
   * @param user - user which will be saved
   * */
  @Override
  public User saveUser(User user) {
    logger.trace("Persisting user ({}) in database", user.getUsername());
    return userRepository.save(user);
  }
}
