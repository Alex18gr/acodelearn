package com.alexc.acodelearn.resourceserver.service;

import com.alexc.acodelearn.resourceserver.entity.Course;
import com.alexc.acodelearn.resourceserver.entity.User;
import com.alexc.acodelearn.resourceserver.json.UserJSON;
import com.alexc.acodelearn.resourceserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findByUsername(String username) {
        return userRepository.findByuserName(username);
    }

    public List<UserJSON> getUserJSONfromUsersList(List<User> users) {
        List<UserJSON> usersJSON = new ArrayList<>();
        for (User user : users) {
            usersJSON.add(new UserJSON(user));
        }
        return usersJSON;
    }
}
