package com.devworks.repository;

import com.devworks.entity.Order;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

  List<Order> findByUserIdOrderByCreatedAtDesc(String userId);

  Optional<Order> findByOrderNumber(String orderNumber);
}
