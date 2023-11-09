package com.example.capstoneproject1.services.category;

import com.example.capstoneproject1.models.CategorySpace;
import com.example.capstoneproject1.repository.CategorySpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements  CategoryService{

    @Autowired
    CategorySpaceRepository categorySpaceRep;


    @Override
    public List<CategorySpace> findAllCategory() {
        return categorySpaceRep.findAll();
    }

    @Override
    public CategorySpace getCategoryById(Integer categoryId) {
        return categorySpaceRep.getById(categoryId);
    }

    @Override
    public Boolean existsCategory(Integer categoryId) {
        Optional<CategorySpace> categorySpace = categorySpaceRep.findById(categoryId);
        return categorySpace.isPresent();
    }
}
