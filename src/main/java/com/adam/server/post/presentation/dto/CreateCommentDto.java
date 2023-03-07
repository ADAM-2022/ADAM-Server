package com.adam.server.post.presentation.dto;

import com.adam.server.post.domain.Comment;
import com.adam.server.post.domain.Post;import com.adam.server.user.domain.User;

public class CreateCommentDto {

  private String body;

  public Comment toEntity(User user, Post post) {
    return new Comment(body, user, post);
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }
}
