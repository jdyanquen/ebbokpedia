package com.jcode.ebookpedia.client.mvp.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.jcode.ebookpedia.client.AppConstants;
import com.jcode.ebookpedia.client.EbookpediaApp;
import com.jcode.ebookpedia.client.events.EditPostEvent;
import com.jcode.ebookpedia.client.mvp.model.UserAccountDTO;

public class UserInfoView extends Composite {

	interface UserInfoViewUiBinder extends UiBinder<Widget, UserInfoView> {
	}

	private static UserInfoViewUiBinder binder = GWT
			.create(UserInfoViewUiBinder.class);

	@UiField
	Image photoImage;

	@UiField
	HTML nicknameHtml, memberFromHtml, websiteHtml, ratingHtml;

	@UiField
	Label newPost;

	public UserInfoView() {
		initWidget(binder.createAndBindUi(this));
	}

	public void clear() {

	}

	public void loadPosterInfo(UserAccountDTO userAccount) {
		String url;

		if (userAccount.getPhotoId() == null)
			url = AppConstants.NO_PHOTO_URL;
		else
			url = EbookpediaApp.DOWNLOAD_URL + userAccount.getPhotoId();

		photoImage.setUrl(url);
		photoImage.setSize("96px", "96px");
		photoImage.getElement().setAttribute("valign", "middle");

		nicknameHtml.setHTML("<b>"
				+ validate(userAccount.getNickname(), "Anonymous") + "</b>");

		memberFromHtml.setHTML("Member from "
				+ DateTimeFormat.getFormat("MMMM d, yyyy").format(
						userAccount.getMemberFrom()));

		websiteHtml.setHTML("Website: "
				+ validate(userAccount.getWebsiteURL(), "unknown"));
	}

	@UiHandler("newPost")
	void onNewPostClick(ClickEvent event) {
		EbookpediaApp.get().getEventBus().fireEvent(new EditPostEvent(null));
	}

	public void showControls(boolean visible) {
		newPost.setVisible(visible);
	}

	private String validate(String str, String alt) {
		return str == null || str.isEmpty() || str.equals("null") ? alt : str;
	}
}
