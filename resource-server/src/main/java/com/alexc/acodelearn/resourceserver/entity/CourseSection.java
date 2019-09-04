package com.alexc.acodelearn.resourceserver.entity;

import com.alexc.acodelearn.resourceserver.entity.Resource.Resource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity @Data
@Table(name = "course_section")
public class CourseSection implements Serializable {

    @Embeddable
    @Data @NoArgsConstructor
    public static class CourseSectionId implements Serializable {

        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id_course_section")
        private int courseSectionId;

        @Column(name="course_id", nullable = false)
        private int courseId;
    }

    @EmbeddedId
    private CourseSectionId courseSectionId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "section_order")
    private Integer order;

    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    protected Date dateCreated = new Date();

    @ManyToOne
    @MapsId(value = "employeeKey")
    private Course course;

    @ManyToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH })
    @JoinTable(
            name = "course_section_has_resource",
            joinColumns = {
                    @JoinColumn(name = "course_section_id_course_section", referencedColumnName = "id_course_section"),
                    @JoinColumn(name = "course_section_course_id", referencedColumnName = "course_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "resource_id", referencedColumnName = "id"),
                    @JoinColumn(name = "resource_course_id", referencedColumnName = "course_id")
            }
    )
    private List<Resource> resources;

}
