package com.jcode.ebookpedia.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class FooterPanel extends Composite {

	interface FooterPanelUiBinder extends UiBinder<Widget, FooterPanel> {
	}

	private static FooterPanelUiBinder binder = GWT
			.create(FooterPanelUiBinder.class);

	public FooterPanel() {
		initWidget(binder.createAndBindUi(this));
	}

}
