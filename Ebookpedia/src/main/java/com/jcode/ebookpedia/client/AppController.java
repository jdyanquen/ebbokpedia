package com.jcode.ebookpedia.client;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.UIObject;
import com.jcode.ebookpedia.client.events.DeletePostEvent;
import com.jcode.ebookpedia.client.events.DeletePostEvent.DeletePostEventHandler;
import com.jcode.ebookpedia.client.events.DeleteUserAccountEvent;
import com.jcode.ebookpedia.client.events.DeleteUserAccountEvent.DeleteUserAccountEventHandler;
import com.jcode.ebookpedia.client.events.EditPostEvent;
import com.jcode.ebookpedia.client.events.EditPostEvent.EditPostEventHandler;
import com.jcode.ebookpedia.client.events.EditUserAccountEvent;
import com.jcode.ebookpedia.client.events.EditUserAccountEvent.EditUserAccountEventHandler;
import com.jcode.ebookpedia.client.events.QualifyPostEvent;
import com.jcode.ebookpedia.client.events.QualifyPostEvent.QualifyPostEventHandler;
import com.jcode.ebookpedia.client.events.ViewPostEvent;
import com.jcode.ebookpedia.client.events.ViewPostEvent.ViewPostEventHandler;
import com.jcode.ebookpedia.client.mvp.model.RoleEnum;
import com.jcode.ebookpedia.client.mvp.model.UserAccountDTO;
import com.jcode.ebookpedia.client.mvp.model.UserStatusEnum;
import com.jcode.ebookpedia.client.mvp.presenter.CommentsPresenter;
import com.jcode.ebookpedia.client.mvp.presenter.PostEditPresenter;
import com.jcode.ebookpedia.client.mvp.presenter.PostListPresenter;
import com.jcode.ebookpedia.client.mvp.presenter.PostViewPresenter;
import com.jcode.ebookpedia.client.mvp.presenter.UserEditPresenter;
import com.jcode.ebookpedia.client.mvp.presenter.UserListPresenter;
import com.jcode.ebookpedia.client.mvp.view.CommentsView;
import com.jcode.ebookpedia.client.mvp.view.MetadataView;
import com.jcode.ebookpedia.client.mvp.view.PostEditView;
import com.jcode.ebookpedia.client.mvp.view.PostListView;
import com.jcode.ebookpedia.client.mvp.view.PostView;
import com.jcode.ebookpedia.client.mvp.view.UserEditView;
import com.jcode.ebookpedia.client.mvp.view.UserListView;
import com.jcode.ebookpedia.client.rpc.DatastoreServiceAsync;

/**
 * Class <strong>AppController</strong>
 * 
 * @author Jesus David
 * @version 1.0.0
 */
public class AppController implements ValueChangeHandler<String> {

	// Properties

	private EventBus eventBus;

	private DatastoreServiceAsync datastoreService;

	private UserAccountDTO currentUser;

	private PostListView postListView;

	private UserListView userListView;

	private boolean isAdmin;

	// Constructor

	public AppController(EventBus eventBus,
			DatastoreServiceAsync datastoreService) {

		this.eventBus = eventBus;
		this.datastoreService = datastoreService;
		this.postListView = new PostListView();
		this.userListView = new UserListView();
		loadUserInfo();
		bind();
	}

	// Methods

	private void bind() {
		History.addValueChangeHandler(this);
		eventBus.addHandler(EditUserAccountEvent.TYPE,
				new EditUserAccountEventHandler() {
					@Override
					public void onEditUserAccountEvent(
							EditUserAccountEvent event) {

						goEditUser(event.getEmail(), event.isEditable());
					}
				});

		eventBus.addHandler(EditPostEvent.TYPE, new EditPostEventHandler() {
			@Override
			public void onEditPostEvent(EditPostEvent event) {
				History.newItem(AppConstants.EDIT_POST_TOKEN, false);
				goEditPost(event.getId());
			}
		});

		eventBus.addHandler(ViewPostEvent.TYPE, new ViewPostEventHandler() {
			@Override
			public void onViewPostEvent(ViewPostEvent event) {
				Long entryId = event.getId();
				goViewPost(entryId);
			}
		});

		eventBus.addHandler(DeletePostEvent.TYPE, new DeletePostEventHandler() {
			@Override
			public void onDeletePostEvent(DeletePostEvent event) {
				boolean confirm = Window
						.confirm("Are you sure to delete this entry?");

				if (confirm) {
					doDeletePost(event.getId());
				}
			}
		});

		eventBus.addHandler(DeleteUserAccountEvent.TYPE,
				new DeleteUserAccountEventHandler() {
					@Override
					public void onDeleteUserAccountEvent(
							DeleteUserAccountEvent event) {
						doDeleteUserAccount(event.getEmail());
					}
				});

		eventBus.addHandler(QualifyPostEvent.TYPE,
				new QualifyPostEventHandler() {

					@Override
					public void onQualifyPostEvent(QualifyPostEvent event) {
						doQualifyPost(event.getPostId(), event.getScore());
					}
				});
	}

	private void doQualifyPost(Long postId, int score) {
		datastoreService.qualify(postId, score, new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) {

			}

			@Override
			public void onSuccess(Void result) {
				Window.alert("Thanks for help us to improve the site.");
			}
		});
	}

	private void doDeletePost(Long id) {
		datastoreService.deletePost(id, new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Failed to delete post.");
			}

			@Override
			public void onSuccess(Void result) {
				Window.alert("Post deleted.");
				History.fireCurrentHistoryState();
			}
		});
	}

	private void doDeleteUserAccount(String email) {
		if (currentUser != null && currentUser.getEmailAddress().equals(email)) {
			Window.alert("You can not delete your own account");
			return;
		}

		if (Window.confirm("This will delete all associated posts")) {
			datastoreService.deleteUser(email, new AsyncCallback<Void>() {
				@Override
				public void onFailure(Throwable caught) {
					Window.alert("Failed to delete user.");
				}

				@Override
				public void onSuccess(Void result) {
					History.fireCurrentHistoryState();
				}
			});
		}
	}

	private void enableTab(int index, boolean enable) {
		((UIObject) EbookpediaApp.get().getTabPanel().getTabBar().getTab(index))
				.setVisible(enable);
	}

	private void enableTabs(boolean enable) {
		for (int i = 0; i < 3; i++) {
			enableTab(i, enable);
		}
	}

	private HasWidgets getTab(int index) {
		EbookpediaApp.get().getTabPanel().selectTab(index);
		return (HasWidgets) EbookpediaApp.get().getTabPanel().getWidget(index);
	}

	public UserAccountDTO getCurrentUser() {
		return currentUser;
	}

	public void go() {
		if ("".equals(History.getToken())) {
			History.newItem(AppConstants.HOME_TOKEN);

		} else {
			History.fireCurrentHistoryState();
		}
	}

	public void goCatalog(String token) {
		try {
			int length = AppConstants.CATALOG_TOKEN.length();
			token = token.substring(length);
			if (token.contains("/")) {
				token = token.substring(0, token.indexOf('/'));
			}

			Long posterId = Long.parseLong(token);
			PostListPresenter presenter = new PostListPresenter(eventBus,
					postListView, datastoreService, posterId, false);
			presenter.go(getTab(AppConstants.HOME_TAB_INDEX));
			presenter.goPage(AppConstants.DEFAULT_PAGE);

		} catch (NumberFormatException e) {
			Window.alert("Invalid catalog id");
			goHome();
		}
	}

	public void goEditPost(Long id) {

		if (currentUser.getUserStatus().equals(UserStatusEnum.SUSPEND)) {
			Window.alert(AppConstants.SUSPENDED_USER_MSG);
			return;
		}

		PostEditPresenter presenter = new PostEditPresenter(eventBus,
				new PostEditView(), new MetadataView(), datastoreService, id,
				isAdmin);
		presenter.go(getTab(AppConstants.MY_CATALOG_TAB_INDEX));
	}

	public void goEditUser(String email, boolean editable) {
		UserEditPresenter presenter = new UserEditPresenter(eventBus,
				new UserEditView(), datastoreService, email, editable);
		presenter.go(null);
	}

	public void goHome() {
		History.newItem(AppConstants.HOME_TOKEN, false);
		PostListPresenter presenter = new PostListPresenter(eventBus,
				postListView, datastoreService, null, false);
		presenter.go(getTab(AppConstants.HOME_TAB_INDEX));
		presenter.goPage(AppConstants.DEFAULT_PAGE);
	}

	public void goMessages() {

		String string = "<div><embed src='http://www.xatech.com/web_gear/chat/chat.swf' quality='high' wmode='transparent' width='600' height='400' name='chat' FlashVars='id=166145264' align='middle' allowScriptAccess='sameDomain' type='application/x-shockwave-flash' pluginspage='http://xat.com/update_flash.shtml' /></div>";

		final HTML chat = new HTML(string);
		chat.setStyleName("chat");
		chat.setVisible(false);

		final HTML loading = new HTML();
		loading.setStyleName("loading");
		loading.setVisible(true);

		Timer timer = new Timer() {
			@Override
			public void run() {
				chat.setVisible(true);
				loading.setVisible(false);
			}
		};
		timer.schedule(7000);

		HasWidgets container = getTab(AppConstants.MESSAGES_TAB_INDEX);
		container.clear();
		container.add(chat);
		container.add(loading);
	}

	public void goMyCatalog() {
		Long posterId = currentUser == null ? null : currentUser.getId();
		PostListPresenter presenter = new PostListPresenter(eventBus,
				postListView, datastoreService, posterId, true);
		presenter.go(getTab(AppConstants.MY_CATALOG_TAB_INDEX));
		presenter.goPage(AppConstants.DEFAULT_PAGE);
	}

	public void goSearch(String token) {
		int length = AppConstants.SEARCH_TOKEN.length();
		token = token.substring(length);
		if (token.contains("/")) {
			token = token.substring(0, token.indexOf('/'));
		}
		PostListPresenter presenter = new PostListPresenter(eventBus,
				postListView, datastoreService, null, false);
		presenter.go(getTab(AppConstants.HOME_TAB_INDEX));
		presenter.doBasicSearch(token);
	}

	public void goSearchMessage(String token) {

	}

	public void goSearchMyCatalog(String token) {
		int length = AppConstants.SEARCH_MY_CATALOG_TOKEN.length();
		token = token.substring(length);
		if (token.contains("/")) {
			token = token.substring(0, token.indexOf('/'));
		}

		Long posterId = currentUser == null ? null : currentUser.getId();
		PostListPresenter presenter = new PostListPresenter(eventBus,
				postListView, datastoreService, posterId, true);
		presenter.go(getTab(AppConstants.MY_CATALOG_TAB_INDEX));
		presenter.doBasicSearch(token);
	}

	public void goSearchUser(String token) {
		int length = AppConstants.SEARCH_USERS_TOKEN.length();
		token = token.substring(length);
		if (token.contains("/")) {
			token = token.substring(0, token.indexOf('/'));
		}
		UserListPresenter presenter = new UserListPresenter(eventBus,
				userListView, datastoreService);
		presenter.go(getTab(AppConstants.ADMIN_TAB_INDEX));
		presenter.doBasicSearch(token);
	}

	public void goUsers() {
		UserListPresenter presenter = new UserListPresenter(eventBus,
				userListView, datastoreService);
		presenter.go(getTab(AppConstants.ADMIN_TAB_INDEX));
		presenter.goPage(AppConstants.DEFAULT_PAGE);
	}

	public void goViewPost(Long entryId) {
		Window.scrollTo(0, 0);
		CommentsPresenter commentsPresenter = new CommentsPresenter(eventBus,
				new CommentsView(), datastoreService, currentUser);

		PostViewPresenter presenter = new PostViewPresenter(eventBus,
				new PostView(), datastoreService, entryId, commentsPresenter,
				isAdmin);

		int index = EbookpediaApp.get().getTabPanel().getTabBar()
				.getSelectedTab();
		presenter.go(getTab(index));
		History.newItem("view", false);
	}

	private void loadUserInfo() {
		enableTabs(false);
		enableTab(AppConstants.ADMIN_TAB_INDEX, false);
		datastoreService.getCurrentUser(Window.Location.getHref(),
				new AsyncCallback<UserAccountDTO>() {
					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Error loading user info.");
					}

					@Override
					public void onSuccess(UserAccountDTO result) {
						currentUser = result;
						EbookpediaApp.get().getTopPanel().update(currentUser);

						if (currentUser.isSignedIn()) {
							enableTabs(true);

							String email = currentUser.getEmailAddress();
							if (AppConstants.ADMIN_EMAIL.equals(email)) {
								currentUser.setRole(RoleEnum.ADMINISTRATOR);
							}

							if (currentUser.getRole()
									.equals(RoleEnum.ADMINISTRATOR)) {

								isAdmin = true;
								enableTab(AppConstants.ADMIN_TAB_INDEX, true);
							}
						}
					}
				});
	}

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		String token = event.getValue();
		if (token != null) {

			if (token.equals(AppConstants.HOME_TOKEN)) {
				goHome();

			} else if (token.startsWith(AppConstants.CATALOG_TOKEN)) {
				goCatalog(token);

			} else if (token.startsWith(AppConstants.SEARCH_TOKEN)) {
				goSearch(token);

			} else if (currentUser != null && currentUser.isSignedIn()) {

				if (token.equals(AppConstants.MESSAGES_TOKEN)) {
					goMessages();

				} else if (token.startsWith(AppConstants.SEARCH_MESSAGES_TOKEN)) {
					goSearchMessage(token);

				} else if (token.equals(AppConstants.MY_CATALOG_TOKEN)) {
					goMyCatalog();

				} else if (token.equals(AppConstants.EDIT_POST_TOKEN)) {
					goEditPost(null);

				} else if (token.startsWith(AppConstants.SEARCH_MY_CATALOG_TOKEN)) {
					goSearchMyCatalog(token);

				} else if (token.equals(AppConstants.ADMIN_USERS_TOKEN)
						&& currentUser.getRole().equals(RoleEnum.ADMINISTRATOR)) {
					goUsers();

				} else if (token.startsWith(AppConstants.SEARCH_USERS_TOKEN)
						&& currentUser.getRole().equals(RoleEnum.ADMINISTRATOR)) {
					goSearchUser(token);

				} else {
					goHome();
				}

			} else {
				goHome();
			}
		}
	}
}
