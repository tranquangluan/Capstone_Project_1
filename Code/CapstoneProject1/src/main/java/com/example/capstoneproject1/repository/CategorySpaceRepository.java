package com.example.capstoneproject1.repository;

import com.example.capstoneproject1.models.CategorySpace;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategorySpaceRepository extends CrudRepository<CategorySpace,Integer> {
}
