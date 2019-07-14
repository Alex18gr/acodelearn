package com.alexc.acodelearn.authorizationserver.service;

import com.alexc.acodelearn.authorizationserver.entity.Role;
import com.alexc.acodelearn.authorizationserver.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

}
