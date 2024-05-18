package com.slip.user.controllers;


import com.slip.user.Models.mongodb.ImageType;
import com.slip.user.service.GoogleCloudStorageService;
import com.slip.user.service.ImageService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;


@RestController
@CrossOrigin
@RequestMapping("/api/v1/image")
public class ImageController {
    public final ImageService imageService;
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping()
       public String uploadUserImage(@RequestParam("file") MultipartFile image,
                                   @RequestParam(value = "img-type",required = false) ImageType imageType,
                                   @RequestParam(value = "post-ref",required = false) String postRef) {
             return imageService.uploadImage( image, imageType, postRef);
       }
}
