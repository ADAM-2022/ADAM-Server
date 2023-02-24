package com.adam.server.auth.presentation;

import com.adam.server.auth.presentation.dto.LoginRequestDto;
import com.adam.server.auth.presentation.dto.LoginResponseDto;
import com.adam.server.security.jwt.JwtAuthenticationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthenticationManager authenticationManager;

  @PostMapping("/login")
  public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto dto) {
    JwtAuthenticationToken jwtAuthenticationToken =
        new JwtAuthenticationToken(dto.getEmail(), dto.getPassword());
    Authentication authentication = authenticationManager.authenticate(jwtAuthenticationToken);
    SecurityContextHolder.getContext().setAuthentication(authentication);
    return ResponseEntity.ok((LoginResponseDto) authentication.getDetails());
  }
}
