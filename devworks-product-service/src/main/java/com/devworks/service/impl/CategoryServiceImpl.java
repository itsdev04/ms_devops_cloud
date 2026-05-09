package com.devworks.service.impl;

import com.devworks.dto.CategoryDto;
import com.devworks.dto.ProductDto;
import com.devworks.entity.Category;
import com.devworks.exception.ResourceNotFoundException;
import com.devworks.repository.CategoryRepository;
import com.devworks.repository.ProductRepository;
import com.devworks.service.CategoryService;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
  private final CategoryRepository categoryRepo;
  private final ProductRepository productRepo;

  public CategoryServiceImpl(CategoryRepository categoryRepo, ProductRepository productRepo) {
    this.categoryRepo = categoryRepo;
    this.productRepo = productRepo;
  }

  @Override
  public List<CategoryDto> getAllCategories() {
    return categoryRepo.findAll().stream().map(this::toDto).collect(Collectors.toList());
  }

  @Override
  public CategoryDto getCategoryById(Long categoryId) {
    return toDto(findCategory(categoryId));
  }

  @Override
  public List<CategoryDto> getCategoriesByProductId(UUID productId) {
    return categoryRepo.findByProductId(productId).stream()
        .map(this::toDto)
        .collect(Collectors.toList());
  }

  @Override
  public CategoryDto createCategory(CategoryDto categoryDto) {
    Category category = new Category();
    category.setTitle(categoryDto.getTitle());
    return toDto(categoryRepo.save(category));
  }

  @Override
  public CategoryDto updateCategory(Long categoryId, CategoryDto categoryDto) {
    Category category = findCategory(categoryId);
    category.setTitle(categoryDto.getTitle());
    return toDto(categoryRepo.save(category));
  }

  @Override
  public void deleteCategory(Long categoryId) {
    Category category = findCategory(categoryId);
    category.getProducts().forEach(product -> product.getCategories().remove(category));
    category.getProducts().clear();
    categoryRepo.save(category);
    categoryRepo.delete(category);
  }

  private Category findCategory(Long categoryId) {
    return categoryRepo
        .findById(categoryId)
        .orElseThrow(() -> new ResourceNotFoundException("Category not found: " + categoryId));
  }

  private CategoryDto toDto(Category category) {
    CategoryDto dto = new CategoryDto();
    dto.setId(category.getId());
    dto.setTitle(category.getTitle());
    dto.setProducts(
        category.getProducts() == null
            ? new ArrayList<>()
            : category.getProducts().stream()
                .map(
                    product -> {
                      var productDto = new ProductDto();
                      productDto.setId(product.getId());
                      productDto.setTitle(product.getTitle());
                      productDto.setShortDesc(product.getShortDesc());
                      productDto.setLongDesc(product.getLongDesc());
                      productDto.setPrice(product.getPrice());
                      productDto.setDiscount(product.getDiscount());
                      productDto.setLive(product.getLive());
                      productDto.setProductImages(
                          product.getProductImages() == null
                              ? new ArrayList<>()
                              : new ArrayList<>(product.getProductImages()));
                      productDto.setCategories(new ArrayList<>());
                      productDto.setReviews(new ArrayList<>());
                      return productDto;
                    })
                .collect(Collectors.toList()));
    return dto;
  }
}
