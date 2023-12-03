package com.example.capstoneproject1.repository;

import com.example.capstoneproject1.models.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatusRepository extends JpaRepository<Status,Integer> {
    Optional<Status> findById(Integer integer);
}
