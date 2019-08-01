package com.alexc.acodelearn.resourceserver.json.resource;

import com.alexc.acodelearn.resourceserver.entity.Resource.CodeSnippetResource;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class CodeSnippetResourceJSON {

    private int resourceId;
    private String resourceName;
    private Date dateCreated;
    private String courseName;
    //
    private String snippetTitle;
    private String snippetDescription;
    private String snippetDocumentData;
    private String snippetLanguage;

    public CodeSnippetResourceJSON(CodeSnippetResource resource) {
        this.resourceId = resource.getResourceId();
        this.resourceName = resource.getName();
        this.dateCreated = resource.getDateCreated();
        this.courseName = resource.getCourse().getTitle();
        //
        this.snippetTitle = resource.getSnippetTitle();
        this.snippetDescription = resource.getSnippetDescription();
        this.snippetDocumentData = resource.getSnippetDocumentData();
        this.snippetLanguage = resource.getSnippetLanguage();
    }

}
