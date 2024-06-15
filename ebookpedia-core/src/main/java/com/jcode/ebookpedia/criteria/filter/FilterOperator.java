package com.jcode.ebookpedia.criteria.filter;

public enum FilterOperator {
	EQ, GT, GE, LT, LE, NE, LK, ANY;	
	
	@Override
	public String toString() {
		String str = "";
		switch (this) {

		case EQ:
			str = " = ";
			break;

		case GT:
			str = " > ";
			break;

		case GE:
			str = " >= ";
			break;

		case LT:
			str = " < ";
			break;

		case LE:
			str = " <= ";
			break;

		case NE:
			str = " != ";
			break;
			
		case LK:
			str = " LIKE ";
			break;

		case ANY:
			str = " IN ";
			break;
		}
		return str;
	}
}