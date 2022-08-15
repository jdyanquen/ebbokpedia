package com.jcode.ebookpedia.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.jcode.ebookpedia.client.events.EditUserAccountEvent.EditUserAccountEventHandler;

public class EditUserAccountEvent extends GwtEvent<EditUserAccountEventHandler> {

	public interface EditUserAccountEventHandler extends EventHandler {
		void onEditUserAccountEvent(EditUserAccountEvent event);
	}

	public static Type<EditUserAccountEventHandler> TYPE = new Type<EditUserAccountEvent.EditUserAccountEventHandler>();

	private String email;

	private boolean editable;

	public EditUserAccountEvent(String email, boolean editable) {
		this.email = email;
		this.editable = editable;
	}

	@Override
	protected void dispatch(EditUserAccountEventHandler handler) {
		handler.onEditUserAccountEvent(this);
	}

	@Override
	public Type<EditUserAccountEventHandler> getAssociatedType() {
		return TYPE;
	}

	public String getEmail() {
		return email;
	}

	public boolean isEditable() {
		return editable;
	}

}
