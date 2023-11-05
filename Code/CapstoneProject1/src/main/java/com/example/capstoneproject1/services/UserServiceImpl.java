package com.example.capstoneproject1.services;

import com.example.capstoneproject1.models.Role;
import com.example.capstoneproject1.models.User;
import com.example.capstoneproject1.repository.RoleRepository;
import com.example.capstoneproject1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Boolean existPassword(String password) {
        return userRepository.existsByPassword(password);
    }

    @Override
    public Boolean existsByRefreshToken(String refreshToken) {
        return userRepository.existsByRefreshToken(refreshToken);
    }

    @Override
    public String getPasswordByEmail(String email) {
        return userRepository.getPasswordByEmail(email);
    }

    @Override
    public Boolean existsById(Integer userId) {
        return userRepository.existsById(userId);
    }

    @Override
    public void update(User user) {
        userRepository.save(user);
    }

    @Override
    public User findByUserId(Integer id) {
        return  userRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findByUsername(String name) {
        return userRepository.findByUsername(name);
    }

    @Override
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void addToUser(String userEmail, String roleCode) {
        Optional<User> user = userRepository.findByEmail(userEmail);
        Optional<Role> role = roleRepository.findByRoleCode(roleCode);
        Set<Role> roles = new HashSet<>();
        // add role to Set Roles
        role.ifPresent(roles::add);
        if(user.isPresent()) {
            user.get().setRoles(roles);
            userRepository.save(user.get());
        }

    }

    @Override
    public User findByRefreshToken(String refreshToken) {
        return userRepository.findByRefreshToken(refreshToken);
    }

    @Override
    public Optional<User> findById(Integer userId) {
        return userRepository.findById(userId);
    }
}
