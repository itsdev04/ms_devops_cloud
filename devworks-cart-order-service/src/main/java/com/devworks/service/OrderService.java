package com.devworks.service;

import com.devworks.dto.CheckoutRequest;
import com.devworks.dto.OrderResponse;
import java.util.List;
import java.util.UUID;

public interface OrderService {

  OrderResponse checkout(String userId, CheckoutRequest request);

  OrderResponse getOrderById(Long orderId);

  OrderResponse getOrderByNumber(String orderNumber);

  List<OrderResponse> getOrdersByUserId(String userId);

  OrderResponse cancelOrder(Long orderId);

  void releaseReservedStock(UUID productId, Integer quantity);
}
