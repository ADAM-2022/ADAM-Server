package com.adam.server.auth.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.hibernate.annotations.ColumnDefault;

@Entity
public class Auth {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  @ColumnDefault(value = "false")
  private boolean emailVerified;

  @Column(nullable = false, length = 104)
  private String password;

  protected Auth() {}

  public Auth(String password) {
    this.password = password;
    this.emailVerified = false;
  }

  public boolean isEmailVerified() {
    return emailVerified;
  }

  public String getPassword() {
    return password;
  }
}
