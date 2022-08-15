package com.jcode.ebookpedia.server.model;

public enum PrivateMessageStatus {
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