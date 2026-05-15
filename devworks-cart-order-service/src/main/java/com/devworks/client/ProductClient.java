package com.devworks.client;

import com.devworks.dto.ProductSnapshot;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// @FeignClient(name = "productClient", url = "${services.product.base-url}")
@FeignClient(name = "devworks-product-service")
public interface ProductClient {

  @GetMapping("/api/products/{productId}")
  ProductSnapshot getProductById(@PathVariable("productId") UUID productId);
}
