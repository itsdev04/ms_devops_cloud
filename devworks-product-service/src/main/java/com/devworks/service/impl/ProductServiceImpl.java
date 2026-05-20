package com.devworks.service.impl;

import com.devworks.dto.CategoryDto;
import com.devworks.dto.PagedResponse;
import com.devworks.dto.ProductDto;
import com.devworks.dto.ReviewDto;
import com.devworks.entity.Category;
import com.devworks.entity.Product;
import com.devworks.entity.Review;
import com.devworks.exception.InvalidRequestException;
import com.devworks.exception.ResourceNotFoundException;
import com.devworks.mapper.ProductMapper;
import com.devworks.repository.CategoryRepository;
import com.devworks.repository.ProductRepository;
import com.devworks.repository.ReviewRepository;
import com.devworks.service.ImageKitStorageService;
import com.devworks.service.ProductService;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
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
  private final ProductMapper productMapper;

  public ProductServiceImpl(
      ProductRepository productRepo,
      CategoryRepository categoryRepo,
      ReviewRepository reviewRepo,
      ImageKitStorageService imageKitStorageService,
      ProductMapper productMapper) {
    this.productRepository = productRepo;
    this.categoryRepository = categoryRepo;
    this.reviewRepository = reviewRepo;
    this.imageKitStorageService = imageKitStorageService;
    this.productMapper = productMapper;
  }

  @Override
  public PagedResponse<ProductDto> getAllProducts(int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    Page<Product> productPage = productRepository.findAll(pageable);
    return toPagedResponse(productPage.map(this::toDto));
  }

  @Override
  public ProductDto getProductById(UUID productId) {
    return toDto(findProduct(productId));
  }

  @Override
  public PagedResponse<ProductDto> getProductsByCategoryId(Long categoryId, int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    Page<Product> productPage = productRepository.findByCategories_Id(categoryId, pageable);

    return toPagedResponse(productPage.map(this::toDto));
  }

  @Override
  public ProductDto createProduct(ProductDto productDto) {
    Product product = new Product();
    applyBasicFields(product, productDto);
    List<Category> categories = resolveCategories(productDto.getCategories());
    product.setCategories(categories);
    Product savedProduct = productRepository.save(product);
    syncCategoryLinks(savedProduct, categories);
    return toDto(savedProduct);
  }

  @Override
  public ProductDto updateProduct(UUID productId, ProductDto productDto) {
    Product product = findProduct(productId);
    applyBasicFields(product, productDto);
    if (productDto.getCategories() != null) {
      List<Category> categories = resolveCategories(productDto.getCategories());
      product.setCategories(categories);
      Product savedProduct = productRepository.save(product);
      syncCategoryLinks(savedProduct, categories);
      return toDto(savedProduct);
    }
    return toDto(productRepository.save(product));
  }

  @Override
  public void deleteProduct(UUID productId) {
    Product product = findProduct(productId);
    productRepository.delete(product);
  }

  @Override
  public ProductDto addCategoryToProduct(UUID productId, Long categoryId) {
    Product product = findProduct(productId);
    Category category = findCategory(categoryId);
    if (!product.getCategories().contains(category)) {
      product.getCategories().add(category);
    }
    if (!category.getProducts().contains(product)) {
      category.getProducts().add(product);
    }
    categoryRepository.save(category);
    return toDto(productRepository.save(product));
  }

  @Override
  public ProductDto removeCategoryFromProduct(UUID productId, Long categoryId) {
    Product product = findProduct(productId);
    Category category = findCategory(categoryId);

    product.getCategories().remove(category);
    category.getProducts().remove(product);

    categoryRepository.save(category);
    return toDto(productRepository.save(product));
  }

  @Override
  public ReviewDto addReviewToProduct(UUID productId, ReviewDto reviewDto) {
    Product product = findProduct(productId);
    Review review = new Review();
    review.setTitle(reviewDto.getTitle());
    review.setComment(reviewDto.getComment());
    review.setRating(reviewDto.getRating());
    review.setProduct(product);
    return toReviewDto(reviewRepository.save(review));
  }

  @Override
  public ProductDto addProductImages(UUID productId, List<MultipartFile> files) {
    Product product = findProduct(productId);

    List<String> uploadedUrls = uploadImages(files);

    if (product.getProductImages() == null) {
      product.setProductImages(new ArrayList<>());
    }
    product.getProductImages().addAll(uploadedUrls);
    return toDto(productRepository.save(product));
  }

  @Override
  public List<String> getProductImages(UUID productId) {
    Product product = findProduct(productId);
    return product.getProductImages() == null
        ? new ArrayList<>()
        : new ArrayList<>(product.getProductImages());
  }

  private Product findProduct(UUID productId) {
    return productRepository
        .findById(productId)
        .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + productId));
  }

  private Category findCategory(Long categoryId) {
    return categoryRepository
        .findById(categoryId)
        .orElseThrow(() -> new ResourceNotFoundException("Category not found: " + categoryId));
  }

  private void applyBasicFields(Product product, ProductDto productDto) {
    // custom logic
    product.setTitle(productDto.getTitle());
    product.setShortDesc(productDto.getShortDesc());
    product.setLongDesc(productDto.getLongDesc());
    product.setPrice(productDto.getPrice());
    product.setDiscount(productDto.getDiscount());
    if (productDto.getLive() != null) {
      product.setLive(productDto.getLive());
    }
    if (productDto.getProductImages() != null) {
      product.setProductImages(new ArrayList<>(productDto.getProductImages()));
    }
  }

  private List<Category> resolveCategories(List<CategoryDto> categoryDtos) {
    if (categoryDtos == null) {
      return new ArrayList<>();
    }
    List<Category> categories = new ArrayList<>();
    for (CategoryDto categoryDto : categoryDtos) {
      if (categoryDto.getId() == null) {
        Category category = new Category();
        category.setTitle(categoryDto.getTitle());
        categories.add(categoryRepository.save(category));
      } else {
        categories.add(findCategory(categoryDto.getId()));
      }
    }
    return categories;
  }

  private void syncCategoryLinks(Product product, List<Category> categories) {
    for (Category category : categories) {
      if (!category.getProducts().contains(product)) {
        category.getProducts().add(product);
      }
      categoryRepository.save(category);
    }
  }

  private List<String> uploadImages(List<MultipartFile> files) {
    if (files == null || files.isEmpty()) {
      throw new InvalidRequestException("At least one product image is required");
    }
    List<String> uploadedUrls = new ArrayList<>();
    for (MultipartFile file : files) {
      uploadedUrls.add(imageKitStorageService.upload(file));
    }
    return uploadedUrls;
  }

  private ProductDto toDto(Product product) {
    ProductDto dto = new ProductDto();
    dto.setId(product.getId());
    dto.setTitle(product.getTitle());
    dto.setShortDesc(product.getShortDesc());
    dto.setLongDesc(product.getLongDesc());
    dto.setPrice(product.getPrice());
    dto.setDiscount(product.getDiscount());
    dto.setLive(product.getLive());
    dto.setProductImages(
        product.getProductImages() == null
            ? new ArrayList<>()
            : new ArrayList<>(product.getProductImages()));
    dto.setCategories(
        product.getCategories() == null
            ? new ArrayList<>()
            : product.getCategories().stream()
                .map(this::toCategoryDtoShallow)
                .collect(Collectors.toList()));
    dto.setReviews(
        product.getReviews() == null
            ? new ArrayList<>()
            : product.getReviews().stream()
                .map(this::toReviewDtoShallow)
                .collect(Collectors.toList()));
    return dto;
  }

  private CategoryDto toCategoryDtoShallow(Category category) {
    CategoryDto dto = new CategoryDto();
    dto.setId(category.getId());
    dto.setTitle(category.getTitle());
    dto.setProducts(new ArrayList<>());
    return dto;
  }

  private ReviewDto toReviewDtoShallow(Review review) {
    ReviewDto dto = new ReviewDto();
    dto.setId(review.getId());
    dto.setTitle(review.getTitle());
    dto.setComment(review.getComment());
    dto.setRating(review.getRating());
    dto.setProduct(null);
    return dto;
  }

  private ReviewDto toReviewDto(Review review) {
    ReviewDto dto = toReviewDtoShallow(review);
    if (review.getProduct() != null) {
      dto.setProduct(toProductDtoShallow(review.getProduct()));
    }
    return dto;
  }

  private ProductDto toProductDtoShallow(Product product) {
    ProductDto dto = new ProductDto();
    dto.setId(product.getId());
    dto.setTitle(product.getTitle());
    dto.setShortDesc(product.getShortDesc());
    dto.setLongDesc(product.getLongDesc());
    dto.setPrice(product.getPrice());
    dto.setDiscount(product.getDiscount());
    dto.setLive(product.getLive());
    dto.setProductImages(
        product.getProductImages() == null
            ? new ArrayList<>()
            : new ArrayList<>(product.getProductImages()));
    dto.setCategories(new ArrayList<>());
    dto.setReviews(new ArrayList<>());
    return dto;
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
