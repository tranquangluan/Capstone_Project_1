package com.example.capstoneproject1.services;

import com.example.capstoneproject1.models.User;

public interface UserService {
    void update(User user);
    User findById(Integer id);
    void delete(Integer id);
    Iterable<User> findAll();
}
