package com.alexc.acodelearn.resourceserver.json.resource;

import com.alexc.acodelearn.resourceserver.entity.Resource.CodeSnippetResource;
import com.alexc.acodelearn.resourceserver.util.ResourceHelper;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class CodeSnippetResourceJSON extends AbstractResourceJSON {

    private int resourceId;
    private String name;
    private Date dateCreated;
    private String courseName;
    private String resourceType;
    //
    private String snippetTitle;
    private String snippetDescription;
    private String snippetDocumentData;
    private String snippetLanguage;

    public CodeSnippetResourceJSON(CodeSnippetResource resource) {
        this.resourceId = resource.getResourceId();
        this.name = resource.getName();
        this.dateCreated = resource.getDateCreated();
        this.courseName = resource.getCourse().getTitle();
        this.resourceType = ResourceHelper.getResourceType(resource);
        //
        this.snippetTitle = resource.getSnippetTitle();
        this.snippetDescription = resource.getSnippetDescription();
        this.snippetDocumentData = resource.getSnippetDocumentData();
        this.snippetLanguage = resource.getSnippetLanguage();
    }

    @Override
    public String getType() {
        return this.resourceType;
    }
}
