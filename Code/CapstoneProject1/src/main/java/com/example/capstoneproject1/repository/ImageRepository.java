package com.example.capstoneproject1.repository;

import com.example.capstoneproject1.models.Image;
import com.example.capstoneproject1.models.Space;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, String> {
    List<Image> findBySpaceId(Space spaceId);
}
