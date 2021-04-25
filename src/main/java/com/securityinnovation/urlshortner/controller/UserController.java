package com.securityinnovation.urlshortner.controller;

import com.securityinnovation.urlshortner.enums.messages.user.UserMessage;
import com.securityinnovation.urlshortner.payload.response.ApiResponse;
import com.securityinnovation.urlshortner.payload.response.UserSummaryResponse;
import com.securityinnovation.urlshortner.security.CurrentUser;
import com.securityinnovation.urlshortner.security.UserPrincipal;
import com.securityinnovation.urlshortner.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h1>UserController</h1>
 * <p>
 *   This controller will handle all the request related to user's details.
 * </p>
 *
 * @author Tanay
 * @version 1.0
 */
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

  final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  /**
   * @param currentUser - obtained current user form security context
   * @return currently logged in user
   */
  @GetMapping("me")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<ApiResponse> getCurrentUser(@CurrentUser UserPrincipal currentUser) {
    UserSummaryResponse userSummary = new UserSummaryResponse(currentUser.getId(), currentUser.getUsername(), currentUser.getName(), currentUser.getEmail());
    return ResponseEntity.ok(new ApiResponse(true, UserMessage.DETAILS_OBTAINED, userSummary));
  }

  /**
   * @param username - username passed in the request parameter
   * @return success if username provided is unique
   */
  @GetMapping("hasUniqueName")
  public ResponseEntity<ApiResponse> checkIfUsernameIsUnique(@RequestParam(value = "username") String username) {
    userService.validateUniqueUsername(username);
    return ResponseEntity.ok(new ApiResponse(true, UserMessage.USERNAME_AVAILABLE));
  }

  /**
   * @param email - email passed in the request parameter
   * @return success if email provided is unique
   */
  @GetMapping("hasUniqueEmail")
  public ResponseEntity<ApiResponse> checkIfEmailIsUnique(@RequestParam(value = "email") String email) {
    userService.validateUniqueEmail(email);
    return ResponseEntity.ok(new ApiResponse(true, UserMessage.EMAIL_AVAILABLE));
  }
}
