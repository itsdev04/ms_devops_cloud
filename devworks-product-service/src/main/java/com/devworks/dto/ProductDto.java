package com.devworks.dto;

import jakarta.validation.constraints.*;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
  private UUID id;

  @NotBlank(message = "title is required")
  private String title;

  @NotBlank(message = "shortDesc is required")
  @Size(max = 500, message = "shortDesc must be at most 500 characters")
  private String shortDesc;

  @NotBlank(message = "longDesc is required")
  private String longDesc;

  @NotNull(message = "price is required")
  @Positive(message = "price must be greater than 0")
  private Double price;

  @Min(value = 0, message = "discount must be greater than or equal to 0")
  @Max(value = 100, message = "discount must be less than or equal to 100")
  private Integer discount;

  private Boolean live;
  private List<String> productImages;
  private List<CategoryDto> categories;
  private List<ReviewDto> reviews;
}
