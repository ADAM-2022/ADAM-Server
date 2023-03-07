package com.adam.server.post.application;

import com.adam.server.common.error.NotFoundException;
import com.adam.server.common.model.Id;
import com.adam.server.post.domain.Comment;
import com.adam.server.post.domain.Post;
import com.adam.server.post.domain.repository.CommentRepository;
import com.adam.server.post.domain.repository.PostRepository;
import com.adam.server.post.presentation.dto.CommentResponse;
import com.adam.server.post.presentation.dto.CommentUpdateDto;
import com.adam.server.post.presentation.dto.CreateCommentDto;
import com.adam.server.user.domain.User;
import com.adam.server.user.domain.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {

  private final PostRepository postRepository;
  private final CommentRepository commentRepository;
  private final UserRepository userRepository;

  @Transactional
  public void create(Id<Post, Long> postId, Id<User, Long> userId, CreateCommentDto dto) {
    Post post =
        postRepository
            .findById(postId.value())
            .orElseThrow(() -> new NotFoundException(Post.class, postId));

    User user =
        userRepository
            .findById(userId.value())
            .orElseThrow(() -> new NotFoundException(User.class, userId));

    Comment comment = dto.toEntity(user, post);
    post.addComment(comment);
  }

  @Transactional(readOnly = true)
  public List<CommentResponse> findAll(Id<Post, Long> postId) {
    List<Comment> comments = commentRepository.findByPostId(postId.value());
    return toCommentResponseList(comments);
  }

  @Transactional(readOnly = true)
  public List<CommentResponse> me(Id<Post, Long> postId, Id<User, Long> userId) {
    List<Comment> comments =
        commentRepository.findByPostIdAndUserId(postId.value(), userId.value());
    return toCommentResponseList(comments);
  }

  @Transactional
  public void update(
      Id<User, Long> userId,
      Id<Comment, Long> commentId,
      Id<Post, Long> postId,
      CommentUpdateDto dto) {
    Comment comment = findComment(userId, commentId, postId);
    comment.update(dto.getBody());
  }

  @Transactional
  public void delete(Id<User, Long> userId, Id<Comment, Long> commentId, Id<Post, Long> postId) {
    Comment comment = findComment(userId, commentId, postId);
    commentRepository.delete(comment);
  }

  private static List<CommentResponse> toCommentResponseList(List<Comment> comments) {
    return comments.stream().map(CommentResponse::fromEntity).toList();
  }

  private Comment findComment(
      Id<User, Long> userId, Id<Comment, Long> commentId, Id<Post, Long> postId) {
    return commentRepository
        .findByIdAndPostIdAndUserId(commentId.value(), postId.value(), userId.value())
        .orElseThrow(() -> new NotFoundException(Comment.class, commentId, postId, userId));
  }
}
