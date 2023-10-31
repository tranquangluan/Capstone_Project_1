package com.example.capstoneproject1.repository;

import com.example.capstoneproject1.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value= "Select * from user where user_name = :name ",nativeQuery = true)
    User findByUsername(String name);


    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);
}
