package com.adam.server.user.domain;

import static com.google.common.base.Preconditions.checkArgument;

import com.adam.server.auth.domain.Auth;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Entity
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Embedded private Email email;

  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  @JoinColumn(name = "auth_id")
  private Auth auth;

  @Embedded private Name name;

  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  @JoinColumn(name = "notification_id")
  private Notification notification;

  @Enumerated(EnumType.STRING)
  @Column(length = 10, nullable = false)
  private SessionTime sessionTime;

  @Enumerated(EnumType.STRING)
  @Column(length = 10, nullable = false)
  private Role role;

  protected User() {}

  public User(Email email, String password, Name name, SessionTime sessionTime) {
    checkArgument(email != null, "email must be provided.");
    checkArgument(password != null, "password must be provided.");
    checkArgument(name != null, "name must be provided.");
    checkArgument(sessionTime != null, "session time must be provided");

    this.email = email;
    this.auth = new Auth(password);
    this.name = name;
    this.notification = new Notification();
    this.sessionTime = sessionTime;
    this.role = Role.USER;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .append("email", email)
        .append("[ password ]")
        .append("name", name)
        .append("notification", notification)
        .append("sessionTime", sessionTime)
        .append("role", role)
        .toString();
  }

  public void validatePassword(PasswordEncoder passwordEncoder, String password) {
    if (!passwordEncoder.matches(password, auth.getPassword())) {
      throw new IllegalArgumentException("Bad password");
    }
  }

  public void update(Name name, SessionTime sessionTime) {
    if (name != null) {
      this.name = name;
    }

    if (sessionTime != null) {
      this.sessionTime = sessionTime;
    }
  }
}
