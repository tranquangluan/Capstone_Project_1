
package com.example.capstoneproject1.services;


import com.example.capstoneproject1.models.Role;

import java.util.Optional;

public interface RoleService {
    Optional<Role> findByRoleCode(String roleCode);
}
