package com.alexc.acodelearn.resourceserver.json.resource;

import com.alexc.acodelearn.resourceserver.entity.Resource.MarkdownDocumentResource;
import com.alexc.acodelearn.resourceserver.util.ResourceHelper;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class MarkdownDocumentResourceJSON extends AbstractResourceJSON {

    private int resourceId;
    private String name;
    private Date dateCreated;
    private String courseName;
    private String resourceType;
    //
    private String documentTitle;
    private String description;
    private String markdownDocumentData;

    public MarkdownDocumentResourceJSON(MarkdownDocumentResource resource) {
        this.resourceId = resource.getResourceId();
        this.name = resource.getName();
        this.dateCreated = resource.getDateCreated();
        this.courseName = resource.getCourse().getTitle();
        this.resourceType = ResourceHelper.getResourceType(resource);
        //
        this.documentTitle = resource.getDocumentTitle();
        this.description = resource.getDescription();
        this.markdownDocumentData = resource.getMarkdownDocumentData();
    }

    @Override
    public String getType() {
        return this.resourceType;
    }

}
