package com.jcode.ebookpedia.client.mvp.presenter;

import java.util.Date;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.HasWidgets;
import com.jcode.ebookpedia.client.mvp.model.RoleEnum;
import com.jcode.ebookpedia.client.mvp.model.UserAccountDTO;
import com.jcode.ebookpedia.client.mvp.model.UserStatusEnum;
import com.jcode.ebookpedia.client.mvp.view.FileUploadDialog;
import com.jcode.ebookpedia.client.rpc.DatastoreServiceAsync;
import com.jcode.ebookpedia.client.util.RpcCall;

public class UserEditPresenter implements Presenter {

	public interface UserEditDisplay extends Display {

		Date getBirthdate();

		String getEmail();

		String getFirstName();

		String getLastName();

		Date getMemberFrom();

		String getNickname();

		String getPhotoUrl();

		RoleEnum getRole();

		UserStatusEnum getUserStatus();

		String getWebSiteUrl();

		void hide();

		Boolean isEditable();

		void setBirthdate(Date birthdate);

		void setEditable(Boolean editable);

		void setEmail(String email);

		void setFirstName(String firstName);

		void setLastName(String lastName);

		void setMemberFrom(Date memberFrom);

		void setNickname(String nickname);

		void setPhotoId(String photoUrl);

		void setRole(RoleEnum role);

		void setUserStatus(UserStatusEnum status);

		void setWebSiteUrl(String webSiteUrl);

		void show();

		boolean validate();
	}

	@SuppressWarnings("unused")
	private EventBus eventBus;

	private UserEditDisplay display;

	private DatastoreServiceAsync rpcService;

	private UserAccountDTO userAccount;
	
	private boolean editable;

	// Constructor

	public UserEditPresenter(EventBus eventBus, UserEditDisplay display,
			DatastoreServiceAsync rpcService, String email, boolean editable) {

		this.eventBus = eventBus;
		this.display = display;
		this.rpcService = rpcService;
		this.editable = editable;
		display.setPresenter(this);
		display.setEditable(editable);
		loadUser(email);
	}

	public void doCancel() {
		display.hide();
	}

	public void doChangePhoto() {
		final FileUploadDialog uploadDialog = new FileUploadDialog(
				FileUploadDialog.ACCEPT_IMAGE);

		uploadDialog.addSubmitCompleteHandler(new SubmitCompleteHandler() {
			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				if (event.getResults().startsWith("file uploaded. id=")) {
					uploadDialog.hide();
					String id = event.getResults().substring(18);
					display.setPhotoId(id);

				} else {
					display.setPhotoId(null);
				}
			}
		});

	}

	public void doSave() {
		if (editable && !display.validate())
			return;

		if (userAccount == null)
			userAccount = new UserAccountDTO();

		userAccount.setUserStatus(display.getUserStatus());
		userAccount.setBirthDate(display.getBirthdate());
		userAccount.setFirstName(display.getFirstName());
		userAccount.setLastName(display.getLastName());
		userAccount.setMemberFrom(display.getMemberFrom());
		userAccount.setNickname(display.getNickname());
		userAccount.setPhotoId(display.getPhotoUrl());
		userAccount.setRole(display.getRole());
		userAccount.setWebsiteURL(display.getWebSiteUrl());

		rpcService.saveUser(userAccount, new RpcCall<Void>() {
			@Override
			public void execute(Void result) {
				display.hide();
				Window.alert("User save successfully");
				History.fireCurrentHistoryState();
			}
		});
	}

	@Override
	public void go(HasWidgets container) {
		display.show();
	}

	private void loadUser(final String email) {
		if (email == null || email.isEmpty())
			return;

		rpcService.getUser(email, new RpcCall<UserAccountDTO>() {
			@Override
			public void execute(UserAccountDTO result) {
				if (result == null) {
					return;
				}
				userAccount = result;
				display.setBirthdate(userAccount.getBirthDate());
				display.setEmail(userAccount.getEmailAddress());
				display.setFirstName(userAccount.getFirstName());
				display.setLastName(userAccount.getLastName());
				display.setMemberFrom(userAccount.getMemberFrom());
				display.setNickname(userAccount.getNickname());
				display.setPhotoId(userAccount.getPhotoId());
				display.setRole(userAccount.getRole());
				display.setWebSiteUrl(userAccount.getWebsiteURL());
				display.setUserStatus(userAccount.getUserStatus());
			}
		});
	}
}
