package com.alexc.acodelearn.resourceserver.controller;

import com.alexc.acodelearn.resourceserver.entity.Course;
import com.alexc.acodelearn.resourceserver.entity.CourseSection;
import com.alexc.acodelearn.resourceserver.entity.Resource.*;
import com.alexc.acodelearn.resourceserver.entity.User;
import com.alexc.acodelearn.resourceserver.json.*;
import com.alexc.acodelearn.resourceserver.json.resource.*;
import com.alexc.acodelearn.resourceserver.rest.ContentNotFoundException;
import com.alexc.acodelearn.resourceserver.rest.UserNotAllowedException;
import com.alexc.acodelearn.resourceserver.service.CourseService;
import com.alexc.acodelearn.resourceserver.service.ResourceService;
import com.alexc.acodelearn.resourceserver.service.UserService;
import com.alexc.acodelearn.resourceserver.util.ResourceHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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

    @RequestMapping(value = "/course/{courseId}/resource/file", method = RequestMethod.POST)
    public ResponseEntity<? extends AbstractResourceJSON> createFileResource(
            HttpServletRequest request,
            @RequestParam("file") MultipartFile file,
            // @RequestParam("resource") FileResourceJSON resource,
            @RequestParam("resource") String resourceRaw,
            @PathVariable String courseId) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        FileResourceJSON resource = mapper.readValue(resourceRaw, FileResourceJSON.class);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = userService.findByUsername(auth.getName());
        int mCourseId = Integer.parseInt(courseId);
        Course currentCourse = courseService.findById(mCourseId);
        if (!request.isUserInRole("ROLE_TEACHER") || !courseService.isUserOwnCourse(
                user, currentCourse
        )) {
            throw new UserNotAllowedException("User not in role or user not own the resource");
        }

        FileResource newFileResource = ResourceHelper.getFileResource(file);
        newFileResource.setCourse(currentCourse);
        newFileResource.setDateCreated(new Date());
        newFileResource.setName(resource.getName());
        newFileResource.setSummary(resource.getSummary());

        FileResource savedResource = (FileResource) resourceService.save(newFileResource);
        ResponseEntity<? extends AbstractResourceJSON> responseEntity = getResponseEntity(savedResource);
        return responseEntity;
    }

    @RequestMapping(value = "/course/{courseId}/sections", method = RequestMethod.GET)
    public HttpEntity<List<CourseSectionJSON>> getAllCourseSections(
            HttpServletRequest request,
            @PathVariable Integer courseId
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = userService.findByUsername(auth.getName());
        Course currentCourse = courseService.findById(courseId);

        if (!courseService.isUserOwnCourse(user, currentCourse) || !courseService.isUserEnrolledInCourse(user, currentCourse)) {
            throw new UserNotAllowedException("User doesnt own or not enrolled to this course");
        }

        List<CourseSection> courseSections = currentCourse.getCourseSections();
        List<CourseSectionJSON> courseSectionsJSON = new ArrayList<>();
        for (CourseSection cs : courseSections) {
            courseSectionsJSON.add(
                    new CourseSectionJSON(
                            cs,
                            cs.getCourse(),
                            cs.getResources()
                    )
            );
        }

        return new ResponseEntity<>(courseSectionsJSON, HttpStatus.OK);
    }

    @RequestMapping(value = "/course/{courseId}/sections", method = RequestMethod.POST)
    public HttpEntity<CourseSectionJSON> saveCourseSection(
            HttpServletRequest request,
            @RequestBody CourseSectionJSON courseSectionJSON,
            @PathVariable String courseId
    ) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = userService.findByUsername(auth.getName());
        int mCourseId = Integer.parseInt(courseId);
        Course currentCourse = courseService.findById(mCourseId);
        if (!request.isUserInRole("ROLE_TEACHER") || !courseService.isUserOwnCourse(
                user, currentCourse
        )) {
            throw new UserNotAllowedException("User not in role or user not own the resource");
        }

        CourseSection savedCourseSection = this.courseService.saveCourseSectionJSON(courseSectionJSON, currentCourse);
        return new ResponseEntity<>(new CourseSectionJSON(savedCourseSection), HttpStatus.OK);
    }

    @RequestMapping(value = "/course/{courseId}/sections/{sectionId}", method = RequestMethod.PUT)
    public HttpEntity<CourseSectionJSON> updateCourseSection(
            HttpServletRequest request,
            @RequestBody CourseSectionJSON courseSectionJSON,
            @PathVariable Integer courseId,
            @PathVariable Integer sectionId) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = userService.findByUsername(auth.getName());
        int mCourseId = courseId;
        Course currentCourse = courseService.findById(mCourseId);
        if (!request.isUserInRole("ROLE_TEACHER") || !courseService.isUserOwnCourse(
                user, currentCourse
        )) {
            throw new UserNotAllowedException("User not in role or user not own the resource");
        }

        CourseSection currentCourseSection = this.courseService.findCourseSectionFromCourseById(sectionId, currentCourse);

        if (currentCourseSection == null) {
            throw new ContentNotFoundException("course section requested not found or is not belong to current course");
        }

        CourseSection savedCourseSection = this.courseService.updateCourseSectionJSON(courseSectionJSON, currentCourseSection);
        return new ResponseEntity<>(new CourseSectionJSON(savedCourseSection), HttpStatus.OK);
    }

    @RequestMapping(value = "/course/{courseId}/sections/{sectionId}", method = RequestMethod.DELETE)
    public HttpEntity<CourseSectionJSON> deleteCourseSection(
            HttpServletRequest request,
            @PathVariable Integer courseId,
            @PathVariable Integer sectionId
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = userService.findByUsername(auth.getName());
        int mCourseId = courseId;
        Course currentCourse = courseService.findById(mCourseId);
        if (!request.isUserInRole("ROLE_TEACHER") || !courseService.isUserOwnCourse(
                user, currentCourse
        )) {
            throw new UserNotAllowedException("User not in role or user not own the resource");
        }

        CourseSection currentCourseSection = this.courseService.findCourseSectionFromCourseById(sectionId, currentCourse);

        if (currentCourseSection == null) {
            throw new ContentNotFoundException("course section requested not found or is not belong to current course");
        }

        // this.courseService.deleteCourseSection(currentCourseSection);
//        this.courseService.deleteCourseSection(
//                this.courseService.findCourseSectionById(currentCourseSection.getCourseSectionId())
//        );
        this.courseService.deleteCourseSectionById(
                currentCourseSection.getCourseSectionId(),
                currentCourseSection.getCourse()
        );

        return new ResponseEntity<>(new CourseSectionJSON(currentCourseSection), HttpStatus.OK);
    }
    @RequestMapping(value = "/course/{courseId}/sections/{sectionId}/resources", method = RequestMethod.POST)
    public HttpEntity<CourseSectionJSON> addResourcesToCourseSection(
            HttpServletRequest request,
            @PathVariable Integer courseId,
            @PathVariable Integer sectionId,
            @RequestBody int[] resourceIds
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = userService.findByUsername(auth.getName());
        int mCourseId = courseId;
        Course currentCourse = courseService.findById(mCourseId);
        if (!request.isUserInRole("ROLE_TEACHER") || !courseService.isUserOwnCourse(
                user, currentCourse
        )) {
            throw new UserNotAllowedException("User not in role or user not own the resource");
        }

        CourseSection currentCourseSection = this.courseService.findCourseSectionFromCourseById(sectionId, currentCourse);

        if (currentCourseSection == null) {
            throw new ContentNotFoundException("course section requested not found or is not belong to current course");
        }

        ArrayList<Resource> addResources = new ArrayList<>();
        boolean check = this.courseService.checkCourseContainResourcesByIds(currentCourse, resourceIds, addResources);

        if (!check) {
            throw new ContentNotFoundException("Resources not found in course resources");
        }


        currentCourseSection.getResources().addAll(addResources);
        CourseSection savedCourseSection = this.courseService.saveCourseSection(currentCourseSection);

        return new ResponseEntity<>(new CourseSectionJSON(savedCourseSection), HttpStatus.OK);
    }

    @RequestMapping(value = "/course/{courseId}/sections/{sectionId}/resources", method = RequestMethod.DELETE)
    public HttpEntity<CourseSectionJSON> deleteResourcesFromCourseSection(
            HttpServletRequest request,
            @PathVariable Integer courseId,
            @PathVariable Integer sectionId,
            @RequestBody int[] resourceIds
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = userService.findByUsername(auth.getName());
        int mCourseId = courseId;
        Course currentCourse = courseService.findById(mCourseId);
        if (!request.isUserInRole("ROLE_TEACHER") || !courseService.isUserOwnCourse(
                user, currentCourse
        )) {
            throw new UserNotAllowedException("User not in role or user not own the resource");
        }

        CourseSection currentCourseSection = this.courseService.findCourseSectionFromCourseById(sectionId, currentCourse);

        if (currentCourseSection == null) {
            throw new ContentNotFoundException("course section requested not found or is not belong to current course");
        }

        boolean check = this.courseService.checkCourseSectionContainResourcesByIds(currentCourse, resourceIds);

        if (!check) {
            throw new ContentNotFoundException("Resources not found in course resources");
        }

        CourseSection savedCourseSection = this.courseService.deleteResourcesFromCourseSection(
                currentCourseSection,
                resourceIds
        );

        return new ResponseEntity<>(new CourseSectionJSON(savedCourseSection), HttpStatus.OK);
    }


    @RequestMapping(value = "/course/{courseId}/resource/{resourceId}", method = RequestMethod.DELETE)
    public void deleteResource(
            HttpServletRequest request,
            @PathVariable String courseId,
            @PathVariable Integer resourceId
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = userService.findByUsername(auth.getName());
        int mCourseId = Integer.parseInt(courseId);
        Course currentCourse = courseService.findById(mCourseId);
        if (!request.isUserInRole("ROLE_TEACHER") || !courseService.isUserOwnCourse(
                user, currentCourse
        )) {
            throw new UserNotAllowedException("User not in role or user not own the resource");
        }

        Resource resourceToDelete = resourceService.findByResourceId(resourceId);

        if (resourceToDelete == null) {
            throw new ContentNotFoundException("Resource not found");
        } else {
            resourceService.delete(resourceToDelete);
        }
    }

    @RequestMapping(value = "/course/{courseId}/resource", method = RequestMethod.POST)
    public ResponseEntity<? extends AbstractResourceJSON> createResource(
            HttpServletRequest request,
            @PathVariable String courseId,
            @RequestBody AbstractResourceJSON resource
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = userService.findByUsername(auth.getName());
        int mCourseId = Integer.parseInt(courseId);
        Course currentCourse = courseService.findById(mCourseId);
        if (!request.isUserInRole("ROLE_TEACHER") || !courseService.isUserOwnCourse(
                user, currentCourse
        )) {
            throw new UserNotAllowedException("User not in role or user not own the resource");
        }

        // now we save the resource
        Resource savedResource = resourceService.saveResourceJSON(resource, currentCourse);
        return this.getResponseEntity(savedResource);
    }

    @RequestMapping(value = "/course/{courseId}/resource/{resourceId}", method = RequestMethod.GET)
    public ResponseEntity<? extends AbstractResourceJSON> getResource(
            HttpServletRequest request,
            @PathVariable Integer courseId,
            @PathVariable Integer resourceId
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = userService.findByUsername(auth.getName());
        Course currentCourse = courseService.findById(courseId);
        Resource resource = resourceService.findByResourceId(resourceId);

        if (!courseService.isUserOwnCourse(user, currentCourse) || !courseService.isUserEnrolledInCourse(user, currentCourse)) {
            throw new UserNotAllowedException("User doesnt own or not enrolled to this course");
        }

        Resource receivedResource = resourceService.findByResourceId(resourceId);
        return getResponseEntity(receivedResource);
    }

    @RequestMapping(value = "/course/{courseId}/resource/{resourceId}", method = RequestMethod.PUT)
    public ResponseEntity<? extends AbstractResourceJSON> updateResource(
            HttpServletRequest request,
            @PathVariable Integer courseId,
            @PathVariable Integer resourceId,
            @RequestBody AbstractResourceJSON resourceJSON
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = userService.findByUsername(auth.getName());
        Course currentCourse = courseService.findById(courseId);
        Resource resource = resourceService.findByResourceId(resourceId);

        if (!courseService.isUserOwnCourse(user, currentCourse)) {
            throw new UserNotAllowedException("User doesnt own this course");
        }

        if (!courseService.courseHasResource(currentCourse, resource)) {
            // TODO: Create new exception type for this one
            throw new UserNotAllowedException("Resource not in course");
        }

        resourceService.editResource(resourceJSON, resource);
        Resource editedResource = resourceService.save(resource);
        ResponseEntity<? extends AbstractResourceJSON> x = getResponseEntity(editedResource);
        if (x != null) return x;

        // TODO: Add an error response here

        return new ResponseEntity<>(resourceJSON, HttpStatus.OK);
    }

    private ResponseEntity<? extends AbstractResourceJSON> getResponseEntity(Resource resource) {
        switch (ResourceHelper.getResourceType(resource)) {
            case ResourceHelper.ResourceTypes.RESOURCE_MARKDOWN:
                return new ResponseEntity<MarkdownDocumentResourceJSON>(
                        new MarkdownDocumentResourceJSON((MarkdownDocumentResource) resource),
                        HttpStatus.OK
                );
            case ResourceHelper.ResourceTypes.RESOURCE_GUIDE:
                return new ResponseEntity<GuideResourceJSON>(
                        new GuideResourceJSON((GuideResource) resource),
                        HttpStatus.OK
                );
            case ResourceHelper.ResourceTypes.RESOURCE_CODE_SNIPPET:
                return new ResponseEntity<CodeSnippetResourceJSON>(
                        new CodeSnippetResourceJSON((CodeSnippetResource) resource),
                        HttpStatus.OK
                );
            case ResourceHelper.ResourceTypes.RESOURCE_REPOSITORY:
                return new ResponseEntity<RepositoryResourceJSON>(
                        new RepositoryResourceJSON((RepositoryResource) resource),
                        HttpStatus.OK
                );
            case ResourceHelper.ResourceTypes.RESOURCE_FILE:
                return new ResponseEntity<FileResourceJSON>(
                        new FileResourceJSON((FileResource) resource),
                        HttpStatus.OK
                );
            case ResourceHelper.ResourceTypes.RESOURCE_LINK:
                return new ResponseEntity<LinkResourceJSON>(
                        new LinkResourceJSON((LinkResource) resource),
                        HttpStatus.OK
                );
        }
        return null;
    }

    @RequestMapping(value = "/course/{courseId}", method = RequestMethod.PUT)
    public ResponseEntity<CourseJSON> updateCourse(
            @PathVariable Integer courseId,
            @RequestBody CourseJSON course,
            HttpServletRequest request
    ) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = userService.findByUsername(auth.getName());
        Course currentCourse = courseService.findById(courseId);
        if (!request.isUserInRole("ROLE_TEACHER") || !courseService.isUserOwnCourse(
                user, currentCourse
        )) {
            throw new UserNotAllowedException("User not in role or user not own the resource");
        }

        logger.info("Updating course with id = " + courseId);

        currentCourse.setTitle(course.getTitle());
        currentCourse.setDescription(course.getDescription());
        currentCourse.setSemester(course.getSemester());

        CourseJSON editedCourseJSON = new CourseJSON(courseService.saveCourse(currentCourse));

        return new ResponseEntity<CourseJSON>(editedCourseJSON, HttpStatus.OK);
    }

    @RequestMapping("/course/{courseId}/resources-all")
    @Transactional
    public DetailedResourcesCollectionJSON getAllCourseResources(
            HttpServletRequest request,
            @PathVariable String courseId,
            @RequestParam(name = "type", required = false) final String resourcesType
    ) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = userService.findByUsername(auth.getName());
        int mCourseId = Integer.parseInt(courseId);
        Course currentCourse = courseService.findById(mCourseId);
        if (courseService.isUserEnrolledInCourse(user, currentCourse) ||
            courseService.isUserOwnCourse(user, currentCourse)) {

            DetailedResourcesCollectionJSON resources;
            if (resourcesType == null || !ResourceHelper.isValidResourceTypeString(resourcesType)) {
                resources = ResourceHelper.getDetailedResourcesCollectionJSONfromResources(
                                currentCourse.getCourseResources()
                        );
            } else {
                resources = ResourceHelper.getDetailedResourcesCollectionJSONfromResources(
                                currentCourse.getCourseResources(),
                                resourcesType
                        );
            }
            return resources;
        } else {
            throw new UserNotAllowedException("user not enrolled to this course and not authorized");
        }
    }

    @RequestMapping(
            value = "/course/{courseId}/resource/{resourceId}/file"
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
        FileResource fileResource = this.resourceService.findFileResourceByResourceId(Integer.parseInt(resourceId));

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

    @RequestMapping("/demo-endpoint-1")
    public void getCourseSectionsDetailsDemo() {
        Course course = this.courseService.findById(1);
            List<CourseSection> courseSections = course.getCourseSections();
        // logger.info(courseSections.toString());
        for (CourseSection cs : courseSections) {
            if (cs.getResources() != null) {
                logger.info(cs.getResources().toString());
            }
        }
    }

}
