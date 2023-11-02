package com.example.capstoneproject1.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    private static final String CLOUD_NAME= "Share-Space-Finder";


    private static final String API_KEY= "698537876736678";


    private static final String API_SECRET= "7GEg5HoGcvHQ8nCRtJzpcPAKFkc";

    @Bean
    public Cloudinary cloudinary() {
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", CLOUD_NAME,
                "api_key", API_KEY,
                "api_secret", API_SECRET,
                "secure", true));
        return cloudinary;
    }




}
