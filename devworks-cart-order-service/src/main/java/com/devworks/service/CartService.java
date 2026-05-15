package com.devworks.service;

import com.devworks.dto.AddCartItemRequest;
import com.devworks.dto.CartResponse;
import com.devworks.dto.UpdateCartItemRequest;

public interface CartService {

  CartResponse getCart(String userId);

  CartResponse addItem(String userId, AddCartItemRequest request);

  CartResponse updateItem(String userId, String productId, UpdateCartItemRequest request);

  CartResponse removeItem(String userId, String productId);

  void clearCart(String userId);
}
