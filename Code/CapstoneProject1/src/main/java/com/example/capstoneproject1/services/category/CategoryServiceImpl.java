package com.example.capstoneproject1.services.category;

import com.example.capstoneproject1.models.CategorySpace;
import com.example.capstoneproject1.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements  CategoryService{

    @Autowired
    CategoryRepository categoryRepository;
    @Override
    public List<CategorySpace> findAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public CategorySpace getCategoryById(Integer categoryId) {
        return categoryRepository.getById(categoryId);
    }

    @Override
    public Boolean existsCategory(Integer categoryId) {
        Optional<CategorySpace> categorySpace = categoryRepository.findById(categoryId);
        return categorySpace.isPresent();
    }

    @Override
    public Optional<CategorySpace> findById(Integer categoryId) {
        return categoryRepository.findById(categoryId);
    }
}
