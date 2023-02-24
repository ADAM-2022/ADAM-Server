package com.adam.server.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@ConfigurationProperties(prefix = "jwt.token")
public class JwtProperties {

  private final String headerKey;

  private final String issuer;

  private final String clientSecret;

  private final int expirySeconds;

  @ConstructorBinding
  public JwtProperties(String headerKey, String issuer, String clientSecret, int expirySeconds) {
    this.headerKey = headerKey;
    this.issuer = issuer;
    this.clientSecret = clientSecret;
    this.expirySeconds = expirySeconds;
  }

  public String getHeaderKey() {
    return headerKey;
  }

  public String getIssuer() {
    return issuer;
  }

  public String getClientSecret() {
    return clientSecret;
  }

  public int getExpirySeconds() {
    return expirySeconds;
  }
}
