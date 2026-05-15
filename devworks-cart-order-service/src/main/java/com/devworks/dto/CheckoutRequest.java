package com.devworks.dto;

import jakarta.validation.constraints.NotBlank;

public record CheckoutRequest(@NotBlank String shippingAddress, String paymentMethod) {}
