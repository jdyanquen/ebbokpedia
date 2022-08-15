package com.jcode.ebookpedia.server.model;

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
@Table(name = "document")
public class Document implements Identifiable {

	// Properties

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "document_id_seq")
	@SequenceGenerator(name = "document_id_seq", sequenceName = "document_id_seq", allocationSize = 1)
	private Long id;

	@NotEmpty
	@Column(name = "cover_id", unique = true, nullable = false)
	private String coverId;

	@NotEmpty
	@Column(name = "isbn", nullable = false)
	private String isbn;

	@NotEmpty
	@Column(name = "title", nullable = false)
	private String title;

	@NotEmpty
	@Column(name = "authors", nullable = false)
	private String authors;

	@NotEmpty
	@Column(name = "publisher", nullable = false)
	private String publisher;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="language_id")
	private Language language;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="file_type_id")
	private FileType fileType;

	@Column(name = "file_size")
	private String fileSize;

	@Column(name = "year")
	private Integer year;

	@Column(name = "edition")
	private Integer edition;

	@Column(name = "pages")
	private Integer pages;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="document_detail_id")
	private DocumentDetail documentDetail;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="post_id")
	private Post post;

	@NotEmpty
	@ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(name = "document_category", 
        joinColumns = { @JoinColumn(name = "documnet_id") }, 
        inverseJoinColumns = { @JoinColumn(name = "category_id") }
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