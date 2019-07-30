package com.alexc.acodelearn.resourceserver.controller;

import com.alexc.acodelearn.resourceserver.entity.Course;
import com.alexc.acodelearn.resourceserver.entity.Resource.FileResource;
import com.alexc.acodelearn.resourceserver.entity.Resource.Resource;
import com.alexc.acodelearn.resourceserver.entity.User;
import com.alexc.acodelearn.resourceserver.json.CourseJSON;
import com.alexc.acodelearn.resourceserver.json.ResourcesCollectionJSON;
import com.alexc.acodelearn.resourceserver.rest.UserNotAllowedException;
import com.alexc.acodelearn.resourceserver.service.CourseService;
import com.alexc.acodelearn.resourceserver.service.ResourceService;
import com.alexc.acodelearn.resourceserver.service.UserService;
import com.alexc.acodelearn.resourceserver.util.ResourceHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class CourseController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserService userService;

    @Autowired
    CourseService courseService;

    @Autowired
    ResourceService resourceService;

    @RequestMapping("/user-courses")
    @Transactional
    public CourseJSON.CourseListJSON getUserCourses(HttpServletRequest request) {

        // TODO: Need to check the role of the user before doing anything...

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = userService.findByUsername(auth.getName());
        user.getCoursesEnrolled().size();
        logger.info(user.toString());
        logger.info(user.getCoursesEnrolled().toString());
        List<CourseJSON> courses = new ArrayList<>();
        for (Course course : user.getCoursesEnrolled())
            courses.add(new CourseJSON(course));

        return new CourseJSON.CourseListJSON(courses);
    }

    @RequestMapping("/course/{courseId}/resources")
    @Transactional
    public ResourcesCollectionJSON getCourseResources(HttpServletRequest request, @PathVariable String courseId) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = userService.findByUsername(auth.getName());
        int mCourseId = Integer.parseInt(courseId);
        Course currentCourse = courseService.findById(mCourseId);
        if (courseService.isUserEnrolledInCourse(user, currentCourse)) {
            ResourcesCollectionJSON courseResources =
                    ResourceHelper.getResourcesCollectionJSONfromResources(currentCourse.getCourseResources());
            return courseResources;
        } else {
            throw new UserNotAllowedException("user not enrolled to this course and not authorized");
        }



    }

    @RequestMapping(
            value = "/course/{courseId}/resource/{resourceId}"
    )
    public HttpEntity<byte[]> getFile(
            @PathVariable String courseId,
            @PathVariable String resourceId,
            HttpServletResponse response
    ) {
        // get authenticated user
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        User user = userService.findByUsername(auth.getName());

        // receive the file resource from the service
        FileResource fileResource = this.resourceService.findByResourceId(Integer.parseInt(resourceId));

        // check for the user if he is authorized to user this resource
//        if (!courseService.isUserEnrolledInCourse(user, fileResource.getCourse())) {
//            throw new UserNotAllowedException("User not enrolled to the course that the requested resource belongs");
//        }

        // log the resource file info
        logger.info(fileResource.getName() + ", " + fileResource.getSummary() + ", " + fileResource.getFileName() +
                ", " + fileResource.getFileType() + ", " + fileResource.getCourse());

        // set the http headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        response.setHeader("Content-Disposition", "attatchment; filename=" + fileResource.getFileName());

        // create and return the http entity with the custom headers and the file data
        return new HttpEntity<byte[]>(fileResource.getFileData(), headers);
    }

}
