package com.jcode.ebookpedia.client.util;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.jcode.ebookpedia.client.EbookpediaApp;
import com.jcode.ebookpedia.client.events.QualifyPostEvent;

public class QualifyPopup extends DialogBox {

	private Button qualifyButton;

	private HTML ratingImg;

	private HTML ratingValue;

	private Long postId;

	private int score;

	public QualifyPopup(Long postId) {
		this.postId = postId;

		setAnimationEnabled(true);
		setAutoHideEnabled(true);
		setText("Qualify this post");
		bind();
		showPostRating(score = 1);
		center();
	}

	private void bind() {
		ratingImg = new HTML();
		ratingValue = new HTML();
		qualifyButton = new Button("Send");

		VerticalPanel panel = new VerticalPanel();
		panel.setStyleName("qualifyPanel");
		panel.add(ratingImg);
		panel.add(ratingValue);
		panel.add(qualifyButton);
		add(panel);

		ratingImg.addMouseMoveHandler(new MouseMoveHandler() {
			@Override
			public void onMouseMove(MouseMoveEvent event) {
				score = (event.getX() / 20) + 1;
				showPostRating(score);
			}
		});

		qualifyButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				EbookpediaApp.get().getEventBus()
						.fireEvent(new QualifyPostEvent(postId, score));
				hide();
			}
		});
	}

	private void showPostRating(int score) {
		String style = "one-star";
		String value = "Poor";

		switch (score) {
		case 1:
			style = "one-star";
			value = "Poor";
			break;
		case 2:
			style = "two-stars";
			value = "Not that bad";
			break;
		case 3:
			style = "three-stars";
			value = "Average";
			break;
		case 4:
			style = "four-stars";
			value = "Good";
			break;
		case 5:
			style = "five-stars";
			value = "Perfect";
			break;
		}
		ratingImg.setStyleName(style);
		ratingValue.setHTML(value);
	}
}
