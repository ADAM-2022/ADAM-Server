package com.adam.server.user.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.ColumnDefault;

@Entity
public class Notification {

  @Id
  @OneToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ColumnDefault(value = "false")
  private boolean allowPost;

  @ColumnDefault(value = "false")
  private boolean allowComment;

  protected Notification() {
    this.allowPost = false;
    this.allowComment = false;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("user", user)
        .append("allowPost", allowPost)
        .append("allowComment", allowComment)
        .toString();
  }
}
