package com.alexc.acodelearn.resourceserver.service;

import com.alexc.acodelearn.resourceserver.repository.CourseRepository;
import com.alexc.acodelearn.resourceserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseService {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    UserRepository userRepository;


}
