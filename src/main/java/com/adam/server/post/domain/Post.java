package com.adam.server.post.domain;

import com.adam.server.user.domain.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.annotations.ColumnDefault;

@Entity
public class Post {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String title;

  @Lob private String body;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id")
  private Category category;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
  private List<Comment> comments = new ArrayList<>();

  @ColumnDefault(value = "0")
  private int viewCount;

  private LocalDateTime createdAt;

  protected Post() {}

  public Post(String title, String body, Category category, User user) {
    this.title = title;
    this.body = body;
    this.category = category;
    this.user = user;
    this.viewCount = 0;
    this.createdAt = LocalDateTime.now();
  }

  public void update(String title, String body, Category category) {
    if (title != null) {
      this.title = title;
    }

    if (body != null) {
      this.body = body;
    }

    if (category != null) {
      this.category = category;
    }
  }

  public Long getId() {
    return id;
  }

  public void addComment(Comment comment) {
    comments.add(comment);
  }

  public String getTitle() {
    return title;
  }

  public String getBody() {
    return body;
  }

  public Category getCategory() {
    return category;
  }

  public User getUser() {
    return user;
  }

  public List<Comment> getComments() {
    return comments;
  }

  public int getViewCount() {
    return viewCount;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }
}
