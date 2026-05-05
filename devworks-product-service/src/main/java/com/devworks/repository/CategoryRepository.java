package com.devworks.repository;

import com.devworks.entity.Category;
import com.devworks.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category c JOIN c.products p WHERE p.id = :productId")
    List<Category> findByProductId(@Param("productId") UUID productId);
}
