package com.jcode.ebookpedia.client.mvp.presenter;

import com.google.gwt.user.client.ui.Widget;

public interface Display {

	Widget asWidget();

	void setPresenter(Presenter presenter);
}
