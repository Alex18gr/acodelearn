package com.alexc.acodelearn.resourceserver.json;

import com.alexc.acodelearn.resourceserver.json.resource.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Data
@NoArgsConstructor
public class DetailedResourcesCollectionJSON {

    private Date timestamp;
    private DetailedResourcesTypeCollectionJSON resources;

    public DetailedResourcesCollectionJSON(Date timestamp) {
        this.timestamp = timestamp;
        resources = new DetailedResourcesTypeCollectionJSON();
    }

    @Data
    public static class DetailedResourcesTypeCollectionJSON {

        private Collection<LinkResourceJSON> linkResources;
        private Collection<RepositoryResourceJSON> repositoryResources;
        private Collection<FileResourceJSON> resourceFiles;
        private Collection<CodeSnippetResourceJSON> codeSnippetResources;
        private Collection<MarkdownDocumentResourceJSON> markdownDocumentResources;
        private Collection<GuideResourceJSON> guideResources;

        public DetailedResourcesTypeCollectionJSON() {
            this.linkResources = new ArrayList<>();
            this.repositoryResources = new ArrayList<>();
            this.resourceFiles = new ArrayList<>();
            this.codeSnippetResources = new ArrayList<>();
            this.markdownDocumentResources = new ArrayList<>();
            this.guideResources = new ArrayList<>();
        }

        public void addLinkResource(LinkResourceJSON resource) {
            this.linkResources.add(resource);
        }

        public void addRepositoryResource(RepositoryResourceJSON resource) {
            this.repositoryResources.add(resource);
        }

        public void addFileResource(FileResourceJSON resource) {
            this.resourceFiles.add(resource);
        }

        public void addCodeSnippetResource(CodeSnippetResourceJSON resource) {
            this.codeSnippetResources.add(resource);
        }

        public void addMarkdownResource(MarkdownDocumentResourceJSON resource) {
            this.markdownDocumentResources.add(resource);
        }

        public void addGuideResource(GuideResourceJSON resource) {
            this.guideResources.add(resource);
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
