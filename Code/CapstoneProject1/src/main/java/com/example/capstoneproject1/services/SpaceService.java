package com.example.capstoneproject1.services;

import com.example.capstoneproject1.models.Space;
import com.example.capstoneproject1.models.User;

public interface SpaceService {
    void update(Space space);
    Space findById(Integer id);
    void delete(Integer id);
    Iterable<Space> findAll();
}
