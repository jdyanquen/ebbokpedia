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
@Table(name = "private_message")
public class PrivateMessage implements Identifiable {

	// Properties

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "private_message_id_seq")
	@SequenceGenerator(name = "private_message_id_seq", sequenceName = "private_message_id_seq", allocationSize = 1)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="sender_id")
	private UserAccount sender;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="receiver_id")
	private UserAccount receiver;

	@Column(name = "cover_id", nullable = false)
	private Date createdAt;

	@Column(name = "message", nullable = false)
	private String message;

	@Column(name = "cover_id", nullable = false)
	private PrivateMessageStatus status;
	
}
