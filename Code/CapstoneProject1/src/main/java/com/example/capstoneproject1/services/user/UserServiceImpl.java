package com.example.capstoneproject1.services.user;

import com.example.capstoneproject1.dto.response.user.PageUser;
import com.example.capstoneproject1.models.Role;
import com.example.capstoneproject1.models.User;
import com.example.capstoneproject1.repository.FavoriteRepository;
import com.example.capstoneproject1.repository.RoleRepository;
import com.example.capstoneproject1.repository.SpaceRepository;
import com.example.capstoneproject1.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    FavoriteRepository favoriteRepository;

    @Autowired
    SpaceRepository spaceRepository;


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

    public String getRoleCode(String role) {
        switch (role) {
            case "Admin":
                return "R1";
            case "Owner":
                return "R2";
            case "User":
                return "R3";
            default:
                return "None";
        }
    }

    @Override
    public PageUser getAllUsers(Integer userId, String email, String name, Integer pageNo, Integer pageSize, String sortBy, String sortDir, String role) {

        try {
            String roleCode = getRoleCode(role);
            if (!Objects.equals(sortDir, "None")) {
                // Create Sorted instance
                Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                        : Sort.by(sortBy).descending();
                // create Pageable instance
                Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
                Page<User> pageUsers;
                if (Objects.equals(roleCode, "None")) {
                    pageUsers = userRepository.findByRoleCodeAndConditions(userId, email, name, null, pageable);
                } else {
                    pageUsers = userRepository.findByRoleCodeAndConditions(userId, email, name, roleCode, pageable);
                }
                System.out.println(2);
                Integer totalPages = pageUsers.getTotalPages();
                List<User> listUsers = pageUsers.getContent();
                return new PageUser(totalPages, listUsers);
            } else {
                System.out.println("Đây");
                Pageable pageable = PageRequest.of(pageNo, pageSize);
                System.out.println(1);
                Page<User> pageUsers;
                if (Objects.equals(roleCode, "None")) {
                    pageUsers = userRepository.findByRoleCodeAndConditions(userId, email, name, null, pageable);
                } else {
                    pageUsers = userRepository.findByRoleCodeAndConditions(userId, email, name, roleCode, pageable);
                }
                System.out.println(2);
                Integer totalPages = pageUsers.getTotalPages();
                List<User> listUsers = pageUsers.getContent();
                return new PageUser(totalPages, listUsers);
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            return new PageUser();
        }
    }

    @Override
    @Transactional
    public Boolean deleteUserByUserId(Integer userId) {
        try {
            favoriteRepository.deleteByUserId_Id(userId);
            spaceRepository.deleteAllByOwnerId_Id(userId);
            userRepository.deleteById(userId);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    @Override
    public void deleteRoleByUserId(Integer userId) {
        userRepository.deleteRole(userId);
    }

    @Override
    public void update(User user) {
        userRepository.save(user);
    }

    @Override
    public User findByUserId(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
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
        // add role to Set Role
        role.ifPresent(roles::add);
        if (user.isPresent()) {
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
