package com.alexc.acodelearn.authorizationserver.controller;

import com.alexc.acodelearn.authorizationserver.entity.Role;
import com.alexc.acodelearn.authorizationserver.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("role")
public class RolesController {

    @Autowired
    private RoleService roleService;

    @GetMapping("roles")
    public Collection<Role> getAllRoles() {
        return roleService.findAll();
    }

}
