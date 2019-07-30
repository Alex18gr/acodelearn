package com.alexc.acodelearn.resourceserver.repository;

import com.alexc.acodelearn.resourceserver.entity.Course;
import com.alexc.acodelearn.resourceserver.entity.Resource.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

@Repository
@Transactional
public interface ResourceRepository extends JpaRepository<Resource, Integer> {

    Collection<Resource> findByCourse(Course course);

    @Override
    List<Resource> findAllById(Iterable<Integer> iterable);
}
