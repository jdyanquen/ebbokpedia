package com.jcode.ebookpedia.client.modules.layout;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.jcode.ebookpedia.client.AppConstants;
import com.jcode.ebookpedia.client.Main;

public class HeaderPanel extends Composite {

	interface HeaderPanelUiBinder extends UiBinder<Widget, HeaderPanel> {
	}

	private static HeaderPanelUiBinder binder = GWT.create(HeaderPanelUiBinder.class);

	@UiField
	TextBox searchField;
	
	@UiField
	Button searchButton;

	public HeaderPanel() {
		initWidget(binder.createAndBindUi(this));
		searchField.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (KeyCodes.KEY_ENTER == event.getNativeKeyCode()) {
					onSearchButtonClick(null);
				}
			}
		});
	}

	String escapeSymbols(String str) {
		char[] chars = { '<', '>', '\\', '/', '*', '+', '-', '=', ',', '.',
				'~', '#', '%', '$', '&', '(', ')', '[', ']', '{', '}', '¿', '?' };

		for (char c : chars) {
			str = str.replace(c, ' ');
		}
		return str;
	}

	@UiHandler("searchButton")
	void onSearchButtonClick(ClickEvent event) {
		int index = Main.getInstance().getTabPanel().getTabBar()
				.getSelectedTab();

		String token;
		switch (index) {
		case 1:
			token = AppConstants.SEARCH_MY_CATALOG_TOKEN;
			break;
		case 2:
			token = AppConstants.SEARCH_MESSAGES_TOKEN;
			break;
		case 3:
			token = AppConstants.SEARCH_USERS_TOKEN;
			break;
		default:
			token = AppConstants.SEARCH_TOKEN;
			break;
		}
		History.newItem(token + escapeSymbols(searchField.getText()));
	}
}
