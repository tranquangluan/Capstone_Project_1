package com.example.capstoneproject1.repository;

import com.example.capstoneproject1.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByRefreshToken(String refreshToken);
    String getPasswordByEmail(String email);
    Optional<User> findByEmail(String email);
    Optional<User> findById(Integer userId);
    Boolean existsByEmail(String email);
    Boolean existsByPassword(String password);
    Boolean existsByRefreshToken(String refreshToken);
    Page<User> findByEmailContaining(String email, Pageable pageable);
    Page<User> findByIdAndEmailContaining(Integer userId, String email, Pageable pageable);
    Page<User> findByIdAndNameContaining(Integer userId, String name, Pageable pageable);
    Page<User> findByNameContaining(String name, Pageable pageable);



}
