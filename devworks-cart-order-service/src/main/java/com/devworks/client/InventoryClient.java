package com.devworks.client;

import com.devworks.dto.InventorySnapshot;
import com.devworks.dto.ReleaseStockRequest;
import com.devworks.dto.ReserveStockRequest;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

// @FeignClient(name = "inventoryClient", url = "${services.inventory.base-url}")
@FeignClient(name = "DEVWORKS-INVENTORY-SERVICE")
public interface InventoryClient {

  @GetMapping("/api/inventories/product/{productId}")
  InventorySnapshot getInventoryByProductId(@PathVariable("productId") UUID productId);

  @PostMapping("/api/inventories/product/{productId}/reserve")
  InventorySnapshot reserveByProductId(
      @PathVariable("productId") UUID productId, @RequestBody ReserveStockRequest request);

  @PostMapping("/api/inventories/product/{productId}/release")
  InventorySnapshot releaseByProductId(
      @PathVariable("productId") UUID productId, @RequestBody ReleaseStockRequest request);
}
