package com.example.capstoneproject1.services;

import com.example.capstoneproject1.models.CategorySpace;
import com.example.capstoneproject1.models.Space;

public interface CategorySpaceService {
    CategorySpace findById(Integer id);
    Iterable<CategorySpace> findAll();
}
