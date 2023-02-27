package com.adam.server.user.domain;

public enum Role {
  USER,
  ADMIN;

  private static String ROLE_PREFIX = "ROLE_";

  public String getRole() {
    return ROLE_PREFIX + this.name();
  }
}
