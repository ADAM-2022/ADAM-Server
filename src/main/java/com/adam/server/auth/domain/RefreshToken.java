package com.adam.server.auth.domain;

import com.adam.server.user.domain.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class RefreshToken {

  @Id private Long id;

  @MapsId
  @OneToOne
  @JoinColumn(name = "user_id")
  private User user;

  private String refreshToken;

  private LocalDateTime expiresAt;

  protected RefreshToken() {}

  public RefreshToken(User user) {
    this.user = user;
    initialize();
  }

  public User getUser() {
    return user;
  }

  public String getRefreshToken() {
    return refreshToken;
  }

  public LocalDateTime getExpiresAt() {
    return expiresAt;
  }

  public void initialize() {
    this.refreshToken = UUID.randomUUID().toString();
    this.expiresAt = LocalDateTime.now().plusWeeks(2);
  }

  public boolean isExpired() {
    LocalDateTime now = LocalDateTime.now();
    return now.isAfter(expiresAt);
  }
}
