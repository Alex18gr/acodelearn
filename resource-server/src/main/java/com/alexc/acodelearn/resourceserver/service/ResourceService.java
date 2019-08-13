package com.alexc.acodelearn.resourceserver.service;

import com.alexc.acodelearn.resourceserver.entity.Course;
import com.alexc.acodelearn.resourceserver.entity.Resource.*;
import com.alexc.acodelearn.resourceserver.json.ResourceJSON;
import com.alexc.acodelearn.resourceserver.json.resource.*;
import com.alexc.acodelearn.resourceserver.repository.ResourceRepository;
import com.alexc.acodelearn.resourceserver.repository.resource.FileResourceRepository;
import com.alexc.acodelearn.resourceserver.util.ResourceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private FileResourceRepository fileResourceRepository;

    public Collection<Resource> findByCourse(Course course) {
        return resourceRepository.findByCourse(course);
    }

    public FileResource findFileResourceByResourceId(Integer resourceId) {
        return fileResourceRepository.findByResourceId(resourceId);
    }

    public Resource findByResourceId(Integer resourceId) {
        return resourceRepository.findById(resourceId).get();
    }

    public List<Resource> findAllById(Iterable<Integer> iterable) {
        return this.resourceRepository.findAllById(iterable);
    }

    public Resource save(Resource r) {
        return resourceRepository.save(r);
    }

    public void delete(Resource r) {
        resourceRepository.delete(r);
    }

    public Resource saveResourceJSON(AbstractResourceJSON resourceJSON, Course course) {

        switch (resourceJSON.getType()) {
            case ResourceHelper.ResourceTypes.RESOURCE_LINK: {
                LinkResource resource = (LinkResource) ResourceHelper.getResourceFormResourceJSON(resourceJSON);
                resource.setCourse(course);
                return this.save(resource);
            }
            case ResourceHelper.ResourceTypes.RESOURCE_FILE: {
                // nothing happpens here...
                return null;
            }
            case ResourceHelper.ResourceTypes.RESOURCE_REPOSITORY: {
                RepositoryResource resource = (RepositoryResource) ResourceHelper.getResourceFormResourceJSON(resourceJSON);
                resource.setCourse(course);
                return this.save(resource);
            }
            case ResourceHelper.ResourceTypes.RESOURCE_CODE_SNIPPET: {
                CodeSnippetResource resource = (CodeSnippetResource) ResourceHelper.getResourceFormResourceJSON(resourceJSON);
                resource.setCourse(course);
                return this.save(resource);
            }
            case ResourceHelper.ResourceTypes.RESOURCE_MARKDOWN: {
                MarkdownDocumentResource resource = (MarkdownDocumentResource) ResourceHelper.getResourceFormResourceJSON(resourceJSON);
                resource.setCourse(course);
                return this.save(resource);
            }
            case ResourceHelper.ResourceTypes.RESOURCE_GUIDE: {
                GuideResource resource = (GuideResource) ResourceHelper.getResourceFormResourceJSON(resourceJSON);
                resource.setCourse(course);
                return this.save(resource);
            }
            default:
                return null;
        }
    }

    public void editResource(AbstractResourceJSON resourceJSON, Resource resourceToEdit) {

        switch (resourceJSON.getType()) {
            case ResourceHelper.ResourceTypes.RESOURCE_LINK: {
                LinkResourceJSON res = (LinkResourceJSON) resourceJSON;

                resourceToEdit.setName(res.getName());
                //
                ((LinkResource) resourceToEdit).setDescription(res.getDescription());
                ((LinkResource) resourceToEdit).setLink(res.getLink());
                break;
            }
            case ResourceHelper.ResourceTypes.RESOURCE_FILE: {
                FileResourceJSON res = (FileResourceJSON) resourceJSON;

                resourceToEdit.setName(res.getName());
                //
                ((FileResource) resourceToEdit).setFileName(res.getName());
                ((FileResource) resourceToEdit).setSummary(res.getSummary());
                break;
            }
            case ResourceHelper.ResourceTypes.RESOURCE_REPOSITORY: {
                RepositoryResourceJSON res = (RepositoryResourceJSON) resourceJSON;

                resourceToEdit.setName(res.getName());
                //
                ((RepositoryResource) resourceToEdit).setRepoName(res.getRepoName());
                ((RepositoryResource) resourceToEdit).setRepoUrl(res.getRepoUrl());
                ((RepositoryResource) resourceToEdit).setRepoNameRepo(res.getRepoNameRepo());
                break;
            }
            case ResourceHelper.ResourceTypes.RESOURCE_CODE_SNIPPET: {
                CodeSnippetResourceJSON res = (CodeSnippetResourceJSON) resourceJSON;

                resourceToEdit.setName(res.getName());
                //
                ((CodeSnippetResource) resourceToEdit).setSnippetDescription(res.getSnippetDescription());
                ((CodeSnippetResource) resourceToEdit).setSnippetDocumentData(res.getSnippetDocumentData());
                ((CodeSnippetResource) resourceToEdit).setSnippetLanguage(res.getSnippetLanguage());
                ((CodeSnippetResource) resourceToEdit).setSnippetTitle(res.getSnippetTitle());
                break;
            }
            case ResourceHelper.ResourceTypes.RESOURCE_GUIDE: {
                GuideResourceJSON res = (GuideResourceJSON) resourceJSON;

                resourceToEdit.setName(res.getName());
                //
                ((GuideResource) resourceToEdit).setGuideData(res.getGuideData());
                ((GuideResource) resourceToEdit).setGuideDescription(res.getGuideDescription());
                ((GuideResource) resourceToEdit).setGuideTitle(res.getGuideTitle());
                break;
            }
            case ResourceHelper.ResourceTypes.RESOURCE_MARKDOWN: {
                MarkdownDocumentResourceJSON res = (MarkdownDocumentResourceJSON) resourceJSON;

                resourceToEdit.setName(res.getName());
                //
                ((MarkdownDocumentResource) resourceToEdit).setDescription(res.getDescription());
                ((MarkdownDocumentResource) resourceToEdit).setDocumentTitle(res.getDocumentTitle());
                ((MarkdownDocumentResource) resourceToEdit).setMarkdownDocumentData(res.getMarkdownDocumentData());
                break;
            }
        }
    }
}
