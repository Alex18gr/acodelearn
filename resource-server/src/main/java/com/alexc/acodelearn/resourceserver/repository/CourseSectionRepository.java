package com.alexc.acodelearn.resourceserver.repository;

import com.alexc.acodelearn.resourceserver.entity.Course;
import com.alexc.acodelearn.resourceserver.entity.CourseSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
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

    @Override
    void delete(CourseSection courseSection);

    @Override
    void deleteById(Integer integer);

    void removeByCourseSectionId(Integer courseSectionId);

    @Modifying
    @Query(value="delete from CourseSection cs where cs.courseSectionId = ?1")
    void deleteByCourseSectionId(CourseSection.CourseSectionId id, Course course);

    void deleteByCourseSectionIdAndCourse(int courseSectionId, Course course);
}
