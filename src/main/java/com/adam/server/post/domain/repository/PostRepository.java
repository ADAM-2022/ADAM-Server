package com.adam.server.post.domain.repository;

import com.adam.server.post.domain.Post;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

  List<Post> findByUserId(Long id);

  Optional<Post> findByIdAndUserId(Long id, Long userId);
}
