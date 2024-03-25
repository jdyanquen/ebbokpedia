package com.jcode.ebookpedia.client.mvp.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.jcode.ebookpedia.client.AppConstants;
import com.jcode.ebookpedia.client.EbookpediaApp;
import com.jcode.ebookpedia.client.events.DeleteUserAccountEvent;
import com.jcode.ebookpedia.client.events.EditUserAccountEvent;
import com.jcode.ebookpedia.client.mvp.model.UserAccountDTO;

public class UserItemView extends Composite {

	interface UserItemViewUiBinder extends UiBinder<Widget, UserItemView> {
	}

	private static UserItemViewUiBinder binder = GWT
			.create(UserItemViewUiBinder.class);

	@UiField
	Image photoImage;

	@UiField
	HTML nameField, emailField, roleField, memberFromField, lastActivityField,
			statusField, ratingField, editField, deleteField;

	public UserItemView(UserAccountDTO userAccount) {
		initWidget(binder.createAndBindUi(this));
		String url;

		if (userAccount.getPhotoId() == null)
			url = AppConstants.NO_PHOTO_URL;
		else
			url = EbookpediaApp.DOWNLOAD_URL + userAccount.getPhotoId();

		String memberFrom = DateTimeFormat.getFormat("MMMM d, yyyy").format(
				userAccount.getMemberFrom());

		String lastActivity = DateTimeFormat.getFormat("MMMM d, yyyy").format(
				userAccount.getLastActivity());

		photoImage.setUrl(url);
		photoImage.setSize("64px", "64px");
		photoImage.getElement().setAttribute("valign", "middle");
		photoImage.addErrorHandler(new ErrorHandler() {
			@Override
			public void onError(ErrorEvent event) {
				photoImage.setUrl(AppConstants.NO_PHOTO_URL);
			}
		});

		final String email = userAccount.getEmailAddress();
		nameField.setHTML("<b>Full name: </b>"
				+ validate(userAccount.getFullName(), "not aviable"));
		emailField.setHTML("<b>Email: </b>"
				+ validate(userAccount.getEmailAddress(), "not aviable"));
		roleField.setHTML("<b>Role: </b>" + userAccount.getRole());

		memberFromField.setHTML("<i>Member from " + memberFrom + "</i>");

		lastActivityField.setHTML("<i>Last activity " + lastActivity + "</i>");

		statusField.setHTML("<i>Status: " + userAccount.getUserStatus()
				+ "</i>");

		editField.setHTML("edit");
		editField.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				EbookpediaApp.get().getEventBus()
						.fireEvent(new EditUserAccountEvent(email, false));
			}
		});

		deleteField.setHTML("delete");
		deleteField.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				EbookpediaApp.get().getEventBus()
						.fireEvent(new DeleteUserAccountEvent(email));
			}
		});

		showUserRating(4);
	}

	private void showUserRating(int score) {
		String style;
		switch (score) {
		case 1:
			style = "one-star";
			break;
		case 2:
			style = "two-stars";
			break;
		case 3:
			style = "three-stars";
			break;
		case 4:
			style = "four-stars";
			break;
		case 5:
			style = "five-stars";
			break;
		default:
			style = "no-stars";
			break;
		}
		ratingField.setStyleName(style);
	}

	private String validate(String str, String alt) {
		return str == null || str.trim().isEmpty() || str.equals("null") ? alt
				: str;
	}
}
