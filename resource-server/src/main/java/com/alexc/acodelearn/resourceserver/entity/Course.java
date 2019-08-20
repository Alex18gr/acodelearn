package com.alexc.acodelearn.resourceserver.entity;

import com.alexc.acodelearn.resourceserver.entity.Resource.Resource;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.jackson.JsonComponent;

import javax.persistence.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Table(name = "course")
// @JsonComponent
public class Course implements Serializable {

    @Transient
    private static final Logger logger = LoggerFactory.getLogger(Course.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int Id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "section_name")
    private String sectionName = "Section";

    @Column(name = "semester")
    private Integer semester;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY,
    cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(
            name = "course_has_user",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> studentsEnrolled;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(
            name = "user_has_course",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> instructors;

    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "course",
            cascade = CascadeType.ALL

    )
    private List<Resource> courseResources;

    @Override
    public String toString() {
        return "Course{" +
                "Id=" + Id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", sectionName='" + sectionName + '\'' +
                '}';
    }

    public static class CourseJsonSerializer extends JsonSerializer<Course> {
        @Override
        public void serialize(Course course, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
            jgen.writeStartObject();
            jgen.writeNumber(course.getId());
            jgen.writeString(course.getTitle());
            jgen.writeEndObject();
        }
    }

    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "course",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<CourseSection> courseSections;

}
