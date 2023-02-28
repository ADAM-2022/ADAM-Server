package com.adam.server.post.presentation.dto;

import com.adam.server.post.domain.Comment;
import com.adam.server.post.domain.Post;
import java.time.LocalDateTime;
import java.util.List;

public class PostResponse {

  private Long id;

  private String title;

  private String body;

  private String category;

  private String name;

  private List<Comment> comments;

  private int viewCount;

  private LocalDateTime createdAt;

  public PostResponse(
      Long id,
      String title,
      String body,
      String name,
      String category,
      List<Comment> comments,
      int viewCount,
      LocalDateTime createdAt) {
    this.id = id;
    this.title = title;
    this.body = body;
    this.name = name;
    this.category = category;
    this.comments = comments;
    this.viewCount = viewCount;
    this.createdAt = createdAt;
  }

  public static PostResponse fromEntity(Post post) {
    return new PostResponse(
        post.getId(),
        post.getTitle(),
        post.getBody(),
        post.getUser().getName().getName(),
        post.getCategory().getName(),
        post.getComments(),
        post.getViewCount(),
        post.getCreatedAt());
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Comment> getComments() {
    return comments;
  }

  public void setComments(List<Comment> comments) {
    this.comments = comments;
  }

  public int getViewCount() {
    return viewCount;
  }

  public void setViewCount(int viewCount) {
    this.viewCount = viewCount;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }
}
