package com.securityinnovation.urlshortner.controller;

import javax.validation.Valid;

import com.securityinnovation.urlshortner.enums.messages.user.ShortenUrlMessage;
import com.securityinnovation.urlshortner.payload.request.ShortenUrlRequest;
import com.securityinnovation.urlshortner.payload.response.ApiResponse;
import com.securityinnovation.urlshortner.payload.response.ShortenUrlResponse;
import com.securityinnovation.urlshortner.security.CurrentUser;
import com.securityinnovation.urlshortner.security.UserPrincipal;
import com.securityinnovation.urlshortner.service.UrlShortnerService;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * <h1>UrlMappingController</h1>
 * <p>
 *   This controller will handle all the request related to url's details.
 * </p>
 *
 * @author Tanay
 * @version 1.0
 */
@RestController
@RequestMapping("/api/v1/urls")
@Validated
@Api(tags = "urls-shortening")
public class UrlMappingController {

  final UrlShortnerService urlShortnerService;

  public UrlMappingController(UrlShortnerService urlShortnerService) {
    this.urlShortnerService = urlShortnerService;
  }

  /**
   * @param shortenUrlRequest - request containing full url which is to be shortened
   * @return - valid response with shortened url
   */
  @PostMapping
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<ApiResponse> shortenTheUrl(@Valid @RequestBody ShortenUrlRequest shortenUrlRequest, @ApiIgnore @CurrentUser UserPrincipal currentUser){
    ShortenUrlResponse shortenUrlResponse = urlShortnerService.shortenTheUrl(shortenUrlRequest, currentUser.getId());
    return ResponseEntity.ok(new ApiResponse(Boolean.TRUE, ShortenUrlMessage.URL_SHORTENED, shortenUrlResponse));
  }

}
