package com.alexc.acodelearn.resourceserver.repository;

import com.alexc.acodelearn.resourceserver.entity.Course;
import com.alexc.acodelearn.resourceserver.entity.Resource.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface ResourceRepository extends JpaRepository<Resource, Long> {

    Collection<Resource> findByCourse(Course course);

}
