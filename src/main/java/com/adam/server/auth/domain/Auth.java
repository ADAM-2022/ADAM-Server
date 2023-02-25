package com.adam.server.auth.domain;

import com.adam.server.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import org.hibernate.annotations.ColumnDefault;

@Entity
public class Auth {

  @Id
  @OneToOne
  @JoinColumn(name = "user_id")
  private User user;

  @Column(nullable = false)
  @ColumnDefault(value = "false")
  private boolean emailVerified;

  @Column(nullable = false, length = 80)
  private String password;

  protected Auth() {}

  public Auth(String password) {
    this.password = password;
    this.emailVerified = false;
  }
}
