package com.alexc.acodelearn.resourceserver.controller;

import com.alexc.acodelearn.resourceserver.entity.Course;
import com.alexc.acodelearn.resourceserver.entity.User;
import com.alexc.acodelearn.resourceserver.json.CourseJSON;
import com.alexc.acodelearn.resourceserver.json.DetailedResourcesCollectionJSON;
import com.alexc.acodelearn.resourceserver.json.ResourcesCollectionJSON;
import com.alexc.acodelearn.resourceserver.rest.UserNotAllowedException;
import com.alexc.acodelearn.resourceserver.service.CourseService;
import com.alexc.acodelearn.resourceserver.service.ResourceService;
import com.alexc.acodelearn.resourceserver.service.UserService;
import com.alexc.acodelearn.resourceserver.util.ResourceHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/instructor")
public class InstructorController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserService userService;

    @Autowired
    CourseService courseService;

    @Autowired
    ResourceService resourceService;

    @RequestMapping(
            value = "/get-resources",
            method = RequestMethod.POST)
    // public void process(@RequestBody List<Map<String, Object>> payload)
    public ResourcesCollectionJSON process(@RequestBody List<Integer> payload)
            throws Exception {

        logger.info(payload.toString());

//        ArrayList<Integer> list = new ArrayList<>();
//        list.add(1);
//        list.add(10);
//        list.add(12);
//        list.add(13);
//        list.add(14);
//        list.add(3);
//        list.add(4);
//        list.add(8);

        // TODO: here we must check if the course that will be put in the path have these resources


        // TODO: we need a way to get the resources with their details
        logger.info(this.resourceService.findAllById(payload).toString());
        ResourcesCollectionJSON courseResources =
                ResourceHelper.getResourcesCollectionJSONfromResources(
                        this.resourceService.findAllById(payload)
                );
        return courseResources;

    }

    @RequestMapping(
            value = "/get-resources-all",
            method = RequestMethod.POST)
    // public void process(@RequestBody List<Map<String, Object>> payload)
    public DetailedResourcesCollectionJSON processDetailed(@RequestBody List<Integer> payload)
            throws Exception {

        logger.info(payload.toString());

//        ArrayList<Integer> list = new ArrayList<>();
//        list.add(1);
//        list.add(10);
//        list.add(12);
//        list.add(13);
//        list.add(14);
//        list.add(3);
//        list.add(4);
//        list.add(8);

        // TODO: here we must check if the course that will be put in the path have these resources


        // TODO: we need a way to get the resources with their details
        logger.info(this.resourceService.findAllById(payload).toString());
        DetailedResourcesCollectionJSON resources =
                ResourceHelper.getDetailedResourcesCollectionJSONfromResources(
                        this.resourceService.findAllById(payload)
                );
        return resources;

    }

    @RequestMapping("/owned-courses")
    @Transactional
    public CourseJSON.CourseListJSON getUserCourses(HttpServletRequest request) {

        // TODO: Need to check the role of the user before doing anything...
        if (!request.isUserInRole("ROLE_TEACHER")) {
            throw new UserNotAllowedException("User do not have the role need for this content");
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = userService.findByUsername(auth.getName());
        user.getCoursesOwned().size();
        logger.info(user.toString());
        logger.info(user.getCoursesOwned().toString());
        List<CourseJSON> courses = new ArrayList<>();
        for (Course course : user.getCoursesOwned())
            courses.add(new CourseJSON(course));

        return new CourseJSON.CourseListJSON(courses);
    }

}
