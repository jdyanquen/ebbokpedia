package com.jcode.ebookpedia.client.mvp.view;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.jcode.ebookpedia.client.AppConstants;
import com.jcode.ebookpedia.client.EbookpediaApp;
import com.jcode.ebookpedia.client.mvp.model.RoleEnum;
import com.jcode.ebookpedia.client.mvp.model.UserStatusEnum;
import com.jcode.ebookpedia.client.mvp.presenter.Presenter;
import com.jcode.ebookpedia.client.mvp.presenter.UserEditPresenter;
import com.jcode.ebookpedia.client.mvp.presenter.UserEditPresenter.UserEditDisplay;
import com.jcode.ebookpedia.client.util.FieldVerifier;

public class UserEditView extends DialogBox implements UserEditDisplay {

	interface UserEditViewUiBinder extends UiBinder<Widget, UserEditView> {
	}

	private static UserEditViewUiBinder binder = GWT
			.create(UserEditViewUiBinder.class);

	@UiField
	Image photoImage;

	@UiField
	HTML emailHtml, memberFromHtml, ratingHtml;

	@UiField
	TextField firstNameField, lastNameField, webSiteUrlField, nicknameField;

	@UiField
	ListBox roleField, statusField;

	@UiField
	Label roleLabel, statusLabel;

	@UiField
	DateBox birthdateField;

	@UiField
	Button saveButton, cancelButton;

	private Boolean editable;

	private Date memberFrom;

	private String email, photoId;

	private UserEditPresenter presenter;

	// Constructor

	public UserEditView() {
		add(binder.createAndBindUi(this));
		setAnimationEnabled(true);
		setGlassEnabled(true);
		setModal(true);

		bind();
		center();
	}

	private void bind() {
		firstNameField.setValidator(AppConstants.FIRST_NAME_REGEX);
		firstNameField.setErrorMessage(AppConstants.INVALID_NAME_MSG);
		firstNameField.setTitle("James");

		lastNameField.setValidator(AppConstants.LAST_NAME_REGEX);
		lastNameField.setErrorMessage("Enter a valid last name");
		lastNameField.setTitle("Bond");

		nicknameField.setValidator(AppConstants.TITLE_REGEX);
		nicknameField.setErrorMessage("Enter a valid last nickname");
		nicknameField.setTitle("jbond88");

		webSiteUrlField.setValidator(AppConstants.URL_REGEX);
		webSiteUrlField.setErrorMessage(AppConstants.INVALID_URL_MSG);
		webSiteUrlField.setTitle(AppConstants.URL_EXAMPLE);

		DateTimeFormat dateFormat = DateTimeFormat.getFormat("MMMM d, yyyy");
		birthdateField.setFormat(new DateBox.DefaultFormat(dateFormat));
		ratingHtml.setHTML("Rating: 3");

		photoImage.setTitle("Click to change image");
		photoImage.addErrorHandler(new ErrorHandler() {
			@Override
			public void onError(ErrorEvent event) {
				photoId = null;
				photoImage.setUrl(AppConstants.NO_PHOTO_URL);
			}
		});

		roleField.addItem(RoleEnum.ADMINISTRATOR + "");
		roleField.addItem(RoleEnum.POSTER + "");

		statusField.addItem(UserStatusEnum.SUSPEND + "");
		statusField.addItem(UserStatusEnum.ACTIVE + "");
		// statusField.addItem(UserStatus.INACTIVE + "");
	}

	@Override
	public Date getBirthdate() {
		return birthdateField.getValue();
	}

	@Override
	public String getEmail() {
		return FieldVerifier.escapeHtml(email);
	}

	@Override
	public String getFirstName() {
		return FieldVerifier.escapeHtml(firstNameField.getValue());
	}

	@Override
	public String getLastName() {
		return FieldVerifier.escapeHtml(lastNameField.getValue());
	}

	@Override
	public Date getMemberFrom() {
		return memberFrom;
	}

	@Override
	public String getNickname() {
		return FieldVerifier.escapeHtml(nicknameField.getValue());
	}

	@Override
	public String getPhotoUrl() {
		return photoId;
	}

	@Override
	public RoleEnum getRole() {
		RoleEnum role = RoleEnum.GUEST;
		switch (roleField.getSelectedIndex()) {
		case 0:
			role = RoleEnum.ADMINISTRATOR;
			break;
		case 1:
			role = RoleEnum.POSTER;
			break;
		case 2:
			role = RoleEnum.GUEST;
			break;
		}
		return role;
	}

	@Override
	public UserStatusEnum getUserStatus() {
		UserStatusEnum status = UserStatusEnum.ACTIVE;
		switch (statusField.getSelectedIndex()) {

		case 0:
			status = UserStatusEnum.SUSPEND;
			break;

		case 1:
			status = UserStatusEnum.ACTIVE;
			break;

		case 2:
			status = UserStatusEnum.INACTIVE;
			break;
		}
		return status;
	}

	@Override
	public String getWebSiteUrl() {
		return FieldVerifier.escapeHtml(webSiteUrlField.getValue());
	}

	@Override
	public Boolean isEditable() {
		return editable;
	}

	@UiHandler("cancelButton")
	void onCancelButtonClick(ClickEvent event) {
		presenter.doCancel();
	}

	@UiHandler("photoImage")
	void onPhotoImageClick(ClickEvent event) {
		if (editable)
			presenter.doChangePhoto();
	}

	@UiHandler("saveButton")
	void onSaveButtonClick(ClickEvent event) {
		presenter.doSave();
	}

	@Override
	public void setBirthdate(Date birthdate) {
		this.birthdateField.setValue(birthdate);
	}

	@Override
	public void setEditable(Boolean editable) {
		this.editable = editable;

		if (editable == null)
			editable = true;

		firstNameField.setReadOnly(!editable);
		lastNameField.setReadOnly(!editable);
		webSiteUrlField.setReadOnly(!editable);
		nicknameField.setReadOnly(!editable);
		birthdateField.setEnabled(editable);

		roleField.setVisible(!editable);
		roleLabel.setVisible(editable);

		statusField.setVisible(!editable);
		statusLabel.setVisible(editable);
	}

	@Override
	public void setEmail(String email) {
		this.email = email;
		emailHtml.setHTML("<b>" + email + "</b>");
	}

	@Override
	public void setFirstName(String firstName) {
		this.firstNameField.setValue(firstName);
	}

	@Override
	public void setLastName(String lastName) {
		this.lastNameField.setValue(lastName);
	}

	@Override
	public void setMemberFrom(Date memberFrom) {
		this.memberFrom = memberFrom;
		memberFromHtml.setHTML("Member from "
				+ DateTimeFormat.getFormat("dd-MM-yyyy").format(memberFrom));
	}

	@Override
	public void setNickname(String nickname) {
		this.nicknameField.setValue(nickname);
	}

	@Override
	public void setPhotoId(String photoId) {
		this.photoId = photoId;
		String url;

		if (photoId == null)
			url = AppConstants.NO_PHOTO_URL;
		else
			url = EbookpediaApp.DOWNLOAD_URL + photoId;

		photoImage.setUrl(url);
		photoImage.setSize("96px", "96px");
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = (UserEditPresenter) presenter;
	}

	@Override
	public void setRole(RoleEnum role) {
		int index = 1;
		switch (role) {

		case ADMINISTRATOR:
			index = 0;
			break;

		case POSTER:
			index = 1;
			break;
		}
		roleField.setSelectedIndex(index);
		roleLabel.setText(role + "");
	}

	@Override
	public void setUserStatus(UserStatusEnum status) {
		int index = 0;
		switch (status) {

		case SUSPEND:
			index = 0;
			break;

		case ACTIVE:
			index = 1;
			break;

		case INACTIVE:
			index = 2;
			break;
		}
		statusField.setSelectedIndex(index);
		statusLabel.setText(status + "");
	}

	@Override
	public void setWebSiteUrl(String webSiteUrl) {
		this.webSiteUrlField.setValue(webSiteUrl);
	}

	@Override
	public boolean validate() {
		boolean result = firstNameField.isValid() && lastNameField.isValid();
		return result;
	}

}
