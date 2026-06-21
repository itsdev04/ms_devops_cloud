package com.devworks.repository;

import com.devworks.entity.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
  // find the user by email
  Optional<User> findByEmail(String email);

  // check the user exist by email or not
  boolean existsByEmail(String email);
}
