package com.alexc.acodelearn.resourceserver.controller;

import com.alexc.acodelearn.resourceserver.config.MethodSecurityConfig;
import com.alexc.acodelearn.resourceserver.entity.User;
import com.alexc.acodelearn.resourceserver.rest.ContentNotAllowedException;
import com.alexc.acodelearn.resourceserver.service.UserService;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class FooController {

    Logger logger = LoggerFactory.getLogger(FooController.class);

    @Autowired
    private UserService userService;

    @PreAuthorize("#oauth2.hasScope('read')")
    @RequestMapping(method = RequestMethod.GET, value = "/foos/{id}")
    @ResponseBody
    public Foo findById(@PathVariable long id, HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String principal = auth.getPrincipal().toString();
        String details = auth.getDetails().toString();


        String name = auth.getName();
        logger.info(auth.getAuthorities().toString());
        String role;
        if(request.isUserInRole(MethodSecurityConfig.ROLE_STUDENT)) {
            role = MethodSecurityConfig.ROLE_STUDENT;
        } else if (request.isUserInRole(MethodSecurityConfig.ROLE_TEACHER)) {
            role = MethodSecurityConfig.ROLE_TEACHER;
        } else {
            role = "no role";
        }

        if (!request.isUserInRole(MethodSecurityConfig.ROLE_TEACHER)) {
            throw new ContentNotAllowedException("user " + name + " not allowed!!!");
        }


        return new Foo("101", name, role);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/user-details")
    public User findUserDetails(HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User theUser = this.userService.findByUsername(auth.getName());

        logger.info(theUser.toString());

        return theUser;
    }

    @Data @NoArgsConstructor
    private class MyUserDetails {
        private String firstName;
        private String lastName;
        private String email;
        private List<String> roles;
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
