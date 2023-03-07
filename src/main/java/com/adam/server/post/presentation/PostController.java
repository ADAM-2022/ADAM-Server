package com.adam.server.post.presentation;

import com.adam.server.common.model.Id;
import com.adam.server.post.application.PostService;
import com.adam.server.post.domain.Post;
import com.adam.server.post.presentation.dto.CreatePostDto;
import com.adam.server.post.presentation.dto.PostResponse;
import com.adam.server.post.presentation.dto.UpdatePostDto;
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
@RequestMapping("/api/posts")
public class PostController {

  private final PostService postService;

  @Operation(security = {@SecurityRequirement(name = "authorization")})
  @PostMapping("")
  public ResponseEntity<Void> create(
      @AuthenticationPrincipal JwtAuthentication authentication, @RequestBody CreatePostDto dto) {
    postService.create(authentication.id(), dto);
    return ResponseEntity.ok(null);
  }

  @Operation(security = {@SecurityRequirement(name = "authorization")})
  @GetMapping("")
  public ResponseEntity<List<PostResponse>> findAll() {
    List<PostResponse> posts = postService.findAll();
    return ResponseEntity.ok(posts);
  }

  @Operation(security = {@SecurityRequirement(name = "authorization")})
  @GetMapping("/{id}")
  public ResponseEntity<PostResponse> findById(@PathVariable Long id) {
    PostResponse post = postService.findById(Id.of(Post.class, id));
    return ResponseEntity.ok(post);
  }

  @Operation(security = {@SecurityRequirement(name = "authorization")})
  @GetMapping("/me")
  public ResponseEntity<List<PostResponse>> me(
      @AuthenticationPrincipal JwtAuthentication authentication) {
    List<PostResponse> posts = postService.me(authentication.id());
    return ResponseEntity.ok(posts);
  }

  @Operation(security = {@SecurityRequirement(name = "authorization")})
  @PutMapping("/{id}")
  public ResponseEntity<Void> update(
      @AuthenticationPrincipal JwtAuthentication authentication,
      @PathVariable Long id,
      @RequestBody UpdatePostDto dto) {
    postService.update(authentication.id(), Id.of(Post.class, id), dto);
    return ResponseEntity.ok(null);
  }

  @Operation(security = {@SecurityRequirement(name = "authorization")})
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(
      @AuthenticationPrincipal JwtAuthentication authentication, @PathVariable Long id) {
    postService.delete(authentication.id(), Id.of(Post.class, id));
    return ResponseEntity.ok(null);
  }
}
