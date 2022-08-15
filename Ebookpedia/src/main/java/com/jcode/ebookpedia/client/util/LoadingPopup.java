package com.jcode.ebookpedia.client.util;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PopupPanel;

public class LoadingPopup extends PopupPanel {

	private static final LoadingPopup instance = new LoadingPopup();

	public static LoadingPopup get() {
		return instance;
	}

	private LoadingPopup() {

		HTML html = new HTML("<b>Loading...<b>");
		html.setStyleName("loading-popup");
		add(html);

		setGlassEnabled(true);
		setModal(true);
		setStyleName("");
		setWidth("130px");
		setHeight("30px");
		center();
	}

}
