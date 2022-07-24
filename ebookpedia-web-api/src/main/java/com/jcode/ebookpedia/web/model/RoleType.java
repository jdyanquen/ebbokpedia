package com.jcode.ebookpedia.web.model;

public enum RoleType {

	ADMINISTRATOR, POSTER, GUEST;

	public String toString() {
		String str = "None";
		switch (this) {
		case ADMINISTRATOR:
			str = "Administrator";
			break;
		case POSTER:
			str = "Poster";
			break;
		case GUEST:
			str = "Guest";
			break;
		}
		return str;
	};
}
