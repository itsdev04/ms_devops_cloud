package com.devworks.dto;

import com.devworks.entity.CartStatus;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record CartResponse(
    Long id,
    String userId,
    CartStatus status,
    BigDecimal totalAmount,
    List<CartItemResponse> items,
    Instant createdAt,
    Instant updatedAt,
    Instant checkedOutAt) {}
