package com.alexc.acodelearn.resourceserver.service;

import com.alexc.acodelearn.resourceserver.entity.User;
import com.alexc.acodelearn.resourceserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
