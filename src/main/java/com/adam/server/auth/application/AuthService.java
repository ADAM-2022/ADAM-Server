package com.adam.server.auth.application;

import com.adam.server.auth.presentation.dto.LoginRequestDto;
import com.adam.server.common.model.Id;
import com.adam.server.security.jwt.Jwt;
import com.adam.server.security.jwt.Jwt.Claims;
import com.adam.server.user.domain.Role;
import com.adam.server.user.domain.User;
import com.adam.server.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {

  private final Jwt jwt;
  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;

  public String accessToken(Id<User, Long> id, Role role) {
    Claims claims = Claims.of(id, role);
    return jwt.accessToken(claims);
  }

  public String refreshToken(Id<User, Long> id) {
    return null;
  }

  public User login(LoginRequestDto dto) {
    return null;
  }
}
