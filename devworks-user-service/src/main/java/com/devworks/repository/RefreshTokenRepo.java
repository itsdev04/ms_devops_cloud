package com.devworks.repository;

import com.devworks.entity.RefreshToken;
import com.devworks.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepo extends JpaRepository<RefreshToken, String> {

  Optional<RefreshToken> findByRefreshToken(String refreshToken);

  Optional<RefreshToken> findByRefreshTokenAndUserId(String refreshToken, String userId);

  Optional<RefreshToken> findByUser(User user);
}
