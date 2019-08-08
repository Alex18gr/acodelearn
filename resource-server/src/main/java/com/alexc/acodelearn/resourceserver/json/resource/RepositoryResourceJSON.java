package com.alexc.acodelearn.resourceserver.json.resource;

import com.alexc.acodelearn.resourceserver.entity.Resource.RepositoryResource;
import com.alexc.acodelearn.resourceserver.util.ResourceHelper;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class RepositoryResourceJSON extends AbstractResourceJSON {

    private int resourceId;
    private String name;
    private Date dateCreated;
    private String courseName;
    private String resourceType;
    //
    private String repoUrl;
    private String repoName;
    private String repoNameRepo;

    public RepositoryResourceJSON(RepositoryResource resource) {
        this.resourceId = resource.getResourceId();
        this.name = resource.getName();
        this.dateCreated = resource.getDateCreated();
        this.courseName = resource.getCourse().getTitle();
        this.resourceType = ResourceHelper.getResourceType(resource);
        this.repoUrl = resource.getRepoUrl();
        this.repoName = resource.getRepoName();
        this.repoNameRepo = resource.getRepoNameRepo();
    }

    @Override
    public String getType() {
        return this.resourceType;
    }
}
