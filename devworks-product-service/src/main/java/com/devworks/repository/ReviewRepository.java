package com.devworks.repository;

import com.devworks.entity.Product;
import com.devworks.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;
public interface ReviewRepository extends JpaRepository<Review, Long>{
    List<Review> findByProduct(Product category);

    List<Review> findByProduct_Id(UUID productId);
}
