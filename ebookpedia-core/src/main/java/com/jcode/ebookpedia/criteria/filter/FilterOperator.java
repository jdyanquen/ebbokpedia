package com.jcode.ebookpedia.criteria.filter;

public enum FilterOperator {
	EQ, GT, GE, LT, LE, NE, LK, NLK, ANY, INVERSE_IN;	
	
	@Override
	public String toString() {
		String str = "";
		switch (this) {

		case EQ:
			str = "=";
			break;

		case GT:
			str = ">";
			break;

		case GE:
			str = ">=";
			break;

		case LT:
			str = "<";
			break;

		case LE:
			str = "<=";
			break;

		case NE:
			str = "!=";
			break;
			
		case LK:
			str = "LIKE";
			break;
		
		case NLK:
			str = "NOT LIKE";
			break;

		case ANY:
			str = "IN";
			break;
		
		case INVERSE_IN:
			str = "INVERSE_IN";
			break;
		}
		return str;
	}
}