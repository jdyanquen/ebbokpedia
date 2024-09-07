package com.jcode.ebookpedia.client.modules.layout;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;


public class TopPanel extends Composite {

	interface TopPanelUiBinder extends UiBinder<Widget, TopPanel> {
	}

	private static TopPanelUiBinder binder = GWT.create(TopPanelUiBinder.class);

	@UiField
	HTML nickname, account, signLink;

	public TopPanel() {
		initWidget(binder.createAndBindUi(this));
		nickname.setHTML("Jesus David");
	}

}
