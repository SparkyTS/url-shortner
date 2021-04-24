package com.securityinnovation.urlshortner.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperBeanProvider {

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }

}
