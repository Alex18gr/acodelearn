package com.alexc.acodelearn.resourceserver.controller;

import com.alexc.acodelearn.resourceserver.entity.Course;
import com.alexc.acodelearn.resourceserver.entity.User;
import com.alexc.acodelearn.resourceserver.json.CourseJSON;
import com.alexc.acodelearn.resourceserver.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class CourseController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserService userService;

    @RequestMapping("/user-courses")
    @Transactional
    public List<CourseJSON> getUserCourses(HttpServletRequest request) {

        // TODO: Need to check the role of the user before doing anything...

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = userService.findByUsername(auth.getName());
        user.getCoursesEnrolled().size();
        logger.info(user.toString());
        logger.info(user.getCoursesEnrolled().toString());
        List<CourseJSON> courses = new ArrayList<>();
        for (Course course : user.getCoursesEnrolled())
            courses.add(new CourseJSON(course));

        return courses;
    }

}