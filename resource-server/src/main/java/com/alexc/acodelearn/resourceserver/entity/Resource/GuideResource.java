package com.alexc.acodelearn.resourceserver.entity.Resource;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@NoArgsConstructor
@Table(name = "guide_resource")
public class GuideResource extends Resource {

    @Column(name = "guide_title")
    private String guideTitle;

    @Column(name = "guide_description")
    private String guideDescription;

    @Column(name = "guide_data")
    private String guideData;

}
