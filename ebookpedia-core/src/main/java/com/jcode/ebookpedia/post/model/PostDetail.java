package com.jcode.ebookpedia.post.model;

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

@Entity
@Table(name = "post_detail")
public class PostDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_detail_id_seq")
    @SequenceGenerator(name = "post_detail_id_seq", sequenceName = "post_detail_id_seq", allocationSize = 1)
    private Long id;
	
	@Column(name = "content", nullable = false)
	private String content;

	@NotEmpty
	@Column(name = "cover_id", nullable = false)
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
	@JoinColumn(name = "language_id")
	private Language language;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "file_type_id")
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
	@JoinColumn(name = "post_id")
	private Post post;

	@NotEmpty
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@JoinTable(name = "post_category", joinColumns = { @JoinColumn(name = "post_id") }, inverseJoinColumns = {@JoinColumn(name = "category_id") })
	private Set<Category> categories;
}
