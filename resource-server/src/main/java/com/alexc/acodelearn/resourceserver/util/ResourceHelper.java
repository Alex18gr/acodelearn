package com.alexc.acodelearn.resourceserver.util;

import com.alexc.acodelearn.resourceserver.entity.Resource.Resource;
import com.alexc.acodelearn.resourceserver.json.ResourceJSON;
import com.alexc.acodelearn.resourceserver.json.ResourcesCollectionJSON;

import java.util.Collection;
import java.util.Date;

public class ResourceHelper {

    public final static String RES_FILE = "FileResource";
    public final static String RES_LINK = "LinkResource";
    public final static String RES_REPOSITORY = "RepositoryResource";

    public static class ResourceTypes {
        public static final String RESOURCE_LINK = "RESOURCE_LINK";
        public static final String RESOURCE_FILE = "RESOURCE_FILE";
        public static final String RESOURCE_REPOSITORY = "RESOURCE_REPOSITORY";
    }

    public static ResourcesCollectionJSON getResourcesCollectionJSONfromResources(Collection<Resource> resources) {

        ResourcesCollectionJSON.ResourcesTypeCollectionJSON linkResources =
                new ResourcesCollectionJSON.ResourcesTypeCollectionJSON(ResourceTypes.RESOURCE_LINK);
        ResourcesCollectionJSON.ResourcesTypeCollectionJSON fileResources =
                new ResourcesCollectionJSON.ResourcesTypeCollectionJSON(ResourceTypes.RESOURCE_FILE);
        ResourcesCollectionJSON.ResourcesTypeCollectionJSON repositoryResources =
                new ResourcesCollectionJSON.ResourcesTypeCollectionJSON(ResourceTypes.RESOURCE_REPOSITORY);

        for (Resource resource : resources) {
            ResourceJSON resourceJSON = new ResourceJSON();
            resourceJSON.setName(resource.getName());
            resourceJSON.setResourceId(resource.getResourceId());
            resourceJSON.setDateCreated(resource.getDateCreated());

            switch (resource.getClass().getSimpleName()) {
                case RES_LINK:
                    resourceJSON.setResourceType(ResourceTypes.RESOURCE_LINK);
                    linkResources.addResource(resourceJSON);
                    break;
                case RES_FILE:
                    resourceJSON.setResourceType(ResourceTypes.RESOURCE_FILE);
                    fileResources.addResource(resourceJSON);
                    break;
                case RES_REPOSITORY:
                    resourceJSON.setResourceType(ResourceTypes.RESOURCE_REPOSITORY);
                    repositoryResources.addResource(resourceJSON);
                    break;
            }
        }

        ResourcesCollectionJSON resourcesCollectionJSON = new ResourcesCollectionJSON(new Date());
        resourcesCollectionJSON.addResourcesCollectionJSON(linkResources);
        resourcesCollectionJSON.addResourcesCollectionJSON(fileResources);
        resourcesCollectionJSON.addResourcesCollectionJSON(repositoryResources);

        return resourcesCollectionJSON;
    }

}
