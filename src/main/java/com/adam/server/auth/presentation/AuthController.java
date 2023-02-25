package com.adam.server.auth.presentation;

import com.adam.server.auth.application.AuthService;
import com.adam.server.auth.presentation.dto.JoinRequestDto;
import com.adam.server.auth.presentation.dto.LoginRequestDto;
import com.adam.server.auth.presentation.dto.LoginResponseDto;
import com.adam.server.security.jwt.JwtAuthenticationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthenticationManager authenticationManager;

  private final AuthService authService;

  @GetMapping("/{refreshToken}/reissue")
  public ResponseEntity<String> reissue(@PathVariable String refreshToken) {
    String accessToken = authService.reissueAccessToken(refreshToken);
    return ResponseEntity.ok(accessToken);
  }

  @PostMapping("/join")
  public ResponseEntity<Void> join(@RequestBody JoinRequestDto dto) {
    authService.join(dto);
    return ResponseEntity.ok(null);
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto dto) {
    JwtAuthenticationToken jwtAuthenticationToken =
        new JwtAuthenticationToken(dto.getEmail(), dto.getPassword());
    Authentication authentication = authenticationManager.authenticate(jwtAuthenticationToken);
    SecurityContextHolder.getContext().setAuthentication(authentication);
    return ResponseEntity.ok((LoginResponseDto) authentication.getDetails());
  }
}
