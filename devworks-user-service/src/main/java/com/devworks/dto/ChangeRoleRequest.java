package com.devworks.dto;

import com.devworks.entity.Role;
import java.util.UUID;

public record ChangeRoleRequest(UUID userId, Role role) {}
