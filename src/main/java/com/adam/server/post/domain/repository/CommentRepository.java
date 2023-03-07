package com.adam.server.post.domain.repository;

import com.adam.server.post.domain.Comment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
  List<Comment> findByPostId(Long postId);

  List<Comment> findByPostIdAndUserId(Long postId, Long userId);

  Optional<Comment> findByIdAndPostIdAndUserId(Long id, Long postId, Long userId);
}
