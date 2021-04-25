package com.securityinnovation.urlshortner.service.impl;

import com.google.common.hash.Hashing;
import com.securityinnovation.urlshortner.entity.UrlMapping;
import com.securityinnovation.urlshortner.enums.messages.constraint.UrlConstraint;
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
import java.util.List;
import java.util.Optional;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
      if(shortUrl.length() > UrlConstraint.SHORT_URL_MAX_LENGTH.getValue()) {
        //throw exception if provided short url contains more than 15 characters
        throw new AppException(ErrorMessages.SHORT_URL_LENGTH_EXCEEDED, HttpStatus.BAD_REQUEST);
      }
      //check availability of valid custom short url if it is provided
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
   * @param userId - user id to retrieve all shorted url of that user
   * @return - list of all urls shortened by user
   */
  @Override
  public List<ShortenUrlResponse> getUrlMappings(Long userId) {
    List<UrlMapping> urlMappings = urlRepository.findByCreatedByOrderByCreatedAtDesc(userId);
    return dtoMapper.convertEntityListToDTOList(urlMappings, ShortenUrlResponse.class);
  }

  /**
   * @param urlMappingId - url mapping id
   * @param userId - logged in user's id
   * @return - returns shortened url mapping having given id
   */
  @Override
  public ShortenUrlResponse getUrlMappingById(Long urlMappingId, Long userId) {
    // get stored url mapping
    Optional<UrlMapping> urlMappingOptional = urlRepository.findByIdAndCreatedBy(urlMappingId, userId);

    //throw error if url mapping is not available for current user
    if(!urlMappingOptional.isPresent()){
      throw new AppException(ErrorMessages.URL_MAPPING_NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    return dtoMapper.convertToDto(urlMappingOptional.get(), ShortenUrlResponse.class);
  }


  /**
   * @param urlMappingId - url mapping id to update the url
   * @param newShortenUrl - new shortened url
   * @param userId - current user id
   * @return - updated mapping for shorten url
   */
  @Override
  public ShortenUrlResponse updateShortenedUrl(Long urlMappingId, String newShortenUrl, Long userId) {

    // get previously stored url mapping
    Optional<UrlMapping> urlMappingOptional = urlRepository.findByIdAndCreatedBy(urlMappingId, userId);

    //throw error if url mapping is not available for current user
    if(!urlMappingOptional.isPresent()){
      throw new AppException(ErrorMessages.URL_MAPPING_NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    if(newShortenUrl.length() > UrlConstraint.SHORT_URL_MAX_LENGTH.getValue()) {
      //throw exception if provided short url contains more than 15 characters
      throw new AppException(ErrorMessages.SHORT_URL_LENGTH_EXCEEDED, HttpStatus.BAD_REQUEST);
    }

    UrlMapping urlMapping = urlMappingOptional.get();
    //only store new url if it is not same as old one
    if(!urlMapping.getShortUrl().equals(newShortenUrl)){
      this.checkIfShortUrlIsAvailable(newShortenUrl);
      urlMapping.setShortUrl(newShortenUrl);
      urlMapping = urlRepository.save(urlMapping);
    }

    return dtoMapper.convertToDto(urlMapping, ShortenUrlResponse.class);
  }

  /**
   * @param mappingId - url mapping id
   * @param userId - logged in user's id
   */
  @Override
  @Transactional
  public void deleteUrlMapping(Long mappingId, Long userId) {
    Integer noOfRecordsDeleted = urlRepository.deleteByIdAndCreatedBy(mappingId, userId);
    if(noOfRecordsDeleted==0){
      throw new AppException(ErrorMessages.URL_MAPPING_NOT_FOUND, HttpStatus.NOT_FOUND);
    }
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
