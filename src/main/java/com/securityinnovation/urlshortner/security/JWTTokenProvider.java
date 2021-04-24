package com.securityinnovation.urlshortner.security;

import com.securityinnovation.urlshortner.enums.messages.error.ErrorMessages;
import com.securityinnovation.urlshortner.exception.AppException;
import com.securityinnovation.urlshortner.payload.response.UserAuthenticationResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * <h1>JWTTokenProvider</h1>
 *
 * <p>
 *   This class is used to provide services related to JWT(Json Web Tokens)
 * </p>
 *
 * @author Tanay
 * @version 1.0
 */
@Component
public class JWTTokenProvider {
  private static final Logger logger = LoggerFactory.getLogger(JWTTokenProvider.class);

  @Value("${app.jwtAccessTokenSecret}")
  private String accessTokenSecret;

  @Value("${app.jwtAccessTokenExpirationInMs}")
  private Long accessTokenExpirationInMs;

  @Value("${app.jwtRefreshTokenSecret}")
  private String refreshTokenSecret;

  @Value("${app.jwtRefRefreshTokenExpirationInMs}")
  private Long refreshTokenExpirationInMs;

  /**
   * <h1>generateToken</h1>
   * <p>
   *   Generates JWT token with embedded user id
   * </p>
   * @param userId - user id to embed in jwt token
   * @return {@link UserAuthenticationResponse} - generated access token and refresh token
   */
  public UserAuthenticationResponse generateToken(Long userId) {
    Date now = new Date();
    Date accExpiryDate = new Date(now.getTime() + accessTokenExpirationInMs);
    Date refExpiryDate = new Date(now.getTime() + refreshTokenExpirationInMs);
    return new UserAuthenticationResponse(
      signToken(accessTokenSecret, userId, SignatureAlgorithm.HS384, accExpiryDate),
      signToken(refreshTokenSecret,userId, SignatureAlgorithm.HS512, refExpiryDate)
    );
  }

  /**
   * <h1>signToken</h1>
   * <p>
   *   Signs JWT token using provided signing algorithms
   * </p>
   * @param secret - jwt secret to sign token
   * @param userId - user id to embed in token
   * @param signatureAlgorithm - signature algorithm to be used while signing token
   * @param expDate - token expiration date
   * @return {@link String} -  generated token string
   */
  private String signToken(String secret, Long userId , SignatureAlgorithm signatureAlgorithm, Date expDate) {
    return Jwts.builder()
               .setSubject(userId.toString())
               .setIssuedAt(new Date())
               .setExpiration(expDate)
               .signWith(signatureAlgorithm, secret)
               .compact();
  }

  /**
   * <h1>getUserIdFromAccessToken</h1>
   * <p>
   *   Decodes user id from access token
   * </p>
   * @param accessToken - decodes user id form given access token
   * @return {@link Long} - decoded user id
   */
  public Long getUserIdFromAccessToken(String accessToken) {
    return getUserIdFromJWT(accessTokenSecret, accessToken);
  }

  /**
   * <h1>getUserIdFromJWT</h1>
   * <p>
   *   Decodes user id form jwt token
   * </p>
   * @param secret - secret to decode token
   * @param token - jwt token
   * @return {@link Long} - decoded user id
   */
  public Long getUserIdFromJWT(String secret, String token) {
    Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    return Long.parseLong(claims.getSubject());
  }

  /**
   * <h1>validateAccessToken</h1>
   * <p>
   *   Validates provided access token.
   * </p>
   * @param authToken - jwt token
   * @return true if provided token is valid
   */
  public boolean validateAccessToken(String authToken) {
    return validateToken(accessTokenSecret, authToken);
  }

  /**
   * <h1>validateToken</h1>
   * <p>
   *   Validates JWT token with secret
   * </p>
   * @param secret - secret used while generating the token
   * @param token - jwt token
   * @return true if token is valid, otherwise throws an exception
   */
  public boolean validateToken(String secret, String token) {
    try {
      Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
      return true;
    } catch (ExpiredJwtException ex) {
      logger.error("Expired JWT token");
    } catch (SignatureException ex) {
      logger.error("Invalid JWT signature");
    } catch (MalformedJwtException ex) {
      logger.error("Invalid JWT token");
    } catch (UnsupportedJwtException ex) {
      logger.error("Unsupported JWT token");
    } catch (IllegalArgumentException ex) {
      logger.error("JWT claims string is empty.");
    }
    throw new AppException(ErrorMessages.LOGIN_AGAIN, HttpStatus.UNAUTHORIZED);
  }

  /**
   * <h1>generateNewTokens</h1>
   * <p>
   *   Generates new set of access and refresh tokens if provided refresh token is valid.
   *   Otherwise throws an exception.
   * </p>
   * @param refreshToken - refresh token
   * @return {@link UserAuthenticationResponse} - new access token and refresh token
   */
  public UserAuthenticationResponse generateNewTokens(String refreshToken) {
    if (StringUtils.hasText(refreshToken)) {
      logger.trace("Validating refresh token : {}", refreshToken);
      validateToken(refreshTokenSecret, refreshToken);
      return generateToken(getUserIdFromJWT(refreshTokenSecret, refreshToken));
    } else {
      logger.error("Refresh token not found!");
      throw new AppException(ErrorMessages.LOGIN_AGAIN, HttpStatus.BAD_REQUEST);
    }
  }



}
