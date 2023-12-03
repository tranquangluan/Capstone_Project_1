package com.example.capstoneproject1.repository;

import com.example.capstoneproject1.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByRefreshToken(String refreshToken);
    String getPasswordByEmail(String email);
    List<User> findByRoles(String roles);
    Optional<User> findByEmail(String email);
    Optional<User> findById(Integer userId);
    Boolean existsByEmail(String email);
    Boolean existsByPassword(String password);
    Boolean existsByRefreshToken(String refreshToken);
    Page<User> findByEmailContaining(String email, Pageable pageable);
    Page<User> findByIdAndEmailContaining(Integer userId, String email, Pageable pageable);
    Page<User> findByIdAndNameContaining(Integer userId, String name, Pageable pageable);
    Page<User> findByNameContaining(String name, Pageable pageable);

    @Modifying
    @Query(value = "DELETE FROM users_role WHERE users_id = :userId", nativeQuery = true)
    @Transactional
    void deleteRole(Integer userId);

    @Query(value = "SELECT u.* FROM users_role ur "
            + "JOIN user u ON ur.users_id = u.userID "
            + "JOIN role r ON ur.role_id = r.role_code "
            + "WHERE r.role_code = :roleCode", nativeQuery = true)
    List<User> findByRoleCode(@Param("roleCode") String roleCode);


    @Query(value = "SELECT u FROM User u " +
            "JOIN u.roles r " +
            "WHERE (:userId IS NULL OR u.id = :userId) " +
            "AND (:email IS NULL OR u.email LIKE CONCAT('%', :email, '%')) " +
            "AND (:name IS NULL OR u.name LIKE CONCAT('%', :name, '%')) " +
            "AND (:roleCode IS NULL OR r.roleCode = :roleCode)", nativeQuery = false)
    Page<User> findByRoleCodeAndConditions(
            @Param("userId") Integer userId,
            @Param("email") String email,
            @Param("name") String name,
            @Param("roleCode") String roleCode,
            Pageable pageable
    );




}
