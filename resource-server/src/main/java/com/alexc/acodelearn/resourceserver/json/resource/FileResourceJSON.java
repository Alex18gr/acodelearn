package com.alexc.acodelearn.resourceserver.json.resource;

import com.alexc.acodelearn.resourceserver.entity.Resource.FileResource;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class FileResourceJSON {

    private int resourceId;
    private String resourceName;
    private Date dateCreated;
    private String courseName;
    //
    private String fileName;
    private String fileType;
    private String summary;

    public FileResourceJSON(FileResource resource) {
        this.resourceId = resource.getResourceId();
        this.resourceName = resource.getName();
        this.dateCreated = resource.getDateCreated();
        this.courseName = resource.getCourse().getTitle();
        this.fileName = resource.getFileName();
        this.fileType = resource.getFileType();
        this.summary = resource.getSummary();
    }

}
