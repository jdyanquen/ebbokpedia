package com.jcode.ebookpedia.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.jcode.ebookpedia.client.events.DeleteUserAccountEvent.DeleteUserAccountEventHandler;

public class DeleteUserAccountEvent extends
		GwtEvent<DeleteUserAccountEventHandler> {

	public interface DeleteUserAccountEventHandler extends EventHandler {
		void onDeleteUserAccountEvent(DeleteUserAccountEvent event);
	}

	public static Type<DeleteUserAccountEventHandler> TYPE = new Type<DeleteUserAccountEvent.DeleteUserAccountEventHandler>();

	private String email;

	public DeleteUserAccountEvent(String email) {
		this.email = email;
	}

	@Override
	protected void dispatch(DeleteUserAccountEventHandler handler) {
		handler.onDeleteUserAccountEvent(this);
	}

	@Override
	public Type<DeleteUserAccountEventHandler> getAssociatedType() {
		return TYPE;
	}

	public String getEmail() {
		return email;
	}

}
