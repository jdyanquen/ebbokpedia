package com.jcode.ebookpedia.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.jcode.ebookpedia.client.events.DeleteCommentEvent.DeleteCommentEventHandler;

public class DeleteCommentEvent extends GwtEvent<DeleteCommentEventHandler> {

	public interface DeleteCommentEventHandler extends EventHandler {
		void onDeleteCommentEvent(DeleteCommentEvent event);
	}

	public static Type<DeleteCommentEventHandler> TYPE = new Type<DeleteCommentEvent.DeleteCommentEventHandler>();

	private Long id;

	public DeleteCommentEvent(Long id) {
		this.id = id;
	}

	@Override
	protected void dispatch(DeleteCommentEventHandler handler) {
		handler.onDeleteCommentEvent(this);
	}

	@Override
	public Type<DeleteCommentEventHandler> getAssociatedType() {
		return TYPE;
	}

	public Long getId() {
		return id;
	}

}
