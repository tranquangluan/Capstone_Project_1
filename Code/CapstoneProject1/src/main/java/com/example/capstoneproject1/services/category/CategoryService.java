package com.example.capstoneproject1.services.category;

import com.example.capstoneproject1.models.CategorySpace;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<CategorySpace> findAllCategory();

    CategorySpace getCategoryById(Integer categoryId);
    Boolean existsCategory(Integer categoryId);
    Optional<CategorySpace> findById(Integer categoryId);
}
