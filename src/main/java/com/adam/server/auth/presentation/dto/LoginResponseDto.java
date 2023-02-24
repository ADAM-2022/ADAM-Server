package com.adam.server.auth.presentation.dto;

import static com.google.common.base.Preconditions.checkArgument;

public record LoginResponseDto(String accessToken, String refreshToken) {

  public LoginResponseDto {
    checkArgument(accessToken != null, "accessToken must be provided.");
    checkArgument(refreshToken != null, "refreshToken must be provided.");
  }
}
