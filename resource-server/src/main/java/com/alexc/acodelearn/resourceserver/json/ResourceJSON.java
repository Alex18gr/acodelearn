package com.alexc.acodelearn.resourceserver.json;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class ResourceJSON {

    private int resourceId;
    private String name;
    private Date dateCreated;
    private String resourceType;

}
