package com.alexc.acodelearn.resourceserver.json;

import com.alexc.acodelearn.resourceserver.entity.Resource.Resource;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Data
@NoArgsConstructor
public class ResourcesCollectionJSON {

    private Date timestamp;
    private Collection<ResourcesTypeCollectionJSON> resources;

    public ResourcesCollectionJSON(Date timestamp) {
        this.timestamp = timestamp;
        resources = new ArrayList<>();
    }

    public void addResourcesCollectionJSON(ResourcesTypeCollectionJSON resourcesCollectionJSON) {
        this.resources.add(resourcesCollectionJSON);
    }

    @Data
    public static class ResourcesTypeCollectionJSON {

        private String resourcesType;
        private Collection<ResourceJSON> resources;

        public ResourcesTypeCollectionJSON(String type) {
            this.resourcesType = type;
            this.resources = new ArrayList<>();
        }

        public void addResource(ResourceJSON resource) {
            this.resources.add(resource);
        }

    }

//    @Data
//    @NoArgsConstructor
//    public static class AllResourcesCollectionJSON {
//
//        private Date timestamp;
//        private Collection<ResourcesCollectionJSON> resources;
//
//    }
}
