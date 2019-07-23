package com.alexc.acodelearn.resourceserver.service;

import com.alexc.acodelearn.resourceserver.entity.Course;
import com.alexc.acodelearn.resourceserver.entity.User;
import com.alexc.acodelearn.resourceserver.repository.CourseRepository;
import com.alexc.acodelearn.resourceserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CourseService {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    UserService userService;

    public Course findById(int id) {
        return courseRepository.findById(id);
    }

    public boolean isUserEnrolledInCourse(User user, Course course) {
        List<Course> userCourses = user.getCoursesEnrolled();
        for (Course c : userCourses) {
            if (c.equals(course)) return true;
        }
        return false;
    }


}
