package com.jcode.ebookpedia.server.model;

import java.util.Date;

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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "post")
public class Post implements Identifiable {

	// Properties

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_id_seq")
	@SequenceGenerator(name = "post_id_seq", sequenceName = "post_id_seq", allocationSize = 1)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_account_id")
	private UserAccount postedBy;

	@Column(name = "views", nullable = false)
	private int views;

	@Column(name = "voters_counter", nullable = false)
	private int votersCounter;

	@Column(name = "score", nullable = false)
	private int score;

	@Column(name = "created_at", nullable = false)
	private Date createdAt;

	public synchronized void increaseViews() {
		views++;
	}

	public synchronized void increaseVotersCounter() {
		votersCounter++;
	}

	public synchronized void increaseScore(int score) {
		this.score += score;
	}

}
