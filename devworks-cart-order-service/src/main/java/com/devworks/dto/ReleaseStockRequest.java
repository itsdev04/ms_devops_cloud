package com.devworks.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ReleaseStockRequest(@NotNull @Positive Integer quantity) {}
