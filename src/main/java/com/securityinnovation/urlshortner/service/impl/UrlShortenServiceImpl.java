package com.securityinnovation.urlshortner.service.impl;

import com.google.common.hash.Hashing;
import com.securityinnovation.urlshortner.entity.UrlMapping;
import com.securityinnovation.urlshortner.enums.messages.error.ErrorMessages;
import com.securityinnovation.urlshortner.enums.messages.user.ShortenUrlMessage;
import com.securityinnovation.urlshortner.exception.AppException;
import com.securityinnovation.urlshortner.mapper.DtoMapper;
import com.securityinnovation.urlshortner.payload.request.ShortenUrlRequest;
import com.securityinnovation.urlshortner.payload.response.ShortenUrlResponse;
import com.securityinnovation.urlshortner.repository.UrlMappingRepository;
import com.securityinnovation.urlshortner.service.UrlShortnerService;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Optional;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * <h1>UrlShortenServiceImpl</h1>
 * <p>
 * This class implements method(s) of {@link UrlShortnerService} interface.
 * </p>
 *
 * @author Tanay
 * @version 1.0
 */
@Service
public class UrlShortenServiceImpl implements UrlShortnerService {

  final UrlMappingRepository urlRepository;
  final DtoMapper dtoMapper;

  public UrlShortenServiceImpl(UrlMappingRepository urlRepository, DtoMapper dtoMapper) {
    this.urlRepository = urlRepository;
    this.dtoMapper = dtoMapper;
  }

  /**
   * @param shortenUrlRequest - http request body containing full url
   * @param userId - currently logged in user
   * @return - valid response with shorten url
   */
  @Override
  public ShortenUrlResponse shortenTheUrl(ShortenUrlRequest shortenUrlRequest, Long userId){

    String fullUrl = shortenUrlRequest.getFullUrl();
    String shortUrl = shortenUrlRequest.getCustomShortUrl();

    //checking if url is valid before converting to shorten url
    UrlValidator urlValidator = new UrlValidator();
    if(!urlValidator.isValid(fullUrl)){
      throw new AppException(ShortenUrlMessage.ENTER_VALID_URL, HttpStatus.BAD_REQUEST);
    }

    if(shortUrl!=null && !shortUrl.isEmpty()){
      //check availability of custom short url if it is provided
      this.checkIfShortUrlIsAvailable(shortUrl);
    }else{
      // shorten the url using hashing technique
      shortUrl = this.convertToShortenUrl(fullUrl, LocalDateTime.now().toString() + userId);
    }

    UrlMapping urlMapping = urlRepository.save(new UrlMapping(null, fullUrl, shortUrl));
    return dtoMapper.convertToDto(urlMapping, ShortenUrlResponse.class);
  }

  /**
   * @param shortenUrl - shorten url to look up
   * @return - original full url
   */
  @Override
  public String getOriginalUrl(String shortenUrl) {
    Optional<UrlMapping> urlMapping = urlRepository.findByShortUrl(shortenUrl);
    String fullUrl = "";
    if(urlMapping.isPresent())
      fullUrl = urlMapping.get().getFullUrl();
    return fullUrl;
  }

  /**
   * Helper method to check if the shorten url is already exist in db
   * @param shortUrl - short url to check
   */
  private void checkIfShortUrlIsAvailable(String shortUrl){
    boolean urlInUse;
    if (!shortUrl.isEmpty()) {
      urlInUse = urlRepository.findByShortUrl(shortUrl).isPresent();
      if(urlInUse){
        throw new AppException(ErrorMessages.SHORT_URL_NOT_AVAILABLE, HttpStatus.BAD_REQUEST);
      }
    }
  }

  /**
   * Helper method to shrink (shorten) the string (url)
   * @param url - url to shrink using hashing
   * @param saltString - extra unique information before hashing
   * @return - shorten string (url mainly)
   */
  private String convertToShortenUrl(String url, String saltString) {
    return Hashing.murmur3_32()
      .hashString(url.concat(saltString), StandardCharsets.UTF_8).toString();
  }

}
