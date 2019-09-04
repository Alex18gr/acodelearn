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
import java.util.ArrayList;
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

    public CourseSection findCourseSectionById(int id) {
        return courseSectionRepository.findById(id).get();
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
        courseSection.setCourse(course);

        return this.courseSectionRepository.save(courseSection);
    }

    public CourseSection updateCourseSectionJSON(CourseSectionJSON courseSectionJSON, CourseSection courseSection) {

        courseSection.setName(courseSectionJSON.getName());
        courseSection.setDescription(courseSectionJSON.getDescription());
        courseSection.setOrder(courseSectionJSON.getOrder());
        courseSection.setDateCreated(courseSectionJSON.getDateCreated());

        return this.courseSectionRepository.save(courseSection);
    }

    public CourseSection findCourseSectionFromCourseById(int sectionId, Course currentCourse) {
        List<CourseSection> courseSections = currentCourse.getCourseSections();
        for (CourseSection cs : courseSections) {
            if (cs.getCourseSectionId().getCourseSectionId() == sectionId) {
                return cs;
            }
        }
        return null;
    }

    public void deleteCourseSection(CourseSection cs) {
        this.courseSectionRepository.delete(cs);
    }

    public void deleteCourseSectionById(CourseSection.CourseSectionId courseSectionId, Course course) {
        // this.courseSectionRepository.deleteById(courseSectionId);
        this.courseSectionRepository.deleteByCourseSectionId(courseSectionId, course);
//        this.courseSectionRepository.deleteByCourseSectionIdAndCourse(
//                courseSectionId,
//                course
//        );
    }

    public CourseSection saveCourseSection(CourseSection courseSection) {
        return this.courseSectionRepository.save(courseSection);
    }

    public boolean checkCourseContainResourcesByIds(Course currentCourse, int[] resourceIds, ArrayList<Resource> addResources) {
        boolean resourceExists;
        for (int resourceId : resourceIds) {
            resourceExists = false;
            for (Resource res : currentCourse.getCourseResources()) {
                if (res.getResourceId() == resourceId) {
                    addResources.add(res);
                    resourceExists = true;
                }
            }
            if (!resourceExists) {
                return false;
            }
        }
        return true;
    }

    public boolean checkCourseSectionContainResourcesByIds(Course currentCourse, int[] resourceIds) {
        boolean resourceExist;
        for (int resourceId : resourceIds) {
            resourceExist = false;
            for (Resource res : currentCourse.getCourseResources()) {
                if (res.getResourceId() == resourceId) {
                    resourceExist = true;
                    break;
                }
            }
            if (!resourceExist) {
                return false;
            }
        }

        return true;
    }

    public CourseSection deleteResourcesFromCourseSection(CourseSection courseSection, int[] resourceIds) {
        for (int resourceId : resourceIds) {
            for (Resource res : courseSection.getResources()) {
                if (res.getResourceId() == resourceId) {
                    courseSection.getResources().remove(res);
                    break;
                }
            }
        }

        return this.saveCourseSection(courseSection);
    }

    public CourseSection updateCourseSectionOrderJSON(CourseSectionJSON courseSectionJSON, CourseSection courseSection) {
        courseSection.setOrder(courseSectionJSON.getOrder());
        return this.courseSectionRepository.save(courseSection);
    }
}
