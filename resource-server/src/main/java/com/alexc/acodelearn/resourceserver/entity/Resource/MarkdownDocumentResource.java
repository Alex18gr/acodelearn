package com.alexc.acodelearn.resourceserver.entity.Resource;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@NoArgsConstructor
@Table(name = "markdown_document_resource")
public class MarkdownDocumentResource extends Resource {

    @Column(name = "md_title")
    private String documentTitle;

    @Column(name = "md_description")
    private String description;

    @Column(name = "md_document_data")
    private String markdownDocumentData;

}
