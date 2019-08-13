package com.alexc.acodelearn.resourceserver.util;

import com.alexc.acodelearn.resourceserver.entity.Resource.*;
import com.alexc.acodelearn.resourceserver.json.DetailedResourcesCollectionJSON;
import com.alexc.acodelearn.resourceserver.json.ResourceJSON;
import com.alexc.acodelearn.resourceserver.json.ResourcesCollectionJSON;
import com.alexc.acodelearn.resourceserver.json.resource.*;
import com.alexc.acodelearn.resourceserver.rest.FileStorageException;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;

public class ResourceHelper {

    public final static String RES_FILE = "FileResource";
    public final static String RES_LINK = "LinkResource";
    public final static String RES_REPOSITORY = "RepositoryResource";
    public final static String RES_CODE_SNIPPET = "CodeSnippetResource";
    public final static String RES_MD = "MarkdownDocumentResource";
    public final static String RES_GUIDE = "GuideResource";

    public static Resource getResourceFormResourceJSON(AbstractResourceJSON resourceJSON) {
        switch (resourceJSON.getType()) {
            case ResourceHelper.ResourceTypes.RESOURCE_LINK: {
                LinkResource resource = new LinkResource();
                LinkResourceJSON res = (LinkResourceJSON) resourceJSON;

                resource.setName(res.getName());
                resource.setDateCreated(res.getDateCreated());
                //
                ((LinkResource) resource).setDescription(res.getDescription());
                ((LinkResource) resource).setLink(res.getLink());
                return resource;
            }
            case ResourceHelper.ResourceTypes.RESOURCE_FILE: {
                FileResource resource = new FileResource();
                FileResourceJSON res = (FileResourceJSON) resourceJSON;

                resource.setName(res.getName());
                resource.setDateCreated(res.getDateCreated());
                //
                ((FileResource) resource).setFileName(res.getName());
                ((FileResource) resource).setSummary(res.getSummary());
                return resource;
            }
            case ResourceHelper.ResourceTypes.RESOURCE_REPOSITORY: {
                RepositoryResource resource = new RepositoryResource();
                RepositoryResourceJSON res = (RepositoryResourceJSON) resourceJSON;

                resource.setName(res.getName());
                resource.setDateCreated(res.getDateCreated());
                //
                ((RepositoryResource) resource).setRepoName(res.getRepoName());
                ((RepositoryResource) resource).setRepoUrl(res.getRepoUrl());
                ((RepositoryResource) resource).setRepoNameRepo(res.getRepoNameRepo());
                return resource;
            }
            case ResourceHelper.ResourceTypes.RESOURCE_CODE_SNIPPET: {
                CodeSnippetResource resource = new CodeSnippetResource();
                CodeSnippetResourceJSON res = (CodeSnippetResourceJSON) resourceJSON;

                resource.setName(res.getName());
                resource.setDateCreated(res.getDateCreated());
                //
                ((CodeSnippetResource) resource).setSnippetDescription(res.getSnippetDescription());
                ((CodeSnippetResource) resource).setSnippetDocumentData(res.getSnippetDocumentData());
                ((CodeSnippetResource) resource).setSnippetLanguage(res.getSnippetLanguage());
                ((CodeSnippetResource) resource).setSnippetTitle(res.getSnippetTitle());
                return resource;
            }
            case ResourceHelper.ResourceTypes.RESOURCE_GUIDE: {
                GuideResource resource = new GuideResource();
                GuideResourceJSON res = (GuideResourceJSON) resourceJSON;

                resource.setName(res.getName());
                resource.setDateCreated(res.getDateCreated());
                //
                ((GuideResource) resource).setGuideData(res.getGuideData());
                ((GuideResource) resource).setGuideDescription(res.getGuideDescription());
                ((GuideResource) resource).setGuideTitle(res.getGuideTitle());
                return resource;
            }
            case ResourceHelper.ResourceTypes.RESOURCE_MARKDOWN: {
                MarkdownDocumentResource resource = new MarkdownDocumentResource();
                MarkdownDocumentResourceJSON res = (MarkdownDocumentResourceJSON) resourceJSON;

                resource.setName(res.getName());
                resource.setDateCreated(res.getDateCreated());
                //
                ((MarkdownDocumentResource) resource).setDescription(res.getDescription());
                ((MarkdownDocumentResource) resource).setDocumentTitle(res.getDocumentTitle());
                ((MarkdownDocumentResource) resource).setMarkdownDocumentData(res.getMarkdownDocumentData());
                return resource;
            }
        }
        return null;
    }

    public static class ResourceTypes {
        public static final String RESOURCE_LINK = "RESOURCE_LINK";
        public static final String RESOURCE_FILE = "RESOURCE_FILE";
        public static final String RESOURCE_REPOSITORY = "RESOURCE_REPOSITORY";
        public static final String RESOURCE_CODE_SNIPPET = "RESOURCE_CODE_SNIPPET";
        public static final String RESOURCE_MARKDOWN = "RESOURCE_MARKDOWN";
        public static final String RESOURCE_GUIDE = "RESOURCE_GUIDE";
    }

    public static DetailedResourcesCollectionJSON getDetailedResourcesCollectionJSONfromResources(Collection<Resource> resources) {

        DetailedResourcesCollectionJSON collectionJSON = new DetailedResourcesCollectionJSON(new Date());

        for (Resource resource : resources) {

            switch (resource.getClass().getSimpleName()) {
                case RES_LINK:
                    collectionJSON.getResources().addLinkResource(new LinkResourceJSON((LinkResource) resource));
                    break;
                case RES_FILE:
                    collectionJSON.getResources().addFileResource(new FileResourceJSON((FileResource) resource));
                    break;
                case RES_REPOSITORY:
                    collectionJSON.getResources()
                            .addRepositoryResource(new RepositoryResourceJSON((RepositoryResource) resource));
                    break;
                case RES_CODE_SNIPPET:
                    collectionJSON.getResources()
                            .addCodeSnippetResource(new CodeSnippetResourceJSON((CodeSnippetResource) resource));
                    break;
                case RES_MD:
                    collectionJSON.getResources()
                            .addMarkdownResource(new MarkdownDocumentResourceJSON((MarkdownDocumentResource) resource));
                    break;
                case RES_GUIDE:
                    collectionJSON.getResources()
                            .addGuideResource(new GuideResourceJSON((GuideResource) resource));
                    break;
            }

        }

        return collectionJSON;
    }

    public static ResourcesCollectionJSON getResourcesCollectionJSONfromResources(Collection<Resource> resources) {

        ResourcesCollectionJSON.ResourcesTypeCollectionJSON linkResources =
                new ResourcesCollectionJSON.ResourcesTypeCollectionJSON(ResourceTypes.RESOURCE_LINK);
        ResourcesCollectionJSON.ResourcesTypeCollectionJSON fileResources =
                new ResourcesCollectionJSON.ResourcesTypeCollectionJSON(ResourceTypes.RESOURCE_FILE);
        ResourcesCollectionJSON.ResourcesTypeCollectionJSON repositoryResources =
                new ResourcesCollectionJSON.ResourcesTypeCollectionJSON(ResourceTypes.RESOURCE_REPOSITORY);
        ResourcesCollectionJSON.ResourcesTypeCollectionJSON codeSnippetResources =
                new ResourcesCollectionJSON.ResourcesTypeCollectionJSON(ResourceTypes.RESOURCE_CODE_SNIPPET);
        ResourcesCollectionJSON.ResourcesTypeCollectionJSON markdownResources =
                new ResourcesCollectionJSON.ResourcesTypeCollectionJSON(ResourceTypes.RESOURCE_MARKDOWN);
        ResourcesCollectionJSON.ResourcesTypeCollectionJSON guideResources =
                new ResourcesCollectionJSON.ResourcesTypeCollectionJSON(ResourceTypes.RESOURCE_GUIDE);

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
                case RES_CODE_SNIPPET:
                    resourceJSON.setResourceType(ResourceTypes.RESOURCE_CODE_SNIPPET);
                    repositoryResources.addResource(resourceJSON);
                    break;
                case RES_MD:
                    resourceJSON.setResourceType(ResourceTypes.RESOURCE_MARKDOWN);
                    repositoryResources.addResource(resourceJSON);
                    break;
                case RES_GUIDE:
                    resourceJSON.setResourceType(ResourceTypes.RESOURCE_GUIDE);
                    repositoryResources.addResource(resourceJSON);
                    break;
            }
        }

        ResourcesCollectionJSON resourcesCollectionJSON = new ResourcesCollectionJSON(new Date());
        resourcesCollectionJSON.addResourcesCollectionJSON(linkResources);
        resourcesCollectionJSON.addResourcesCollectionJSON(fileResources);
        resourcesCollectionJSON.addResourcesCollectionJSON(repositoryResources);
        resourcesCollectionJSON.addResourcesCollectionJSON(codeSnippetResources);
        resourcesCollectionJSON.addResourcesCollectionJSON(markdownResources);
        resourcesCollectionJSON.addResourcesCollectionJSON(guideResources);

        return resourcesCollectionJSON;
    }

    public static String getResourceType(Resource resource) {
        switch (resource.getClass().getSimpleName()) {
            case RES_LINK:
                return ResourceTypes.RESOURCE_LINK;
            case RES_FILE:
                return ResourceTypes.RESOURCE_FILE;
            case RES_REPOSITORY:
                return ResourceTypes.RESOURCE_REPOSITORY;
            case RES_CODE_SNIPPET:
                return ResourceTypes.RESOURCE_CODE_SNIPPET;
            case RES_MD:
                return ResourceTypes.RESOURCE_MARKDOWN;
            case RES_GUIDE:
                return ResourceTypes.RESOURCE_GUIDE;
            default:
                return "RESOURCE";
        }
    }

    public static boolean resourcesSameType(Resource res1, Resource res2) {
        return getResourceType(res1).equals(getResourceType(res2));
    }

    public static FileResource getFileResource(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {

            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            FileResource fileResource = new FileResource();
            fileResource.setFileType(file.getContentType());
            fileResource.setFileName(fileName);
            fileResource.setFileData(file.getBytes());
            return fileResource;
        } catch (IOException e) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", e);
        }
    }
}
