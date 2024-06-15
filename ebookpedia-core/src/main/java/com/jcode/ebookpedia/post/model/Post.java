package com.jcode.ebookpedia.post.model;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.jcode.ebookpedia.user.model.UserAccount;

@Entity
@Table(name = "post")
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_id_seq")
    @SequenceGenerator(name = "post_id_seq", sequenceName = "post_id_seq", allocationSize = 1)
    private Long id;
	
	@Column(name = "summary", nullable = false)
	private String summary;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "created_by_user_account_id", nullable = false)
	private UserAccount createdBy;

	@Column(name = "created_at", nullable = false)
	private ZonedDateTime createdAt;
	
	@Column(name = "views", nullable = false)
	private int views;

	@Column(name = "voters_counter", nullable = false)
	private int votersCounter;

	@Column(name = "score", nullable = false)
	private int score;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public UserAccount getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(UserAccount createdBy) {
		this.createdBy = createdBy;
	}

	public int getViews() {
		return views;
	}

	public void setViews(int views) {
		this.views = views;
	}

	public int getVotersCounter() {
		return votersCounter;
	}

	public void setVotersCounter(int votersCounter) {
		this.votersCounter = votersCounter;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public ZonedDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(ZonedDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
	
}
