package com.example.capstoneproject1.repository;

import com.example.capstoneproject1.models.Space;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpaceRepository extends CrudRepository<Space, Integer> {
}
