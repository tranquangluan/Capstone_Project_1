package com.example.capstoneproject1.repository;

import com.example.capstoneproject1.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

}
