package com.alexc.acodelearn.resourceserver.json.resource;

import com.alexc.acodelearn.resourceserver.entity.Resource.MarkdownDocumentResource;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class MarkdownDocumentResourceJSON {

    private int resourceId;
    private String resourceName;
    private Date dateCreated;
    private String courseName;
    //
    private String documentTitle;
    private String description;
    private String markdownDocumentData;

    public MarkdownDocumentResourceJSON(MarkdownDocumentResource resource) {
        this.resourceId = resource.getResourceId();
        this.resourceName = resource.getName();
        this.dateCreated = resource.getDateCreated();
        this.courseName = resource.getCourse().getTitle();
        //
        this.documentTitle = resource.getDocumentTitle();
        this.description = resource.getDescription();
        this.markdownDocumentData = resource.getMarkdownDocumentData();
    }

}
