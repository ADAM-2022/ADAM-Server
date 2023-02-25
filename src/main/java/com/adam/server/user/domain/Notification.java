package com.adam.server.user.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.ColumnDefault;

@Entity
public class Notification {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

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
        .append("allowPost", allowPost)
        .append("allowComment", allowComment)
        .toString();
  }
}
