package com.adam.server.user.presentation.dto;

import com.adam.server.user.domain.Role;
import com.adam.server.user.domain.SessionTime;
import com.adam.server.user.domain.User;

public class UserResponse {

  private Long id;

  private String email;

  private String name;

  private SessionTime sessionTime;

  private Role role;

  private UserResponse(Long id, String email, String name, SessionTime sessionTime, Role role) {
    this.id = id;
    this.email = email;
    this.name = name;
    this.sessionTime = sessionTime;
    this.role = role;
  }

  public static UserResponse fromEntity(User user) {
    return new UserResponse(
        user.getId(),
        user.getEmail().getAddress(),
        user.getName().getFullName(),
        user.getSessionTime(),
        user.getRole());
  }

  public Long getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  public String getName() {
    return name;
  }

  public SessionTime getSessionTime() {
    return sessionTime;
  }

  public Role getRole() {
    return role;
  }
}
