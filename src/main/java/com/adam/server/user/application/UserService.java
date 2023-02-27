package com.adam.server.user.application;

import com.adam.server.common.error.NotFoundException;
import com.adam.server.common.model.Id;
import com.adam.server.user.domain.User;
import com.adam.server.user.domain.repository.UserRepository;
import com.adam.server.user.presentation.dto.UpdateRequestDto;
import com.adam.server.user.presentation.dto.UserResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

  private final UserRepository userRepository;

  @Transactional(readOnly = true)
  public UserResponse findById(Id<User, Long> id) {
    User user = findUser(id);
    return UserResponse.fromEntity(user);
  }

  @Transactional(readOnly = true)
  public List<UserResponse> findAll() {
    List<User> users = userRepository.findAll();
    return users.stream().map(UserResponse::fromEntity).toList();
  }

  @Transactional
  public void update(Id<User, Long> id, UpdateRequestDto dto) {
    User user = findUser(id);
    user.update(dto.getName(), dto.getSessionTime());
  }

  private User findUser(Id<User, Long> id) {
    return userRepository
        .findById(id.value())
        .orElseThrow(() -> new NotFoundException(User.class, id));
  }
}
