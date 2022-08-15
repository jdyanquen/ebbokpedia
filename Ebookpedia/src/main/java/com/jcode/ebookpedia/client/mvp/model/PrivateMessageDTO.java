package com.jcode.ebookpedia.client.mvp.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class PrivateMessageDTO implements BaseBean {

	public enum Status {
		READ, UREAD, ERASED;

		public String toString() {
			String str = "";
			switch (this) {
			case READ:
				str = "Read";
				break;
			case UREAD:
				str = "Unread";
				break;
			case ERASED:
				str = "Erased";
				break;
			}
			return str;
		};
	}

	// Properties

	private Long id;

	private UserAccountDTO senderKey;

	private UserAccountDTO receiverKey;

	private Date createdAt;

	private String message;

	private Status status;

	private transient Set<String> fts;

	// Constructor

	public PrivateMessageDTO() {
	}

	// Getters and setters

	public Date getCreatedAt() {
		return createdAt;
	}

	public Set<String> getFts() {
		return fts == null ? fts = new HashSet<String>() : fts;
	}

	@Override
	public Long getId() {
		return id;
	}

	public String getMessage() {
		return message;
	}

	public UserAccountDTO getReceiverKey() {
		return receiverKey;
	}

	public UserAccountDTO getSenderKey() {
		return senderKey;
	}

	public Status getStatus() {
		return status;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public void setFts(Set<String> fts) {
		this.fts = fts;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setReceiverKey(UserAccountDTO receiverKey) {
		this.receiverKey = receiverKey;
	}

	public void setSenderKey(UserAccountDTO senderKey) {
		this.senderKey = senderKey;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}
