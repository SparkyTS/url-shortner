package com.securityinnovation.urlshortner.controller;

import javax.validation.Valid;

import com.securityinnovation.urlshortner.enums.messages.user.ShortenUrlMessage;
import com.securityinnovation.urlshortner.payload.request.ShortenUrlRequest;
import com.securityinnovation.urlshortner.payload.response.ApiResponse;
import com.securityinnovation.urlshortner.payload.response.ShortenUrlResponse;
import com.securityinnovation.urlshortner.security.CurrentUser;
import com.securityinnovation.urlshortner.security.UserPrincipal;
import com.securityinnovation.urlshortner.service.UrlShortnerService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h1>UrlMappingController</h1>
 * <p>
 * This controller will handle all the request related to url's details.
 * </p>
 *
 * @author Tanay
 * @version 1.0
 */
@RestController
@RequestMapping("/api/v1/urls")
@Validated
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
  public ResponseEntity<ApiResponse> shortenTheUrl(@Valid @RequestBody ShortenUrlRequest shortenUrlRequest, @CurrentUser UserPrincipal userPrincipal) {
    ShortenUrlResponse shortenUrlResponse = urlShortnerService.shortenTheUrl(shortenUrlRequest, userPrincipal.getId());
    return new ResponseEntity<>((new ApiResponse(Boolean.TRUE, ShortenUrlMessage.URL_SHORTENED, shortenUrlResponse)),
      HttpStatus.CREATED);
  }

  /**
   * @param userPrincipal - current user stored in security context
   * @return - all urls shorted by current users
   */
  @GetMapping
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<ApiResponse> getShortenUrlMappings(@CurrentUser UserPrincipal userPrincipal) {
    List<ShortenUrlResponse> urlMappings = urlShortnerService.getUrlMappings(userPrincipal.getId());
    return ResponseEntity.ok(
      new ApiResponse(Boolean.TRUE, ShortenUrlMessage.URL_MAPPINGS_RETRIEVED_SUCCESSFULLY, urlMappings));
  }

  /**
   * @param urlMappingId - id of url mapping to retrieve
   * @param userPrincipal - current user stored in security context
   * @return - valid response containing url mapping
   */
  @GetMapping("{urlMappingId}")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<ApiResponse> getShortenUrlMappingById(@PathVariable Long urlMappingId,
    @CurrentUser UserPrincipal userPrincipal) {
    ShortenUrlResponse urlMapping = urlShortnerService.getUrlMappingById(urlMappingId, userPrincipal.getId());
    return ResponseEntity.ok(
      new ApiResponse(Boolean.TRUE, ShortenUrlMessage.URL_MAPPING_RETRIEVED_SUCCESSFULLY, urlMapping));
  }

  /**
   * @param urlMappingId  - url mapping id to update
   * @param newShortenUrl - new shorten url
   * @param userPrincipal - current user stored in security context
   * @return - returns valid response with newly shortened url
   */
  @PatchMapping("/{urlMappingId}/{newShortenUrl}")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<ApiResponse> updateShortenedEndpoint(@PathVariable Long urlMappingId,
    @PathVariable String newShortenUrl, @CurrentUser UserPrincipal userPrincipal) {
    ShortenUrlResponse shortenUrlResponse = urlShortnerService.updateShortenedUrl(urlMappingId, newShortenUrl,
      userPrincipal.getId());
    return ResponseEntity.ok(
      new ApiResponse(Boolean.TRUE, ShortenUrlMessage.URL_MAPPING_UPDATED_SUCCESSFULLY, shortenUrlResponse));
  }

  /**
   * @param urlMappingId  - url mapping id
   * @param userPrincipal - current user stored in security context
   * @return - No Content response if everything works fine
   */
  @DeleteMapping("{urlMappingId}")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<ApiResponse> deleteUrlMapping(@PathVariable Long urlMappingId,
    @CurrentUser UserPrincipal userPrincipal) {
    urlShortnerService.deleteUrlMapping(urlMappingId, userPrincipal.getId());
    return new ResponseEntity<>(new ApiResponse(Boolean.TRUE, ShortenUrlMessage.URL_DELETED_SUCCESSFULLY, null),
      HttpStatus.NO_CONTENT);
  }

}
