package com.adam.server.post.presentation;

import com.adam.server.common.model.Id;
import com.adam.server.post.application.CommentService;
import com.adam.server.post.domain.Comment;
import com.adam.server.post.domain.Post;
import com.adam.server.post.presentation.dto.CommentResponse;
import com.adam.server.post.presentation.dto.CommentUpdateDto;
import com.adam.server.post.presentation.dto.CreateCommentDto;
import com.adam.server.security.jwt.JwtAuthentication;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/posts/{postId}/comments")
public class CommentController {

  private final CommentService commentService;

  @Operation(security = {@SecurityRequirement(name = "authorization")})
  @PostMapping("")
  public void create(
      @AuthenticationPrincipal JwtAuthentication authentication,
      @PathVariable Long postId,
      @RequestBody CreateCommentDto dto) {
    this.commentService.create(Id.of(Post.class, postId), authentication.id(), dto);
  }

  @Operation(security = {@SecurityRequirement(name = "authorization")})
  @GetMapping("")
  public ResponseEntity<List<CommentResponse>> findAll(@PathVariable Long postId) {
    List<CommentResponse> commentResponses = this.commentService.findAll(Id.of(Post.class, postId));
    return ResponseEntity.ok(commentResponses);
  }

  @Operation(security = {@SecurityRequirement(name = "authorization")})
  @GetMapping("/me")
  public ResponseEntity<List<CommentResponse>> me(
      @PathVariable Long postId, @AuthenticationPrincipal JwtAuthentication authentication) {
    List<CommentResponse> commentResponses =
        this.commentService.me(Id.of(Post.class, postId), authentication.id());
    return ResponseEntity.ok(commentResponses);
  }

  @Operation(security = {@SecurityRequirement(name = "authorization")})
  @PutMapping("/{commentId}")
  public ResponseEntity<Void> update(
      @AuthenticationPrincipal JwtAuthentication authentication,
      @PathVariable Long commentId,
      @PathVariable Long postId,
      @RequestBody CommentUpdateDto dto) {
    this.commentService.update(
        authentication.id(), Id.of(Comment.class, commentId), Id.of(Post.class, postId), dto);
    return ResponseEntity.ok(null);
  }

  @Operation(security = {@SecurityRequirement(name = "authorization")})
  @DeleteMapping("/{commentId}")
  public ResponseEntity<Void> delete(
      @AuthenticationPrincipal JwtAuthentication authentication,
      @PathVariable Long commentId,
      @PathVariable Long postId) {
    this.commentService.delete(
        authentication.id(), Id.of(Comment.class, commentId), Id.of(Post.class, postId));
    return ResponseEntity.ok(null);
  }
}
