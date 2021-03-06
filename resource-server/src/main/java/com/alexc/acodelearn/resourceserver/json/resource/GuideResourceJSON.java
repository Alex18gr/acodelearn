package com.alexc.acodelearn.resourceserver.json.resource;

import com.alexc.acodelearn.resourceserver.entity.Resource.GuideResource;
import com.alexc.acodelearn.resourceserver.util.ResourceHelper;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class GuideResourceJSON extends AbstractResourceJSON {

    private int resourceId;
    private String name;
    private Date dateCreated;
    private String courseName;
    private String resourceType;
    //
    private String guideTitle;
    private String guideDescription;
    private String guideData;

    public GuideResourceJSON(GuideResource resource) {
        this.resourceId = resource.getResourceId();
        this.name = resource.getName();
        this.dateCreated = resource.getDateCreated();
        this.courseName = resource.getCourse().getTitle();
        this.resourceType = ResourceHelper.getResourceType(resource);
        //
        this.guideTitle = resource.getGuideTitle();
        this.guideDescription = resource.getGuideDescription();
        this.guideData = resource.getGuideData();
    }

    @Override
    public String getType() {
        return this.resourceType;
    }

}
