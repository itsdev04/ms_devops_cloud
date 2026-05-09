package com.devworks.controller;

import com.devworks.dto.CategoryDto;
import com.devworks.service.CategoryService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
@Validated
public class CategoryController {
  private final CategoryService categoryService;

  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @GetMapping
  public ResponseEntity<List<CategoryDto>> getAllCategories() {
    return ResponseEntity.ok(categoryService.getAllCategories());
  }

  @GetMapping("/{categoryId}")
  public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long categoryId) {
    return ResponseEntity.ok(categoryService.getCategoryById(categoryId));
  }

  @GetMapping("/product/{productId}")
  public ResponseEntity<List<CategoryDto>> getCategoriesByProductId(@PathVariable UUID productId) {
    return ResponseEntity.ok(categoryService.getCategoriesByProductId(productId));
  }

  @PostMapping
  public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(categoryService.createCategory(categoryDto));
  }

  @PutMapping("/{categoryId}")
  public ResponseEntity<CategoryDto> updateCategory(
      @PathVariable Long categoryId, @Valid @RequestBody CategoryDto categoryDto) {
    return ResponseEntity.ok(categoryService.updateCategory(categoryId, categoryDto));
  }

  @DeleteMapping("/{categoryId}")
  public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId) {
    categoryService.deleteCategory(categoryId);
    return ResponseEntity.noContent().build();
  }
}
