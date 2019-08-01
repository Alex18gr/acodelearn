package com.alexc.acodelearn.resourceserver.entity.Resource;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@NoArgsConstructor
@Table(name = "code_snippet_resource")
public class CodeSnippetResource extends Resource {

    @Column(name = "snippet_title")
    private String snippetTitle;

    @Column(name = "snippet_description")
    private String snippetDescription;

    @Column(name = "snippet_document_data")
    private String snippetDocumentData;

    @Column(name = "snippet_language")
    private String snippetLanguage;

}
