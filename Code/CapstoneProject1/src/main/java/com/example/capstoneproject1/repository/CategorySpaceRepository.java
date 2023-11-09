package com.example.capstoneproject1.repository;

import com.example.capstoneproject1.models.CategorySpace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategorySpaceRepository extends JpaRepository<CategorySpace,Integer> {
    List<CategorySpace> findAll();

}
