package com.adam.server.user.domain.repository;

import com.adam.server.user.domain.Email;
import com.adam.server.user.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmail(Email email);
}
