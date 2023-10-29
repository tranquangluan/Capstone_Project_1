package com.example.capstoneproject1.services.impl;


import com.example.capstoneproject1.models.Role;
import com.example.capstoneproject1.repository.RoleRepository;
import com.example.capstoneproject1.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleRepository roleRepository;

    @Override
    public Optional<Role> findByRoleCode(String roleCode) {
        return roleRepository.findByRoleCode(roleCode);
    }

}
