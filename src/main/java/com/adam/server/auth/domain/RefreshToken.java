package com.adam.server.auth.domain;

import com.adam.server.user.domain.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class RefreshToken {

  @Id
  @OneToOne
  @JoinColumn(name = "user_id")
  private User user;

  private String refreshToken;

  private LocalDateTime expiresAt;

  protected RefreshToken() {}

  public RefreshToken(User user) {
    this.user = user;
    this.refreshToken = UUID.randomUUID().toString();
    this.expiresAt = LocalDateTime.now().plusWeeks(2);
  }
}
