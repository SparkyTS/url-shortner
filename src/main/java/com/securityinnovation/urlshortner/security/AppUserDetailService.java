package com.securityinnovation.urlshortner.security;


import com.securityinnovation.urlshortner.entity.User;
import com.securityinnovation.urlshortner.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <h1>AppUserDetailService</h1>
 * <p>
 *   This service will be used to load the registered user details
 * </p>
 *
 * @author Tanay
 * @version 1.0
 */
@Service
public class AppUserDetailService implements UserDetailsService {

  final UserRepository userRepository;

  public AppUserDetailService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
    // user can login with either username or email
    User user = userRepository
      .findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
      .orElseThrow(() -> new UsernameNotFoundException(
        String.format("Username or email is not registered : %s", usernameOrEmail)));

    return UserPrincipal.create(user);
  }

  @Transactional
  public UserDetails loadUserById(Long id) {
    User user = userRepository
      .findById(id)
      .orElseThrow(() -> new UsernameNotFoundException("User not found !"));

    return UserPrincipal.create(user);
  }
}