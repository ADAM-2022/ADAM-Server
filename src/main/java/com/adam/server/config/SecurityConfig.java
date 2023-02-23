package com.adam.server.config;

import com.adam.server.user.domain.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf()
        .disable()
        .headers()
        .disable()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeHttpRequests()
        .requestMatchers("/api/login")
        .permitAll()
        .requestMatchers("/api/join")
        .permitAll()
        .requestMatchers("/api/user/exists")
        .permitAll()
        .requestMatchers("/api/**")
        .hasRole(Role.USER.name())
        .anyRequest()
        .permitAll()
        .and()
        .formLogin()
        .disable();
    return http.build();
  }
}
