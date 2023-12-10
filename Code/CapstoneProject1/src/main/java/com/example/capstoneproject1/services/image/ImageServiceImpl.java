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

    @Override
    public Image save(Image image) {
        return imageRepository.save(image);
    }

    @Override
    public boolean existsById(String imageId) {
        return imageRepository.existsById(imageId);
    }

    @Transactional
    @Override
    public void deleteImageBySpaceId(Space space) throws IOException {
        List<Image> listImages = imageRepository.findBySpaceId(space);
        if(!listImages.isEmpty()) {
            for (Image image : listImages) {
                imageRepository.deleteById(image.getImageId());
                cloudinaryService.delete(image.getImageId());
            }
        }
    }

    @Override
    public void deleteById(String imageId) {
        imageRepository.deleteById(imageId);
    }
}
