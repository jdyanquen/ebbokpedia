package com.jcode.ebookpedia.server.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "document_index")
public class DocumentIndex implements Identifiable {

	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="document_id")
	private Document document;

	@Column(name = "posterId", nullable = false)
	private Long posterId;

	@Column(name = "isbn", nullable = false)
	private String isbn;

	@Column(name = "year", nullable = false)
	private Integer year;

	@Column(name = "edition", nullable = false)
	private Integer edition;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="file_type_id")
	private FileType fileType;

	private Set<String> keywords;

	
}
