package com.jcode.ebookpedia.criteria.filter;

public enum FilterOperator {
	EQUAL, GREATER_THAN, GREATER_THAN_OR_EQUAL, LESS_THAN, LESS_THAN_OR_EQUAL, NOT_EQUAL, LIKE, IN;	
	
	@Override
	public String toString() {
		String str = "";
		switch (this) {

		case EQUAL:
			str = " = ";
			break;

		case GREATER_THAN:
			str = " > ";
			break;

		case GREATER_THAN_OR_EQUAL:
			str = " >= ";
			break;

		case LESS_THAN:
			str = " < ";
			break;

		case LESS_THAN_OR_EQUAL:
			str = " <= ";
			break;

		case NOT_EQUAL:
			str = " != ";
			break;
			
		case LIKE:
			str = " like ";
			break;

		case IN:
			str = " in ";
			break;
		}
		return str;
	}
}