package com.adam.server.post.presentation.dto;

import com.adam.server.post.domain.Comment;

public class CommentResponse {

  private Long id;

  private String body;

  private String name;

  private CommentResponse(Long id, String body, String name) {
    this.id = id;
    this.body = body;
    this.name = name;
  }

  public static CommentResponse fromEntity(Comment comment) {
    return new CommentResponse(
        comment.getId(), comment.getBody(), comment.getUser().getName().getFullName());
  }

  public Long getId() {
    return id;
  }

  public String getBody() {
    return body;
  }

  public String getName() {
    return name;
  }
}
