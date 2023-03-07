package com.adam.server.post.presentation.dto;

public class CreatePostDto {
  private String title;

  private String body;

  private String category;

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
}
