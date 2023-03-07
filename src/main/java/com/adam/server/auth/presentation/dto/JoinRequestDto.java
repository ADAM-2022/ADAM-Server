package com.adam.server.auth.presentation.dto;

import com.adam.server.user.domain.Email;
import com.adam.server.user.domain.Name;
import com.adam.server.user.domain.SessionTime;
import io.swagger.v3.oas.annotations.media.Schema;

public class JoinRequestDto {

  @Schema(type = "string")
  private Email email;

  private String password;

  @Schema(type = "string")
  private Name name;

  private SessionTime sessionTime;

  public JoinRequestDto(Email email, String password, Name name, SessionTime sessionTime) {
    this.email = email;
    this.password = password;
    this.name = name;
    this.sessionTime = sessionTime;
  }

  public Email getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  public Name getName() {
    return name;
  }

  public SessionTime getSessionTime() {
    return sessionTime;
  }
}
