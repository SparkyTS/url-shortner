package com.securityinnovation.urlshortner;

import javax.annotation.PostConstruct;

import com.securityinnovation.urlshortner.entity.Role;
import com.securityinnovation.urlshortner.enums.RoleName;
import com.securityinnovation.urlshortner.repository.RoleRepository;
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

  final RoleRepository repository;

  public UrlShortnerApplication(RoleRepository repository) {
    this.repository = repository;
  }

  @PostConstruct
  void init() {
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    repository.save(new Role(1L, RoleName.ROLE_USER));
    repository.save(new Role(2L, RoleName.ROLE_ADMIN));
  }
}
