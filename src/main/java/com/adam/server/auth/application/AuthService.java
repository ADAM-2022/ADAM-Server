package com.adam.server.auth.application;

import static com.google.common.base.Preconditions.checkArgument;

import com.adam.server.auth.domain.RefreshToken;
import com.adam.server.auth.domain.repository.RefreshTokenRepository;
import com.adam.server.auth.presentation.dto.JoinRequestDto;
import com.adam.server.auth.presentation.dto.LoginRequestDto;
import com.adam.server.common.error.NotFoundException;
import com.adam.server.common.model.Id;
import com.adam.server.security.jwt.Jwt;
import com.adam.server.security.jwt.Jwt.Claims;
import com.adam.server.user.domain.Email;
import com.adam.server.user.domain.Role;
import com.adam.server.user.domain.User;
import com.adam.server.user.domain.repository.UserRepository;
import com.auth0.jwt.exceptions.TokenExpiredException;
import java.time.Instant;
import java.time.ZoneOffset;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthService {

  private final Jwt jwt;
  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final RefreshTokenRepository refreshTokenRepository;

  public String accessToken(Id<User, Long> id, Role role) {
    Claims claims = Claims.of(id, role);
    return jwt.accessToken(claims);
  }

  @Transactional
  public String refreshToken(User user) {
    RefreshToken refreshToken =
        refreshTokenRepository.findByUser(user).orElseGet(() -> new RefreshToken(user));
    refreshToken.initialize();
    refreshTokenRepository.save(refreshToken);
    return refreshToken.getRefreshToken();
  }

  @Transactional(readOnly = true)
  public User login(LoginRequestDto dto) {
    checkArgument(dto != null, "login request dto must be provided");

    Email email = dto.getEmail();
    User user =
        userRepository
            .findByEmail(email)
            .orElseThrow(() -> new NotFoundException(User.class, email));
    user.validatePassword(passwordEncoder, dto.getPassword());
    return user;
  }

  @Transactional(readOnly = true)
  public String reissueAccessToken(String refreshToken) {
    RefreshToken refreshTokenEntity =
        refreshTokenRepository
            .findByRefreshToken(refreshToken)
            .orElseThrow(() -> new NotFoundException(RefreshToken.class, refreshToken));

    if (refreshTokenEntity.isExpired()) {
      Instant expiredAt = refreshTokenEntity.getExpiresAt().toInstant(ZoneOffset.UTC);
      throw new TokenExpiredException("The Refresh Token has expired.", expiredAt);
    }

    User user = refreshTokenEntity.getUser();
    Id<User, Long> id = Id.of(User.class, user.getId());
    Role role = user.getRole();
    return accessToken(id, role);
  }

  @Transactional
  public void join(JoinRequestDto dto) {
    String password = passwordEncoder.encode(dto.getPassword());
    User user = new User(dto.getEmail(), password, dto.getName(), dto.getSessionTime());
    userRepository.save(user);
  }
}
