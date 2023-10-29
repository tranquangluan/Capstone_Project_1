package com.example.capstoneproject1.services;

import com.example.capstoneproject1.models.User;

import java.util.Optional;

public interface UserService {
//    void update(User user);
//    User findById(Integer id);
//    void delete(Integer id);
//    User findByUsername(String name);
//    Iterable<User> findAll();

    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);
    User save(User user);
}
