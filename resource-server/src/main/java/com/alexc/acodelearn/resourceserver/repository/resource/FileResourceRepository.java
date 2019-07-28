package com.alexc.acodelearn.resourceserver.repository.resource;

import com.alexc.acodelearn.resourceserver.entity.Resource.FileResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface FileResourceRepository extends JpaRepository<FileResource, Long> {

    FileResource findByResourceId(Integer resourceId);
}
