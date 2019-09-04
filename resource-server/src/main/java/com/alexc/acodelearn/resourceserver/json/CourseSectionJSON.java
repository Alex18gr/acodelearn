package com.alexc.acodelearn.resourceserver.json;

import com.alexc.acodelearn.resourceserver.entity.Course;
import com.alexc.acodelearn.resourceserver.entity.CourseSection;
import com.alexc.acodelearn.resourceserver.entity.Resource.Resource;
import com.alexc.acodelearn.resourceserver.util.ResourceHelper;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
public class CourseSectionJSON {

    private int courseSectionId;
    private String name;
    private String description;
    private Integer order;
    private Date dateCreated;
    private CourseJSON course;
    private DetailedResourcesCollectionJSON resources;

    public CourseSectionJSON(CourseSection cs, Course course, List<Resource> resources) {
        this.courseSectionId = cs.getCourseSectionId().getCourseSectionId();
        this.name = cs.getName();
        this.description = cs.getDescription();
        this.order = cs.getOrder();
        this.dateCreated = cs.getDateCreated();
        this.course = new CourseJSON(course);
        this.resources = ResourceHelper.getDetailedResourcesCollectionJSONfromResources(resources);
    }

    public CourseSectionJSON(CourseSection cs) {
        this.courseSectionId = cs.getCourseSectionId().getCourseSectionId();
        this.name = cs.getName();
        this.description = cs.getDescription();
        this.order = cs.getOrder();
        this.dateCreated = cs.getDateCreated();
        this.course = new CourseJSON(cs.getCourse());
        this.resources = ResourceHelper.getDetailedResourcesCollectionJSONfromResources(cs.getResources());
    }

}
