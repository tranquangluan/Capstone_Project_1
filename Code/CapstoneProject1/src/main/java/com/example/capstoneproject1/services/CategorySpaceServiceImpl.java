package com.example.capstoneproject1.services;

import com.example.capstoneproject1.models.CategorySpace;
import com.example.capstoneproject1.models.Space;
import com.example.capstoneproject1.repository.CategorySpaceRepository;
import com.example.capstoneproject1.repository.SpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategorySpaceServiceImpl implements CategorySpaceService{
    @Autowired
    private CategorySpaceRepository categorySpaceRepository;
    @Override
    public CategorySpace findById(Integer id) {
        return  categorySpaceRepository.findById(id).orElse(null);
    }

    @Override
    public Iterable<CategorySpace> findAll() {
        return (List<CategorySpace>) categorySpaceRepository.findAll();
    }
}
