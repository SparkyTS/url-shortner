package com.securityinnovation.urlshortner.config;

import com.securityinnovation.urlshortner.security.AppUserDetailService;
import com.securityinnovation.urlshortner.security.JWTAuthenticationEntryPoint;
import com.securityinnovation.urlshortner.security.JWTAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * <h1>SecurityConfig</h1>
 * <p>
 *   All the security related configuration are done in this class.
 * </p>
 *
 * @author Tanay
 * @version 1.0
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  final AppUserDetailService appUserDetailService;
  final JWTAuthenticationEntryPoint unauthorizedHandler;
  final JWTAuthenticationFilter jwtAuthenticationFilter;

  public SecurityConfig(JWTAuthenticationEntryPoint unauthorizedHandler,
    JWTAuthenticationFilter jwtAuthenticationFilter, AppUserDetailService appUserDetailService) {
    this.unauthorizedHandler = unauthorizedHandler;
    this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    this.appUserDetailService = appUserDetailService;
  }

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(appUserDetailService)
        .passwordEncoder(bCryptPasswordEncoder());
  }

  @Bean(BeanIds.AUTHENTICATION_MANAGER)
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .cors()
        .and()
      .csrf()
        .disable()
      .exceptionHandling()
        .authenticationEntryPoint(unauthorizedHandler)
        .and()
      .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
      .authorizeRequests()
        .antMatchers("/api/v1/auth/**").permitAll()
        .antMatchers("/api/v1/user/hasUniqueName", "/api/v1/user/hasUniqueEmail", "/*").permitAll()
        .anyRequest().authenticated();

    // applying jwt authentication filter
    http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);


  }
}
