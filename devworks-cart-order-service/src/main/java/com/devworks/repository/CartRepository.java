package com.devworks.repository;

import com.devworks.entity.Cart;
import com.devworks.entity.CartStatus;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

  Optional<Cart> findByUserIdAndStatus(String userId, CartStatus status);
}
