package com.adam.server.auth.presentation.dto;

import com.adam.server.user.domain.Email;
import io.swagger.v3.oas.annotations.media.Schema;

public class LoginRequestDto {

  @Schema(type = "string")
  private Email email;

  private String password;

  public LoginRequestDto(Email email, String password) {
    this.email = email;
    this.password = password;
  }

  public void setEmail(Email email) {
    this.email = email;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Email getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }
}
