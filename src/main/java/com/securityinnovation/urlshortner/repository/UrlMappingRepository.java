package com.securityinnovation.urlshortner.repository;

import com.securityinnovation.urlshortner.entity.UrlMapping;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlMappingRepository extends JpaRepository<UrlMapping, Long> {

  Optional<UrlMapping> findByShortUrl(String shortUrl);

  List<UrlMapping> findByCreatedByOrderByCreatedAtDesc(Long createdByUserId);

  Optional<UrlMapping> findByIdAndCreatedBy(Long urlMappingId , Long userId);

  Integer deleteByIdAndCreatedBy(Long urlMappingId, Long userId);
}
