package com.alexc.acodelearn.resourceserver.json.resource;

import com.alexc.acodelearn.resourceserver.entity.Resource.FileResource;
import com.alexc.acodelearn.resourceserver.util.ResourceHelper;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class FileResourceJSON extends AbstractResourceJSON {

    private int resourceId;
    private String name;
    private Date dateCreated;
    private String courseName;
    private String resourceType;
    //
    private String fileName;
    private String fileType;
    private String summary;

    public FileResourceJSON(FileResource resource) {
        this.resourceId = resource.getResourceId();
        this.name = resource.getName();
        this.dateCreated = resource.getDateCreated();
        this.courseName = resource.getCourse().getTitle();
        this.resourceType = ResourceHelper.getResourceType(resource);
        //
        this.fileName = resource.getFileName();
        this.fileType = resource.getFileType();
        this.summary = resource.getSummary();
    }

    @Override
    public String getType() {
        return this.resourceType;
    }

}
