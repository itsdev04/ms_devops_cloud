package com.devworks.repository;

import com.devworks.entity.Product;
import com.devworks.entity.Review;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
  List<Review> findByProduct(Product category);

  List<Review> findByProduct_Id(UUID productId);
}
