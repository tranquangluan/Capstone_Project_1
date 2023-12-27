package com.example.capstoneproject1.services.image;

import com.example.capstoneproject1.models.Image;
import com.example.capstoneproject1.models.Space;

import java.io.IOException;

public interface ImageService {
    Image save(Image image);
    boolean existsById(String imageId);
    void deleteImageBySpaceId(Space space) throws IOException;
    void deleteById(String imageId);
}
