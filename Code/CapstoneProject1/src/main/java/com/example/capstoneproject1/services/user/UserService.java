package com.example.capstoneproject1.services.user;

import com.example.capstoneproject1.dto.response.user.PageUser;
import com.example.capstoneproject1.models.Role;
import com.example.capstoneproject1.models.User;

import java.util.Optional;

public interface UserService {
    void update(User user);
    User findByUserId(Integer id);
    void delete(Integer id);
    Iterable<User> findAll();
    User findByRefreshToken(String refreshToken);
    Optional<User> findById(Integer userId);
    Optional<User> findByEmail(String email);
    User saveUser(User user);
    Role saveRole(Role role);
    void addToUser(String userEmail, String roleName);
    Boolean existsByEmail(String email);
    User save(User user);
    Boolean existPassword(String password);
    Boolean existsByRefreshToken(String refreshToken);
    String getPasswordByEmail(String email);
    Boolean existsById(Integer userId);

    PageUser getAllUsers(Integer userId, String email, String name , Integer pageNo, Integer pageSize , String sortBy, String sortDir , String role);

    Boolean deleteUserByUserId(Integer userId);

    void deleteRoleByUserId(Integer userId);
}
