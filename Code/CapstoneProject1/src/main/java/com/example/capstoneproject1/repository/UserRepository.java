package com.example.capstoneproject1.repository;

import com.example.capstoneproject1.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    @Query(value= "Select * from user where user_name = :name ",nativeQuery = true)
    User findByUsername(String name);
}
