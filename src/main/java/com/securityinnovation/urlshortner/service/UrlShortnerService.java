package com.securityinnovation.urlshortner.service;

import com.securityinnovation.urlshortner.payload.request.ShortenUrlRequest;
import com.securityinnovation.urlshortner.payload.response.ShortenUrlResponse;
import java.util.List;

/**
 * <h1>UrlShortnerService</h1>
 * <p>
 *   This interface will defined method(s) required to implement service for shortening the url.
 * </p>
 *
 * @author Tanay
 * @version 1.0
 */
public interface UrlShortnerService {

  ShortenUrlResponse shortenTheUrl(ShortenUrlRequest shortenUrlRequest, Long userId);

  String getOriginalUrl(String shortenUrl);

  List<ShortenUrlResponse> getUrlMappings(Long userId);

  ShortenUrlResponse updateShortenedUrl(Long urlMappingId, String newShortenUrl, Long userId);

  void deleteUrlMapping(Long mappingId, Long userId);

  ShortenUrlResponse getUrlMappingById(Long urlMappingId, Long userId);
}
