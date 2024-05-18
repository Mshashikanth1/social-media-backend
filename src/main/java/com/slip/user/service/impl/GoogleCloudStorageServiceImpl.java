package com.slip.user.service.impl;

import com.google.cloud.storage.*;
import com.slip.user.service.GoogleCloudStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class GoogleCloudStorageServiceImpl implements GoogleCloudStorageService {


    @Value("${user.images.gcp.bucket}")
    public String BUCKET_NAME;

    private final Storage storage = StorageOptions.getDefaultInstance().getService();
    @Override
    public String uploadImage(byte[] imageData, String filePath, String contentType) {
        final BlobInfo blobInfo = BlobInfo.newBuilder(BUCKET_NAME, filePath)
                .setContentType(contentType)
                .build();
        return storage.create(blobInfo, imageData).getMediaLink();
    }
}
