package com.jcode.ebookpedia.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.jcode.ebookpedia.client.events.QualifyPostEvent.QualifyPostEventHandler;

public class QualifyPostEvent extends GwtEvent<QualifyPostEventHandler> {

	public interface QualifyPostEventHandler extends EventHandler {
		void onQualifyPostEvent(QualifyPostEvent event);
	}

	public static Type<QualifyPostEventHandler> TYPE = new Type<QualifyPostEvent.QualifyPostEventHandler>();

	private Long postId;

	private int score;

	public QualifyPostEvent(Long postId, int score) {
		this.postId = postId;
		this.score = score;
	}

	@Override
	protected void dispatch(QualifyPostEventHandler handler) {
		handler.onQualifyPostEvent(this);
	}

	@Override
	public Type<QualifyPostEventHandler> getAssociatedType() {
		return TYPE;
	}

	public Long getPostId() {
		return postId;
	}

	public int getScore() {
		return score;
	}
}
