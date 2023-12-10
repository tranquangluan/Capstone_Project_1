package com.example.capstoneproject1.repository;

import com.example.capstoneproject1.models.CategorySpace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategorySpace,Integer> {
    List<CategorySpace> findAll();
    Optional<CategorySpace> findById(Integer categoryId);
}
