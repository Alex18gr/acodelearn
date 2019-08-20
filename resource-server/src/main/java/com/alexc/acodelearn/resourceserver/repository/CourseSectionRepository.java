package com.alexc.acodelearn.resourceserver.repository;

import com.alexc.acodelearn.resourceserver.entity.CourseSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface CourseSectionRepository extends JpaRepository<CourseSection, Integer> {

    @Override
    <S extends CourseSection> S save(S s);

    @Override
    Optional<CourseSection> findById(Integer integer);
}
