package com.securityinnovation.urlshortner.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.securityinnovation.urlshortner.config.SecurityConfig;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 *  <h1>JWTAuthenticationEntryPoint</h1>
 *  <p>
 *    This class extends {@link AuthenticationEntryPoint} and
 *    used to respond if any authorization error occurs in authentication process.
 *
 *    It is autowired in {@link SecurityConfig } and used as exception handler for authentication process.
 *  </p>
 *
 * @author Tanay
 * @version 1.0
 */
@Component
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {

  private static final Logger logger = LoggerFactory.getLogger(JWTAuthenticationEntryPoint.class);

  /**
   * @param httpServletRequest - http request
   * @param httpServletResponse - http response
   * @param e - authentication exception
   * @throws IOException - input / output exception
   * @see SecurityConfig
   *
   * <h1>Un-Authorized Request Handler</h1>
   * <p>
   *   logging authentication error and sending un-authorized response
   * </p>
   */
  @Override
  public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
    AuthenticationException e) throws IOException {
    logger.error("Unauthorized user : Message - {}  {}", e.getMessage(), httpServletRequest.getRequestURL());
    httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
  }
}
