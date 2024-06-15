package com.jcode.ebookpedia.post.dto;

import java.time.ZonedDateTime;

import com.jcode.ebookpedia.user.dto.UserData;

public class PostData {

	private Long id;
	
	private String summary;

	private UserData createdBy;

	private ZonedDateTime createdAt;
	
	private int views;

	private int votersCounter;

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

	public UserData getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(UserData createdBy) {
		this.createdBy = createdBy;
	}

	public ZonedDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(ZonedDateTime createdAt) {
		this.createdAt = createdAt;
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
		
}
