package com.adam.server.post.domain;

import com.adam.server.user.domain.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;

@Entity
public class Comment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Lob private String body;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "post_id")
  private Post post;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  protected Comment() {}

  public Comment(String body, User user, Post post) {
    this.body = body;
    this.user = user;
    this.post = post;
  }

  public Long getId() {
    return id;
  }

  public String getBody() {
    return body;
  }

  public Post getPost() {
    return post;
  }

  public User getUser() {
    return user;
  }

  public void update(String body) {
    if (body != null) {
      this.body = body;
    }
  }
}
