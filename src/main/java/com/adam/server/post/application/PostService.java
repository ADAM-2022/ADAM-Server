package com.adam.server.post.application;

import com.adam.server.common.error.NotFoundException;
import com.adam.server.common.model.Id;
import com.adam.server.post.domain.Category;
import com.adam.server.post.domain.Post;
import com.adam.server.post.domain.repository.CategoryRepository;
import com.adam.server.post.domain.repository.PostRepository;
import com.adam.server.post.presentation.dto.CreateRequestDto;
import com.adam.server.post.presentation.dto.PostResponse;
import com.adam.server.post.presentation.dto.UpdateRequestDto;
import com.adam.server.user.domain.User;
import com.adam.server.user.domain.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostService {
  private final UserRepository userRepository;

  private final PostRepository postRepository;

  private final CategoryRepository categoryRepository;

  @Transactional
  public void create(Id<User, Long> id, CreateRequestDto dto) {
    User user =
        userRepository
            .findById(id.value())
            .orElseThrow(() -> new NotFoundException(User.class, id));

    Category category =
        categoryRepository
            .findByName(dto.getCategory())
            .orElseThrow(() -> new NotFoundException(Category.class, dto.getCategory()));

    Post post = new Post(dto.getTitle(), dto.getBody(), category, user);
    postRepository.save(post);
  }

  @Transactional(readOnly = true)
  public List<PostResponse> findAll() {
    return postRepository.findAll().stream().map(PostResponse::fromEntity).toList();
  }

  @Transactional(readOnly = true)
  public List<PostResponse> me(Id<User, Long> id) {
    return postRepository.findByUserId(id.value()).stream().map(PostResponse::fromEntity).toList();
  }

  @Transactional
  public void update(Id<User, Long> userId, Id<Post, Long> postId, UpdateRequestDto dto) {
    Post post = findPost(userId, postId);

    Category category = null;
    if (dto.getCategory() != null) {
      category =
          categoryRepository
              .findByName(dto.getCategory())
              .orElseThrow(() -> new NotFoundException(Category.class, dto.getCategory()));
    }

    post.update(dto.getTitle(), dto.getBody(), category);
  }

  @Transactional
  public void delete(Id<User, Long> userId, Id<Post, Long> postId) {
    Post post = findPost(userId, postId);
    postRepository.delete(post);
  }

  @Transactional(readOnly = true)
  public PostResponse findById(Id<Post, Long> id) {
    Post post =
        postRepository
            .findById(id.value())
            .orElseThrow(() -> new NotFoundException(Post.class, id));
    return PostResponse.fromEntity(post);
  }

  private Post findPost(Id<User, Long> userId, Id<Post, Long> postId) {
    return postRepository
        .findByIdAndUserId(postId.value(), userId.value())
        .orElseThrow(() -> new NotFoundException(Post.class, postId));
  }
}
