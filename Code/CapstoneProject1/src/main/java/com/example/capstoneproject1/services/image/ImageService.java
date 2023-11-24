package com.example.capstoneproject1.services.image;

import com.example.capstoneproject1.models.Space;

import java.io.IOException;

public interface ImageService {

    void deleteImageBySpaceId(Space space) throws IOException;

}
