package com.jcode.ebookpedia.client.mvp.model;

import java.util.Collections;
import java.util.Set;


public class DocumentDTO implements BaseBean {

	public enum Category {
		Art, Biology, Chemical, Computation, Economy, Engineering, History, Laws, Literature, Mathematics, Medicine, Physical, Religion, Telecommunications, Other;
	}

	public enum FileType {
		chm, djvu, epub, pdf, mobi, unknown;
	}

	public enum Language {
		en, es, fr, it;

		public String toString() {
			String str;
			switch (this) {
			case es:
				str = "es - Spanish";
				break;

			case fr:
				str = "fr - French";
				break;

			case it:
				str = "it - Italian";
				break;

			default:
				str = "en - English";
				break;
			}
			return str;
		};
	}

	public enum Quality {
		Low, Normal, High;

		public String toString() {
			String str;
			switch (this) {
			case Low:
				str = "Low (no-searchable / incomplete / scanned)";
				break;
			case High:
				str = "High (searchable / bookmarked)";
				break;
			default:
				str = "Normal (searchable)";
				break;
			}
			return str;
		};
	}

	// Properties

	private Long id;

	private PostDTO postKey;

	private DocumentDetailsDTO documentDetailstKey;

	private String coverId;

	private String isbn;

	private String title;

	private String authors;

	private String publisher;

	private Language language;

	private FileType fileType;

	private Quality quality;

	private String fileSize;

	private Integer year;

	private Integer edition;

	private Integer pages;

	private Set<Category> categories;

	// Constructor

	public DocumentDTO() {
	}

	// Getters and setters

	public String getAuthors() {
		return authors;
	}

	public Set<Category> getCategories() {
		return categories == null ? categories = Collections.emptySet()
				: categories;
	}

	public String getCoverId() {
		return coverId;
	}

	public DocumentDetailsDTO getDocumentDetailstKey() {
		return documentDetailstKey;
	}

	public Integer getEdition() {
		return edition;
	}

	public String getFileSize() {
		return fileSize;
	}

	public FileType getFileType() {
		return fileType;
	}

	public Long getId() {
		return id;
	}

	public String getIsbn() {
		return isbn;
	}

	public Language getLanguage() {
		return language;
	}

	public PostDTO getPostKey() {
		return postKey;
	}

	public String getPublisher() {
		return publisher;
	}

	public Quality getQuality() {
		return quality;
	}

	public String getTitle() {
		return title;
	}

	public Integer getPages() {
		return pages;
	}

	public Integer getYear() {
		return year;
	}

	public void setAuthors(String authors) {
		this.authors = authors;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}

	public void setCoverId(String coverId) {
		this.coverId = coverId;
	}

	public void setDocumentDetailstKey(DocumentDetailsDTO documentDetailstKey) {
		this.documentDetailstKey = documentDetailstKey;
	}

	public void setEdition(Integer edition) {
		this.edition = edition;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public void setFileType(FileType fileType) {
		this.fileType = fileType;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public void setPostKey(PostDTO postKey) {
		this.postKey = postKey;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public void setQuality(Quality quality) {
		this.quality = quality;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setPages(Integer pages) {
		this.pages = pages;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

}
