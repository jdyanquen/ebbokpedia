package com.jcode.ebookpedia.web.model;

import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "DOCUMENTS")
public class Document implements BaseBean {

	/** Serial Version UUID */
	private static final long serialVersionUID = 1L;

	// Properties

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "documents_id_seq")
	@SequenceGenerator(name = "documents_id_seq", sequenceName = "documents_id_seq", allocationSize = 1)
	private Long id;

	@NotEmpty
	@Column(name = "COVER_ID", unique = true, nullable = false)
	private String coverId;

	@NotEmpty
	@Column(name = "ISBN", nullable = false)
	private String isbn;

	@NotEmpty
	@Column(name = "TITLE", nullable = false)
	private String title;

	@NotEmpty
	@Column(name = "AUTHORS", nullable = false)
	private String authors;

	@NotEmpty
	@Column(name = "PUBLISHER", nullable = false)
	private String publisher;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="LANGUAGE_ID")
	private Language language;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="FILE_TYPE_ID")
	private FileType fileType;

	@Column(name = "FILE_SIZE")
	private Long fileSize;

	@Column(name = "YEAR")
	private Integer year;

	@Column(name = "EDITION")
	private Integer edition;

	@Column(name = "PAGES")
	private Integer pages;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="DOCUMENT_DETAIL_ID")
	private DocumentDetail documentDetail;

	@NotEmpty
	@ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
        name = "DOCUMENTS_CATEGORIES", 
        joinColumns = { @JoinColumn(name = "DOCUMENT_ID") }, 
        inverseJoinColumns = { @JoinColumn(name = "CATEGORY_ID") }
    )
	private Set<Category> categories;

	// Constructor

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		return obj instanceof Document && Objects.equals(this.getId(), ((Document)obj).getId());
	}
}