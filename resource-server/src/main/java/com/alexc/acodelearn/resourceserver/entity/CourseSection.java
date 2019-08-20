package com.alexc.acodelearn.resourceserver.entity;

import com.alexc.acodelearn.resourceserver.entity.Resource.Resource;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity @Data
@Table(name = "course_section")
public class CourseSection implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_course_section")
    private int courseSectionId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "order")
    private Integer order;

    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    protected Date dateCreated = new Date();

    @ManyToOne
    @JoinColumn(name="course_id", nullable=false)
    private Course course;

    @ManyToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH, CascadeType.REMOVE })
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
