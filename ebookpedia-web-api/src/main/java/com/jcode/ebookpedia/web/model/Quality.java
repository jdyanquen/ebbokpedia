package com.jcode.ebookpedia.web.model;

public enum Quality {
	Low, Normal, High;

	public String toString() {
		String str;
		switch (this) {
		case Low:
			str = "Low (no-searchable / incomplete / scanned)";
			break;
		case High:
			str = "High (searchable / bookmarked)";
			break;
		default:
			str = "Normal (searchable)";
			break;
		}
		return str;
	};
}