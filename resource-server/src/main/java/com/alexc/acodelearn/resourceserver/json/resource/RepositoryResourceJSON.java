package com.alexc.acodelearn.resourceserver.json.resource;

import com.alexc.acodelearn.resourceserver.entity.Resource.RepositoryResource;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class RepositoryResourceJSON {

    private int resourceId;
    private String resourceName;
    private Date dateCreated;
    private String courseName;
    //
    private String repoUrl;
    private String repoName;
    private String repoNameRepo;

    public RepositoryResourceJSON(RepositoryResource resource) {
        this.resourceId = resource.getResourceId();
        this.resourceName = resource.getName();
        this.dateCreated = resource.getDateCreated();
        this.courseName = resource.getCourse().getTitle();
        this.repoUrl = resource.getRepoUrl();
        this.repoName = resource.getRepoName();
        this.repoNameRepo = resource.getRepoNameRepo();
    }
}
