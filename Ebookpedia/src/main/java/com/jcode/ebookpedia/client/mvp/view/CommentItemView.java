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
import com.jcode.ebookpedia.client.events.DeleteCommentEvent;
import com.jcode.ebookpedia.client.mvp.model.CommentDTO;
import com.jcode.ebookpedia.client.mvp.model.RoleEnum;
import com.jcode.ebookpedia.client.mvp.model.UserAccountDTO;

public class CommentItemView extends Composite {

	interface CommentItemViewUiBinder extends UiBinder<Widget, CommentItemView> {
	}

	private static CommentItemViewUiBinder binder = GWT
			.create(CommentItemViewUiBinder.class);

	@UiField
	Image photoImage;

	@UiField
	HTML nicknameField, contentField, deleteField;

	// Constructor

	public CommentItemView(CommentDTO comment, UserAccountDTO currentUser) {
		initWidget(binder.createAndBindUi(this));
		String url;

		if (comment.getPublisherPhotoId() == null)
			url = AppConstants.NO_PHOTO_URL;
		else
			url = EbookpediaApp.DOWNLOAD_URL + comment.getPublisherPhotoId();

		photoImage.setUrl(url);
		photoImage.setSize("64px", "64px");
		photoImage.getElement().setAttribute("valign", "middle");
		photoImage.addErrorHandler(new ErrorHandler() {
			@Override
			public void onError(ErrorEvent event) {
				photoImage.setUrl(AppConstants.NO_PHOTO_URL);
			}
		});

		String createdAt = DateTimeFormat.getFormat(
				"MMMM d, yyyy 'at' h:mm aaa").format(comment.getCreatedAt());
		nicknameField.setHTML("<b>" + comment.getNickname() + "</b><i> the "
				+ createdAt + " said: </i>");
		contentField.setHTML("<p>" + comment.getContent());

		if (currentUser.getId() != null
				&& currentUser.getId() == comment.getPublisherKey().getId()
				|| currentUser.getRole().equals(RoleEnum.ADMINISTRATOR)) {

			final Long commentId = comment.getId();
			deleteField.setHTML("<i>delete</i>");
			deleteField.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					EbookpediaApp.get().getEventBus()
							.fireEvent(new DeleteCommentEvent(commentId));
				}
			});

		} else {
			deleteField.setHTML("");
		}
	}

}
