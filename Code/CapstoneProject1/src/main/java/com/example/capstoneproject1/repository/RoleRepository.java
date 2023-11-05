package com.example.capstoneproject1.repository;

import com.example.capstoneproject1.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
//    @Query(value= "Select * from role where role_code = :roleCode ",nativeQuery = true)
//    Role findByRoleCode(String roleCode);
    Optional<Role> findByRoleCode(String roleCode);

}
