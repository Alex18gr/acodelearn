package com.alexc.acodelearn.resourceserver.json.resource;

import com.alexc.acodelearn.resourceserver.entity.Resource.LinkResource;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class LinkResourceJSON {

    private int resourceId;
    private String resourceName;
    private Date dateCreated;
    private String courseName;
    //
    private String link;
    private String description;

    public LinkResourceJSON(LinkResource resource) {
        this.resourceId = resource.getResourceId();
        this.resourceName = resource.getName();
        this.dateCreated = resource.getDateCreated();
        this.courseName = resource.getCourse().getTitle();
        this.link = resource.getLink();
        this.description = resource.getDescription();

    }
}
