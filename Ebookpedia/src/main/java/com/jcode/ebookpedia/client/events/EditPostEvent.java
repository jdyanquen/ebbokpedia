package com.jcode.ebookpedia.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.jcode.ebookpedia.client.events.EditPostEvent.EditPostEventHandler;

public class EditPostEvent extends GwtEvent<EditPostEventHandler> {

	public interface EditPostEventHandler extends EventHandler {
		void onEditPostEvent(EditPostEvent event);
	}

	public static Type<EditPostEventHandler> TYPE = new Type<EditPostEvent.EditPostEventHandler>();

	private Long id;

	public EditPostEvent(Long id) {
		this.id = id;
	}

	@Override
	protected void dispatch(EditPostEventHandler handler) {
		handler.onEditPostEvent(this);
	}

	@Override
	public Type<EditPostEventHandler> getAssociatedType() {
		return TYPE;
	}

	public Long getId() {
		return id;
	}

}
