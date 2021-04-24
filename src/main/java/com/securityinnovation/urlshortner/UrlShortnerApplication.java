package com.securityinnovation.urlshortner;

import javax.annotation.PostConstruct;

import java.util.TimeZone;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

@SpringBootApplication
@EntityScan(basePackageClasses = { UrlShortnerApplication.class, Jsr310JpaConverters.class })
public class UrlShortnerApplication {

  public static void main(String[] args) {
    SpringApplication.run(UrlShortnerApplication.class, args);
  }

  @PostConstruct
  void init() {
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
  }
}
