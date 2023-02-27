package com.adam.server.user.presentation;

import com.adam.server.security.jwt.JwtAuthentication;
import com.adam.server.user.application.UserService;
import com.adam.server.user.presentation.dto.UpdateRequestDto;
import com.adam.server.user.presentation.dto.UserResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;

  @GetMapping("/me")
  public ResponseEntity<UserResponse> me(
      @AuthenticationPrincipal JwtAuthentication authentication) {
    UserResponse userResponse = userService.findById(authentication.id());
    return ResponseEntity.ok(userResponse);
  }

  @GetMapping("")
  public ResponseEntity<List<UserResponse>> findAll() {
    List<UserResponse> userResponses = userService.findAll();
    return ResponseEntity.ok(userResponses);
  }

  @PutMapping("")
  public ResponseEntity<Void> update(
      @AuthenticationPrincipal JwtAuthentication jwtAuthentication,
      @RequestBody UpdateRequestDto dto) {
    userService.update(jwtAuthentication.id(), dto);
    return ResponseEntity.ok(null);
  }
}
