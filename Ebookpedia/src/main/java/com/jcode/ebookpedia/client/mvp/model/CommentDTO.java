package com.jcode.ebookpedia.client.mvp.model;

import java.util.Date;

public class CommentDTO implements BaseBean {

	// Properties

	private Long id;

	private Long postId;

	private UserAccountDTO publisherKey;

	private String publisherPhotoId;

	private String nickname;

	private String content;

	private Date createdAt;

	// Constructor

	public CommentDTO() {
		setCreatedAt(new Date());
	}

	// Getters and setters

	public String getContent() {
		return content;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public Long getId() {
		return id;
	}

	public String getNickname() {
		return nickname;
	}

	public Long getPostId() {
		return postId;
	}

	public UserAccountDTO getPublisherKey() {
		return publisherKey;
	}

	public String getPublisherPhotoId() {
		return publisherPhotoId;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public void setPublisherKey(UserAccountDTO publisherKey) {
		this.publisherKey = publisherKey;
	}

	public void setPublisherPhotoId(String publisherPhotoId) {
		this.publisherPhotoId = publisherPhotoId;
	}

}
