package com.jcode.ebookpedia.client.mvp.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

public class FileUploadDialog extends DialogBox {

	public static final String ACCEPT_ALL_FILES = "";
	public static final String ACCEPT_AUDIO = "audio/*";
	public static final String ACCEPT_IMAGE = "image/*";
	public static final String ACCEPT_VIDEO = "video/*";

	private String accept;

	private FormPanel form;

	public FileUploadDialog() {
		this(ACCEPT_ALL_FILES);
	}

	public FileUploadDialog(String accept) {
		this.accept = accept;

		setAnimationEnabled(true);
		setGlassEnabled(true);
		setModal(true);
		setWidth("300px");
		center();
		init();
	}

	public void addSubmitCompleteHandler(
			SubmitCompleteHandler submitCompleteHandler) {

		form.addSubmitCompleteHandler(submitCompleteHandler);
	}

	private void init() {
		if (accept == null) {
			accept = "";
		}

		form = new FormPanel();
		form.setAction(GWT.getModuleBaseURL() + "upload");
		form.setEncoding(FormPanel.ENCODING_MULTIPART);
		form.setMethod(FormPanel.METHOD_POST);

		FileUpload upload = new FileUpload();
		upload.getElement().setAttribute("accept", accept);
		upload.setName("upload");
		upload.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				form.submit();
			}
		});

		VerticalPanel panel = new VerticalPanel();
		panel.setSpacing(5);
		panel.add(new HTML("<b>Select a file:<b>"));
		panel.add(upload);
		panel.add(new HTML("<br>"));
		panel.add(new Button("Cancel", new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				hide();
			}
		}));

		form.setWidget(panel);
		add(form);
	}
}
