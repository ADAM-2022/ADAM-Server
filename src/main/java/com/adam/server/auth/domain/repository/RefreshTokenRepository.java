package com.adam.server.auth.domain.repository;

import com.adam.server.auth.domain.RefreshToken;
import com.adam.server.user.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

  Optional<RefreshToken> findByUser(User user);

  Optional<RefreshToken> findByRefreshToken(String refreshToken);
}
