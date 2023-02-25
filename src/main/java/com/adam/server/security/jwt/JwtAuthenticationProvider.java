package com.adam.server.security.jwt;

import com.adam.server.auth.application.AuthService;
import com.adam.server.auth.presentation.dto.LoginRequestDto;
import com.adam.server.auth.presentation.dto.LoginResponseDto;
import com.adam.server.common.model.Id;
import com.adam.server.user.domain.Role;
import com.adam.server.user.domain.User;
import java.util.List;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

  private final AuthService authService;

  public JwtAuthenticationProvider(AuthService authService) {
    this.authService = authService;
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
    return processUserAuthentication(jwtAuthenticationToken.loginRequest());
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
  }

  private Authentication processUserAuthentication(LoginRequestDto dto) {
    User user = authService.login(dto);

    Id<User, Long> id = Id.of(User.class, user.getId());
    Role role = user.getRole();

    JwtAuthentication jwtAuthentication = new JwtAuthentication(id, role);
    List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList(role.name());

    JwtAuthenticationToken authenticated =
        new JwtAuthenticationToken(jwtAuthentication, null, authorityList);

    String accessToken = authService.accessToken(id, role);
    String refreshToken = authService.refreshToken(user);
    authenticated.setDetails(new LoginResponseDto(accessToken, refreshToken));
    return authenticated;
  }
}
