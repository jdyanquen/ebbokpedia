package com.jcode.ebookpedia.client.mvp.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.jcode.ebookpedia.client.mvp.model.UserAccountDTO;
import com.jcode.ebookpedia.client.mvp.presenter.Presenter;
import com.jcode.ebookpedia.client.mvp.presenter.UserListPresenter;
import com.jcode.ebookpedia.client.mvp.presenter.UserListPresenter.UserListDisplay;
import com.jcode.ebookpedia.client.util.BeanArray;
import com.jcode.ebookpedia.client.util.Paginable;
import com.jcode.ebookpedia.client.util.PageResult;

public class UserListView extends Composite implements UserListDisplay,
		Paginable {

	interface UserListViewUiBinder extends UiBinder<Widget, UserListView> {
	}

	private static UserListViewUiBinder binder = GWT
			.create(UserListViewUiBinder.class);

	@UiField
	VerticalPanel usersPanel;

	@UiField
	Pager pager;

	private UserListPresenter presenter;

	public UserListView() {
		initWidget(binder.createAndBindUi(this));
		pager.setPaginable(this);
	}

	public void clear() {
		usersPanel.clear();
	}

	@Override
	public void goPage(int page) {
		presenter.goPage(page);
	}

	public void setData(PageResult<BeanArray> pagingResult) {
		clear();
		if (pagingResult == null || pagingResult.getData() == null)
			return;

		for (BeanArray entry : pagingResult.getData()) {
			if (entry.getBean(0) instanceof UserAccountDTO) {
				UserAccountDTO userAccount = (UserAccountDTO) entry.getBean(0);
				usersPanel.add(new UserItemView(userAccount));
			}
		}
		pager.update(pagingResult.getOffset(), pagingResult.getLimit(),
				pagingResult.getRecordsCount());
	}

	public void setPresenter(Presenter presenter) {
		this.presenter = (UserListPresenter) presenter;
	}
}
