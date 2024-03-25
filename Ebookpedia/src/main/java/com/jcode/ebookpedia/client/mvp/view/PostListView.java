package com.jcode.ebookpedia.client.mvp.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.jcode.ebookpedia.client.mvp.model.DocumentDTO;
import com.jcode.ebookpedia.client.mvp.model.DocumentDTO.FileType;
import com.jcode.ebookpedia.client.mvp.model.PostDTO;
import com.jcode.ebookpedia.client.mvp.presenter.PostListPresenter;
import com.jcode.ebookpedia.client.mvp.presenter.PostListPresenter.DocumentFilterDisplay;
import com.jcode.ebookpedia.client.mvp.presenter.PostListPresenter.PostListDisplay;
import com.jcode.ebookpedia.client.mvp.presenter.Presenter;
import com.jcode.ebookpedia.client.util.BeanArray;
import com.jcode.ebookpedia.client.util.Paginable;
import com.jcode.ebookpedia.client.util.PageResult;

public class PostListView extends Composite implements PostListDisplay,
		Paginable {

	interface PostListViewUiBinder extends UiBinder<Widget, PostListView> {
	}

	private static PostListViewUiBinder binder = GWT
			.create(PostListViewUiBinder.class);

	@UiField
	UserInfoView userInfoPanel;

	@UiField
	DocumentFilterView filtersPanel;

	@UiField
	VerticalPanel postPanel;

	@UiField
	Pager pager;

	private PostListPresenter presenter;

	public PostListView() {
		initWidget(binder.createAndBindUi(this));
		filtersPanel.setFileTypes(FileType.values());
		pager.setPaginable(this);
	}

	@Override
	public void clear() {
		userInfoPanel.clear();
		filtersPanel.clear();
		postPanel.clear();
	}

	@Override
	public DocumentFilterDisplay getDocumentFilterPanel() {
		return filtersPanel;
	}

	@Override
	public UserInfoView getPosterInfoPanel() {
		return userInfoPanel;
	}

	@Override
	public void goPage(int page) {
		presenter.goPage(page);
	}

	@Override
	public void setData(PageResult<BeanArray> pagingResult, boolean editable) {
		postPanel.clear();
		if (pagingResult == null || pagingResult.getData() == null)
			return;

		for (BeanArray entry : pagingResult.getData()) {
			if (entry.getBean(0) instanceof PostDTO
					&& entry.getBean(1) instanceof DocumentDTO) {

				PostDTO post = (PostDTO) entry.getBean(0);
				DocumentDTO document = (DocumentDTO) entry.getBean(1);
				postPanel.add(new PostItemView(post, document, editable));
			}
		}
		userInfoPanel.showControls(editable);
		pager.update(pagingResult.getOffset(), pagingResult.getLimit(),
				pagingResult.getRecordsCount());
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = (PostListPresenter) presenter;
		filtersPanel.setPresenter(this.presenter);
	}
}
