package com.securityinnovation.urlshortner.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;

import com.securityinnovation.urlshortner.service.UrlShortnerService;
import io.swagger.annotations.Api;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@Api(tags = "Redirection")
public class RedirectionController {

  private static final Logger logger = LoggerFactory.getLogger(RedirectionController.class);
  final UrlShortnerService urlShortnerService;

  public RedirectionController(UrlShortnerService urlShortnerService) {
    this.urlShortnerService = urlShortnerService;
  }

  @GetMapping("/{shortUrl}")
  public ResponseEntity.HeadersBuilder<?> redirectToOriginalUrl(@NotBlank @PathVariable String shortUrl, HttpServletResponse httpServletResponse){
    try {
      String originalUrl = urlShortnerService.getOriginalUrl(shortUrl);
      if(!originalUrl.isEmpty())
        httpServletResponse.sendRedirect(originalUrl);
      else
        return ResponseEntity.notFound();
    } catch (IOException e) {
      logger.error("Something went wrong while redirecting", e.getCause());
    }
    return null;
  }

}
