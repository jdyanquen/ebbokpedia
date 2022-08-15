package com.jcode.ebookpedia.client.util;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.jcode.ebookpedia.client.mvp.model.BaseBean;

public class BeanArray implements IsSerializable {

	private BaseBean[] beans;

	// For serialization
	protected BeanArray() {
	}

	public BeanArray(BaseBean... beans) {
		this.beans = beans;
	}

	public BaseBean getBean(int index) {
		return beans[index];
	}

}
