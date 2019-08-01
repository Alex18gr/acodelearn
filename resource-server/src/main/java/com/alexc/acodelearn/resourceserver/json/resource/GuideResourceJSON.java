package com.alexc.acodelearn.resourceserver.json.resource;

import com.alexc.acodelearn.resourceserver.entity.Resource.GuideResource;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class GuideResourceJSON {

    private int resourceId;
    private String resourceName;
    private Date dateCreated;
    private String courseName;
    //
    private String guideTitle;
    private String guideDescription;
    private String guideData;

    public GuideResourceJSON(GuideResource resource) {
        this.resourceId = resource.getResourceId();
        this.resourceName = resource.getName();
        this.dateCreated = resource.getDateCreated();
        this.courseName = resource.getCourse().getTitle();
        //
        this.guideTitle = resource.getGuideTitle();
        this.guideDescription = resource.getGuideDescription();
        this.guideData = resource.getGuideData();
    }

}
