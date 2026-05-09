package com.devworks.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageKitStorageService {
  String upload(MultipartFile file);
}
