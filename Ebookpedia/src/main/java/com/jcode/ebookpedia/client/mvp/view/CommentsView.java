package com.jcode.ebookpedia.client.mvp.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.jcode.ebookpedia.client.mvp.model.CommentDTO;
import com.jcode.ebookpedia.client.mvp.model.UserAccountDTO;
import com.jcode.ebookpedia.client.mvp.presenter.CommentsPresenter;
import com.jcode.ebookpedia.client.mvp.presenter.CommentsPresenter.CommentsDisplay;
import com.jcode.ebookpedia.client.mvp.presenter.Presenter;
import com.jcode.ebookpedia.client.util.BeanArray;
import com.jcode.ebookpedia.client.util.FieldVerifier;
import com.jcode.ebookpedia.client.util.Paginable;
import com.jcode.ebookpedia.client.util.PageResult;

public class CommentsView extends Composite implements CommentsDisplay,
		Paginable {

	interface CommentsViewUiBinder extends UiBinder<Widget, CommentsView> {
	}

	private static CommentsViewUiBinder binder = GWT
			.create(CommentsViewUiBinder.class);

	@UiField
	Button sendButton;

	@UiField
	Label infoLabel;

	@UiField
	TextArea commentBox;

	@UiField
	VerticalPanel commentsPanel;

	@UiField
	Pager pager;

	private CommentsPresenter presenter;

	public CommentsView() {
		initWidget(binder.createAndBindUi(this));
		pager.setPaginable(this);
		sendButton.setText("Send");
	}

	@Override
	public String getComment() {
		return commentBox.getValue();
	}

	@Override
	public void goPage(int page) {
		presenter.goPage(page);
	}

	@UiHandler("sendButton")
	void onSendButtonClick(ClickEvent event) {
		presenter.doSend();
	}

	public void setComment(String comment) {
		commentBox.setValue(comment);
	}

	@Override
	public void setData(PageResult<BeanArray> pagingResult,
			UserAccountDTO currentUser) {

		commentsPanel.clear();
		if (pagingResult == null || pagingResult.getData() == null)
			return;

		for (BeanArray entry : pagingResult.getData()) {
			if (entry.getBean(0) instanceof CommentDTO) {
				CommentDTO comment = (CommentDTO) entry.getBean(0);
				commentsPanel.add(new CommentItemView(comment, currentUser));
			}
		}
		pager.update(pagingResult.getOffset(), pagingResult.getLimit(),
				pagingResult.getRecordsCount());
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = (CommentsPresenter) presenter;
	}

	public void setVisibleControls(boolean visible) {
		commentBox.setVisible(visible);
		sendButton.setVisible(visible);
		pager.setVisible(visible);

		if (visible)
			infoLabel.setText("Comments");
		else
			infoLabel.setText("You must be logged in to post a comment.");
	}

	@Override
	public boolean validate() {
		String value = FieldVerifier.escapeHtml(commentBox.getValue());
		return !value.trim().isEmpty();
	}

}
