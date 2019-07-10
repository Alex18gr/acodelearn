package com.alexc.acodelearn.resourceserver.controller;

import com.alexc.acodelearn.resourceserver.config.MethodSecurityConfig;
import com.alexc.acodelearn.resourceserver.rest.ContentNotAllowedException;
import lombok.Data;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class FooController {

    @PreAuthorize("#oauth2.hasScope('read')")
    @RequestMapping(method = RequestMethod.GET, value = "/foos/{id}")
    @ResponseBody
    public Foo findById(@PathVariable long id, HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String name = auth.getName();
        String role;
        if(request.isUserInRole(MethodSecurityConfig.ROLE_STUDENT)) {
            role = MethodSecurityConfig.ROLE_STUDENT;
        } else if (request.isUserInRole(MethodSecurityConfig.ROLE_TEACHER)) {
            role = MethodSecurityConfig.ROLE_TEACHER;
        } else {
            role = "no role";
        }

        if (!role.equals(MethodSecurityConfig.ROLE_TEACHER)) {
            throw new ContentNotAllowedException("user " + name + " not allowed!!!");
        }


        return new Foo("101", name, role);
    }

    @Data
    private class Foo {
        private String id;
        private String name;
        private String role;

        public Foo(String id, String name, String role) {
            this.id = id;
            this.name = name;
            this.role = role;
        }
    }

}
