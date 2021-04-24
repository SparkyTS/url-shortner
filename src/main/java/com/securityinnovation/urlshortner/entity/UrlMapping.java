package com.securityinnovation.urlshortner.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.securityinnovation.urlshortner.entity.audit.UserDateAudit;
import org.hibernate.annotations.NaturalId;

@Entity
@Table(name = "url_mapping", uniqueConstraints = {
  @UniqueConstraint(columnNames = {
    "shorten_url"
  })
})
public class UrlMapping extends UserDateAudit {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Lob
  @Column(name="full_url")
  private String fullUrl;

  @NaturalId
  @NotBlank
  @Email
  @Column(name="shorten_url")
  private String shortUrl;

  public UrlMapping() {
  }

  public UrlMapping(Long id, @NotBlank String fullUrl, @NotBlank @Email String shortUrl) {
    this.id = id;
    this.fullUrl = fullUrl;
    this.shortUrl = shortUrl;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFullUrl() {
    return fullUrl;
  }

  public void setFullUrl(String fullUrl) {
    this.fullUrl = fullUrl;
  }

  public String getShortUrl() {
    return shortUrl;
  }

  public void setShortUrl(String shortUrl) {
    this.shortUrl = shortUrl;
  }
}
