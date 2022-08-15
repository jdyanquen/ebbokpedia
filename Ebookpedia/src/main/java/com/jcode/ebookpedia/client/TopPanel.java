package com.jcode.ebookpedia.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.jcode.ebookpedia.client.events.EditUserAccountEvent;
import com.jcode.ebookpedia.client.mvp.model.UserAccountDTO;

public class TopPanel extends Composite {

	interface TopPanelUiBinder extends UiBinder<Widget, TopPanel> {
	}

	private static TopPanelUiBinder binder = GWT.create(TopPanelUiBinder.class);

	@UiField
	HTML nickname, account, signLink;

	public TopPanel() {
		initWidget(binder.createAndBindUi(this));
	}

	public void update(final UserAccountDTO currentUser) {
		try {
			if (currentUser.isSignedIn()) {
				nickname.setHTML(currentUser.getEmailAddress());

				account.setTitle("Edit my account");
				account.setHTML("Account");
				account.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						EbookpediaApp
								.get()
								.getEventBus()
								.fireEvent(
										new EditUserAccountEvent(currentUser
												.getEmailAddress(), true));
					}
				});

				signLink.setHTML("<a href='" + currentUser.getLogoutURL()
						+ "'>Sign out</a>");
			} else {
				nickname.setHTML("");
				account.setHTML("");
				signLink.setHTML("<a href='" + currentUser.getLoginURL()
						+ "'>Sign in</a>");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
