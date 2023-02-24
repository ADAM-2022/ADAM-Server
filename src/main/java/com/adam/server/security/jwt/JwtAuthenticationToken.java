package com.adam.server.security.jwt;

import com.adam.server.auth.presentation.dto.LoginRequestDto;
import com.adam.server.user.domain.Email;
import java.util.Collection;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {
  private final Object principal;
  private String credentials;

  public JwtAuthenticationToken(Email principal, String credentials) {
    super(null);
    super.setAuthenticated(false);

    this.principal = principal;
    this.credentials = credentials;
  }

  public JwtAuthenticationToken(
      Object principal, String credentials, Collection<? extends GrantedAuthority> authorities) {
    super(authorities);
    super.setAuthenticated(true);

    this.principal = principal;
    this.credentials = credentials;
  }

  @Override
  public void setAuthenticated(boolean authenticated) {
    if (authenticated) {
      throw new IllegalArgumentException(
          "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list"
              + "instead");
    }
    super.setAuthenticated(false);
  }

  @Override
  public Object getCredentials() {
    return credentials;
  }

  @Override
  public Object getPrincipal() {
    return principal;
  }

  @Override
  public void eraseCredentials() {
    super.eraseCredentials();
    credentials = null;
  }

  public LoginRequestDto loginRequest() {
    return new LoginRequestDto((Email) principal, credentials);
  }
}
