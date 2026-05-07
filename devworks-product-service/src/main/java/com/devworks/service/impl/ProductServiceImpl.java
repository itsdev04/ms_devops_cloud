package com.devworks.service.impl;

import com.devworks.dto.PagedResponse;
import com.devworks.dto.ProductDto;
import com.devworks.dto.ReviewDto;
import com.devworks.entity.Product;
import com.devworks.repository.CategoryRepository;
import com.devworks.repository.ProductRepository;
import com.devworks.repository.ReviewRepository;
import com.devworks.service.ImageKitStorageService;
import com.devworks.service.ProductService;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
  private final ProductRepository productRepository;
  private final CategoryRepository categoryRepository;
  private final ReviewRepository reviewRepository;
  private final ImageKitStorageService imageKitStorageService;

  public ProductServiceImpl(
      ProductRepository productRepo,
      CategoryRepository categoryRepo,
      ReviewRepository reviewRepo,
      ImageKitStorageService imageKitStorageService) {
    this.productRepository = productRepo;
    this.categoryRepository = categoryRepo;
    this.reviewRepository = reviewRepo;
    this.imageKitStorageService = imageKitStorageService;
  }

  @Override
  public PagedResponse<ProductDto> getAllProducts(int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    Page<Product> products = productRepository.findAll(pageable);

    return null;
  } // toPagedResponse(products.map(this::toDto));  }

  @Override
  public ProductDto getProductById(UUID productId) {
    return null;
  }

  @Override
  public PagedResponse<ProductDto> getProductsByCategoryId(Long categoryId, int page, int size) {
    return null;
  }

  @Override
  public ProductDto createProduct(ProductDto productDto) {
    return null;
  }

  @Override
  public ProductDto updateProduct(UUID productId, ProductDto productDto) {
    return null;
  }

  @Override
  public void deleteProduct(UUID productId) {}

  @Override
  public ProductDto addCategoryToProduct(UUID productId, Long categoryId) {
    return null;
  }

  @Override
  public ProductDto removeCategoryFromProduct(UUID productId, Long categoryId) {
    return null;
  }

  @Override
  public ReviewDto addReviewToProduct(UUID productId, ReviewDto reviewDto) {
    return null;
  }

  @Override
  public ProductDto addProductImages(UUID productId, List<MultipartFile> files) {
    return null;
  }

  @Override
  public List<String> getProductImages(UUID productId) {
    return null;
  }

  private PagedResponse<ProductDto> toPagedResponse(Page<ProductDto> page) {
    return new PagedResponse<>(
        page.getContent(),
        page.getNumber(),
        page.getSize(),
        page.getTotalElements(),
        page.getTotalPages(),
        page.getNumberOfElements(),
        page.isFirst(),
        page.isLast());
  }
}
