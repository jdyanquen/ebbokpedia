package com.jcode.ebookpedia.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.jcode.ebookpedia.client.events.ViewPostEvent.ViewPostEventHandler;

public class ViewPostEvent extends GwtEvent<ViewPostEventHandler> {

	public interface ViewPostEventHandler extends EventHandler {
		void onViewPostEvent(ViewPostEvent event);
	}

	public static Type<ViewPostEventHandler> TYPE = new Type<ViewPostEvent.ViewPostEventHandler>();

	private Long id;

	public ViewPostEvent(Long id) {
		this.id = id;
	}

	@Override
	protected void dispatch(ViewPostEventHandler handler) {
		handler.onViewPostEvent(this);
	}

	@Override
	public Type<ViewPostEventHandler> getAssociatedType() {
		return TYPE;
	}

	public Long getId() {
		return id;
	}

}
