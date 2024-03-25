package com.jcode.ebookpedia.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.jcode.ebookpedia.client.events.DeletePostEvent.DeletePostEventHandler;

public class DeletePostEvent extends GwtEvent<DeletePostEventHandler> {

	public interface DeletePostEventHandler extends EventHandler {
		void onDeletePostEvent(DeletePostEvent event);
	}

	public static Type<DeletePostEventHandler> TYPE = new Type<DeletePostEvent.DeletePostEventHandler>();

	private Long id;

	public DeletePostEvent(Long id) {
		this.id = id;
	}

	@Override
	protected void dispatch(DeletePostEventHandler handler) {
		handler.onDeletePostEvent(this);
	}

	@Override
	public Type<DeletePostEventHandler> getAssociatedType() {
		return TYPE;
	}

	public Long getId() {
		return id;
	}

}
