package com.adam.server.config;

import com.adam.server.security.jwt.JwtAuthenticationTokenFilter;
import com.adam.server.user.domain.Role;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private final JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

  public SecurityConfig(JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter) {
    this.jwtAuthenticationTokenFilter = jwtAuthenticationTokenFilter;
  }

  @Bean
  public AuthenticationManager authenticationManager(List<AuthenticationProvider> providers) {
    return new ProviderManager(providers);
  }

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
        .requestMatchers("/api/auth/login")
        .permitAll()
        .requestMatchers("/api/auth/join")
        .permitAll()
        .requestMatchers("/api/user/exists")
        .permitAll()
        .requestMatchers("/api/**")
        .hasRole(Role.USER.name())
        .anyRequest()
        .permitAll()
        .and()
        .formLogin()
        .disable()
        .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }
}
