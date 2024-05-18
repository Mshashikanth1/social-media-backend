package com.slip.user.Models.mongodb;


import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Data
@Document(collection = "images")
public class Image {
    @Id
    private String id;
    private String filename;
    private String contentType;
    private ImageType imageType;
    private String postRef;
    private byte[] file;
}
