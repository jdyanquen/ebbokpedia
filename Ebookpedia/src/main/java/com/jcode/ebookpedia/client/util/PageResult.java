package com.jcode.ebookpedia.client.util;

import java.io.Serializable;
import java.util.List;

public class PageResult<T> implements Serializable {

	/** Serial version UID */
	private static final long serialVersionUID = 1L;
	
	private List<T> data;
	private int offset;
	private int recordsCount;

	public PageResult() {
	}

	public PageResult(List<T> data, int offset, int recordsCount) {
		this.data = data;
		this.offset = offset;
		this.recordsCount = recordsCount;
	}

	public List<T> getData() {
		return data;
	}

	public int getLimit() {
		if (data == null) {
			return 0;
		}
		return data.size();
	}

	public int getOffset() {
		return offset;
	}

	public int getRecordsCount() {
		return recordsCount;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public void setRecordsCount(int recordsCount) {
		this.recordsCount = recordsCount;
	}

}
