package com.slip.user.service;


public interface GoogleCloudStorageService {
    String uploadImage(byte[] imageData, String filePath, String contentType);
}
