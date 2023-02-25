package com.adam.server.auth.presentation.dto;

import com.adam.server.user.domain.Email;
import com.adam.server.user.domain.Name;
import com.adam.server.user.domain.SessionTime;

public class JoinRequestDto {

  private Email email;
  private String password;
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
