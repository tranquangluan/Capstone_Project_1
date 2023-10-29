package com.example.capstoneproject1.services;

import com.example.capstoneproject1.models.User;
import com.example.capstoneproject1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
//    @Override
//    public void update(User user) {
//        userRepository.save(user);
//    }
//
//    @Override
//    public User findById(Integer id) {
//        return  userRepository.findById(id).orElse(null);
//    }
//
//    @Override
//    public void delete(Integer id) {
//        userRepository.deleteById(id);
//    }
//
//    @Override
//    public User findByUsername(String name) {
//        return userRepository.findByUsername(name);
//    }
//
//    @Override
//    public List<User> findAll() {
//        return (List<User>) userRepository.findAll();
//    }
}
