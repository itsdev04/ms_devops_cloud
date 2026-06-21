package com.devworks.service;

import com.devworks.dto.LoginRequest;
import com.devworks.dto.LoginResponse;
import com.devworks.dto.TokenRefreshRequest;
import com.devworks.dto.TokenRefreshResponse;
import com.devworks.dto.UserDto;
import com.devworks.entity.Role;
import java.util.List;
import java.util.UUID;

public interface UserService {
  UserDto createUser(UserDto userDto);

  UserDto getUserById(UUID id);

  UserDto getUserByEmail(String email);

  List<UserDto> getAllUsers();

  UserDto updateUser(UUID id, UserDto userDto);

  void deleteUser(UUID id);

  void changeUserRole(UUID id, Role role);

  LoginResponse login(LoginRequest loginRequest);

  TokenRefreshResponse refreshToken(TokenRefreshRequest refreshRequest);
}
