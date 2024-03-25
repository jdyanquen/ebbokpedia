package com.jcode.ebookpedia.client.mvp.model;

import java.util.Date;

public class PostDTO implements BaseBean {

	// Properties

	private Long id;

	private String posterNickname;

	private UserAccountDTO posterKey;

	private int views;

	private int votersCounter;

	private int score;

	private Date createdAt;

	// Constructor

	public PostDTO() {
		this(null);
	}

	public PostDTO(UserAccountDTO posterKey) {
		setCreatedAt(new Date());
		setPosterKey(posterKey);
	}

	// Getters and setters

	public Date getCreatedAt() {
		return createdAt;
	}

	public Long getId() {
		return id;
	}

	public UserAccountDTO getPosterKey() {
		return posterKey;
	}

	public String getPosterNickname() {
		return posterNickname;
	}

	public int getScore() {
		return score;
	}

	public int getViews() {
		return views;
	}

	public int getVotersCounter() {
		return votersCounter;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public void setPosterKey(UserAccountDTO posterKey) {
		this.posterKey = posterKey;
	}

	public void setPosterNickname(String posterNickname) {
		this.posterNickname = posterNickname;
	}

	void setScore(int score) {
		this.score = score;
	}

	void setViews(int views) {
		this.views = views;
	}

	void setVotersCounter(int votersCounter) {
		this.votersCounter = votersCounter;
	}

	public void increaseScore(int newScore) {
		this.score += Math.abs(newScore);
	}

	public void increaseVotersCounter() {
		votersCounter++;
	}

	public void increaseViews() {
		views++;
	}

}
