package com.example.capstoneproject1.services.category;

import com.example.capstoneproject1.models.CategorySpace;

import java.util.List;

public interface CategoryService {

    List<CategorySpace> findAllCategory();

    CategorySpace getCategoryById(Integer categoryId);
    Boolean existsCategory(Integer categoryId);
}
