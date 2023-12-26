package com.example.capstoneproject1.services.image;


import com.example.capstoneproject1.models.Image;
import com.example.capstoneproject1.models.Space;
import com.example.capstoneproject1.repository.ImageRepository;
import com.example.capstoneproject1.services.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@Service
public class ImageServiceImpl implements ImageService{

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    CloudinaryService cloudinaryService;

    @Transactional
    @Override
    public void deleteImageBySpaceId(Space space) throws IOException {
        List<Image> listImages = imageRepository.findBySpaceId(space);
        if(!listImages.isEmpty()) {
            for (Image image : listImages) {
                imageRepository.deleteById(image.getImageId());
                if(space.getId() > 60)
                    cloudinaryService.delete(image.getImageId());
            }
        }
    }
}
