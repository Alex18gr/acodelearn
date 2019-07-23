package com.alexc.acodelearn.resourceserver.json;

import com.alexc.acodelearn.resourceserver.entity.Course;
import com.alexc.acodelearn.resourceserver.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Data
@NoArgsConstructor
public class CourseJSON {

    private int Id;
    private String title;
    private String description;
    private Integer semester;
    private Collection<UserJSON> instructors;

    public CourseJSON(Course course) {
        this.Id = course.getId();
        this.title = course.getTitle();
        this.description = course.getDescription();
        this.semester = course.getSemester();
        this.instructors = new ArrayList<>();
        if (course.getInstructors() != null) {
            for (User user : course.getInstructors())
                instructors.add(new UserJSON(user));
        }
    }

    @Data
    @NoArgsConstructor
    public static class CourseListJSON {

        private Date timestamp;
        private Collection<CourseJSON> courseList;

        public CourseListJSON(Collection<CourseJSON> courses) {
            this.timestamp = new Date();
            this.courseList = courses;
        }

    }

}
