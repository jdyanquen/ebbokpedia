package com.jcode.ebookpedia.client.mvp.view;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.user.client.ui.TextBox;

public class TextField extends TextBox implements BlurHandler {

	private static final String ERROR_STYLE = "errorTextField";

	private String errorMessage;
	private String validator;
	private String title;
	private boolean valid = false;

	public TextField() {
		super();
		addBlurHandler(this);
	}

	private void enableErrorStyle(boolean enable) {
		if (enable) {
			addStyleName(ERROR_STYLE);
			selectAll();
			super.setTitle(getErrorMessage());

		} else {
			removeStyleName(ERROR_STYLE);
			super.setTitle(title);
		}
	}

	public String getErrorMessage() {
		if (title != null && !title.isEmpty()) {
			return errorMessage + " (" + title + ")";
		}
		return errorMessage;
	}

	public String getValidator() {
		return validator;
	}

	@Override
	public String getValue() {
		return super.getValue().trim();
	}

	public boolean isValid() {
		validate();
		return valid;
	}

	@Override
	public void onBlur(BlurEvent event) {
		validate();
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
		super.setTitle(title);
	}

	public void setValidator(String regex) {
		this.validator = regex;
	}

	@Override
	public void setValue(String s) {
		removeStyleDependentName(ERROR_STYLE);
		super.setValue(s);
	}

	private void validate() {
		try {
			valid = super.getValue().trim().matches(validator);
			enableErrorStyle(!valid);

		} catch (Exception e) {
			return;
		}
	}

}
