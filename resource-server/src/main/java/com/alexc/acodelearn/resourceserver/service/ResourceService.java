package com.alexc.acodelearn.resourceserver.service;

import com.alexc.acodelearn.resourceserver.entity.Course;
import com.alexc.acodelearn.resourceserver.entity.Resource.FileResource;
import com.alexc.acodelearn.resourceserver.entity.Resource.Resource;
import com.alexc.acodelearn.resourceserver.repository.ResourceRepository;
import com.alexc.acodelearn.resourceserver.repository.resource.FileResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private FileResourceRepository fileResourceRepository;

    public Collection<Resource> findByCourse(Course course) {
        return resourceRepository.findByCourse(course);
    }

    public FileResource findByResourceId(Integer resourceId) {
        return fileResourceRepository.findByResourceId(resourceId);
    }

    public List<Resource> findAllById(Iterable<Integer> iterable) {
        return this.resourceRepository.findAllById(iterable);
    }

}
