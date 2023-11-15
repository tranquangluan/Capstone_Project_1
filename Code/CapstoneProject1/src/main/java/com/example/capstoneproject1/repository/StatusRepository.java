package com.example.capstoneproject1.repository;

import com.example.capstoneproject1.models.SpaceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<SpaceStatus,Integer> {
}
