package com.alexc.acodelearn.resourceserver.json.resource;

import com.alexc.acodelearn.resourceserver.util.ResourceHelper;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

// @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "className")
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "resourceType",
        visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CodeSnippetResourceJSON.class, name = ResourceHelper.ResourceTypes.RESOURCE_CODE_SNIPPET),
        @JsonSubTypes.Type(value = FileResourceJSON.class, name = ResourceHelper.ResourceTypes.RESOURCE_FILE),
        @JsonSubTypes.Type(value = GuideResourceJSON.class, name = ResourceHelper.ResourceTypes.RESOURCE_GUIDE),
        @JsonSubTypes.Type(value = LinkResourceJSON.class, name = ResourceHelper.ResourceTypes.RESOURCE_LINK),
        @JsonSubTypes.Type(value = MarkdownDocumentResourceJSON.class, name = ResourceHelper.ResourceTypes.RESOURCE_MARKDOWN),
        @JsonSubTypes.Type(value = RepositoryResourceJSON.class, name = ResourceHelper.ResourceTypes.RESOURCE_REPOSITORY)
})
public abstract class AbstractResourceJSON {

    public abstract String getType();

}
