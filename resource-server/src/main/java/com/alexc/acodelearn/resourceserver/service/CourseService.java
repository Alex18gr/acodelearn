package com.alexc.acodelearn.resourceserver.service;

import com.alexc.acodelearn.resourceserver.entity.Course;
import com.alexc.acodelearn.resourceserver.entity.CourseSection;
import com.alexc.acodelearn.resourceserver.entity.Resource.Resource;
import com.alexc.acodelearn.resourceserver.entity.User;
import com.alexc.acodelearn.resourceserver.json.CourseSectionJSON;
import com.alexc.acodelearn.resourceserver.repository.CourseRepository;
import com.alexc.acodelearn.resourceserver.repository.CourseSectionRepository;
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
    CourseSectionRepository courseSectionRepository;

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

    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    public boolean isUserOwnCourse(User user, Course course) {
        List<Course> userCourses = user.getCoursesOwned();
        for (Course c : userCourses) {
            if (c.equals(course)) return true;
        }
        return false;
    }

    public boolean courseHasResource(Course course, Resource resource) {
        List<Resource> resourceList = course.getCourseResources();
        for (Resource res : resourceList) {
            if (res.getResourceId() == resource.getResourceId())
                return true;
        }
        return false;
    }

    public CourseSection saveCourseSectionJSON(CourseSectionJSON courseSectionJSON, Course course) {
        CourseSection courseSection = new CourseSection();
        courseSection.setName(courseSectionJSON.getName());
        courseSection.setDescription(courseSectionJSON.getDescription());
        courseSection.setOrder(courseSectionJSON.getOrder());
        courseSection.setDateCreated(courseSectionJSON.getDateCreated());

        return this.courseSectionRepository.save(courseSection);
    }

    public CourseSection updateCourseSectionJSON(CourseSectionJSON courseSectionJSON, CourseSection courseSection) {

        courseSection.setName(courseSectionJSON.getName());
        courseSection.setDescription(courseSectionJSON.getDescription());
        courseSection.setOrder(courseSectionJSON.getOrder());
        courseSection.setDateCreated(courseSectionJSON.getDateCreated());

        return this.courseSectionRepository.save(courseSection);
    }

    public

}
