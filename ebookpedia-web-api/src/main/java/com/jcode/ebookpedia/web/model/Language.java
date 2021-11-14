package com.jcode.ebookpedia.web.model;

public enum Language {
	en, es, fr, it;

	public String toString() {
		String str;
		switch (this) {
		case es:
			str = "es - Spanish";
			break;

		case fr:
			str = "fr - French";
			break;

		case it:
			str = "it - Italian";
			break;

		default:
			str = "en - English";
			break;
		}
		return str;
	};
}