package com.jcode.ebookpedia.client.mvp.model;

public enum UserStatusEnum {
	ACTIVE, INACTIVE, SUSPEND, NEW;

	public String toString() {

		String str = "None";
		switch (this) {

		case ACTIVE:
			str = "Active";
			break;

		case INACTIVE:
			str = "Inactive";
			break;

		case SUSPEND:
			str = "Suspend";
			break;

		case NEW:
			str = "New";
			break;
		}
		return str;
	};

}
