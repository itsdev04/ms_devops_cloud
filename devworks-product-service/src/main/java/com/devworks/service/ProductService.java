package com.devworks.service;

import com.devworks.dto.PagedResponse;
import com.devworks.dto.ProductDto;
import com.devworks.dto.ReviewDto;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    PagedResponse<ProductDto> getAllProducts(int page, int size);

    ProductDto getProductById(UUID productId);

    PagedResponse<ProductDto> getProductsByCategoryId(Long categoryId, int page, int size);

    ProductDto createProduct(ProductDto productDto);

    ProductDto updateProduct(UUID productId, ProductDto productDto);

    void deleteProduct(UUID productId);

    ProductDto addCategoryToProduct(UUID productId, Long categoryId);

    ProductDto removeCategoryFromProduct(UUID productId, Long categoryId);

    ReviewDto addReviewToProduct(UUID productId, ReviewDto reviewDto);

    ProductDto addProductImages(UUID productId, List<MultipartFile> files);

    List<String> getProductImages(UUID productId);
}
