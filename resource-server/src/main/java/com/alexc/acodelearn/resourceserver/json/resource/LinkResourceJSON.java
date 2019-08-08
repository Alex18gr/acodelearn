package com.alexc.acodelearn.resourceserver.json.resource;

import com.alexc.acodelearn.resourceserver.entity.Resource.LinkResource;
import com.alexc.acodelearn.resourceserver.util.ResourceHelper;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class LinkResourceJSON extends AbstractResourceJSON {

    private int resourceId;
    private String name;
    private Date dateCreated;
    private String courseName;
    private String resourceType;
    //
    private String link;
    private String description;

    public LinkResourceJSON(LinkResource resource) {
        this.resourceId = resource.getResourceId();
        this.name = resource.getName();
        this.dateCreated = resource.getDateCreated();
        this.courseName = resource.getCourse().getTitle();
        this.resourceType = ResourceHelper.getResourceType(resource);
        //
        this.link = resource.getLink();
        this.description = resource.getDescription();

    }

    @Override
    public String getType() {
        return this.resourceType;
    }
}
