package com.slip.user.repositories.mongodb;


import com.slip.user.Models.mongodb.Image;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ImageRepository extends MongoRepository<Image, String> {

}