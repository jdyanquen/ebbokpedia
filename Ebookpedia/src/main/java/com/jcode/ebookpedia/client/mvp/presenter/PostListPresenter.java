package com.jcode.ebookpedia.client.mvp.presenter;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasWidgets;
import com.jcode.ebookpedia.client.AppConstants;
import com.jcode.ebookpedia.client.filter.EnumFilter;
import com.jcode.ebookpedia.client.filter.Filter;
import com.jcode.ebookpedia.client.filter.FilterOperator;
import com.jcode.ebookpedia.client.filter.NumberFilter;
import com.jcode.ebookpedia.client.filter.StringFilter;
import com.jcode.ebookpedia.client.mvp.model.DocumentDTO.FileType;
import com.jcode.ebookpedia.client.mvp.model.UserAccountDTO;
import com.jcode.ebookpedia.client.mvp.view.UserInfoView;
import com.jcode.ebookpedia.client.rpc.DatastoreServiceAsync;
import com.jcode.ebookpedia.client.util.BeanArray;
import com.jcode.ebookpedia.client.util.PageResult;
import com.jcode.ebookpedia.client.util.RpcCall;

public class PostListPresenter implements Presenter {

	public interface DocumentFilterDisplay extends Display {

		HasValue<String> getAuthorsField();

		HasValue<String> getEditionField();

		String getFileType();

		HasValue<String> getIsbnField();

		HasValue<String> getKeywordsField();

		HasValue<String> getPublisherField();

		HasValue<String> getTitleField();

		HasValue<String> getYearField();

		void selectFileType(int index);

		void setFileTypes(Enum<?>[] values);

		void setOpen(boolean isOpen);
	}

	public interface PostListDisplay extends Display {

		void clear();

		DocumentFilterDisplay getDocumentFilterPanel();

		UserInfoView getPosterInfoPanel();

		void setData(PageResult<BeanArray> pagingResult, boolean editable);
	}

	@SuppressWarnings("unused")
	private EventBus eventBus;

	private PostListDisplay display;

	private DatastoreServiceAsync rpcService;

	private List<Filter<?>> filters;

	private Long posterId;

	private boolean editable;

	// Constructor

	public PostListPresenter(EventBus eventBus, PostListDisplay display,
			DatastoreServiceAsync rpcService, Long posterId, boolean editable) {

		this.eventBus = eventBus;
		this.display = display;
		this.rpcService = rpcService;
		this.editable = editable;
		this.posterId = posterId;
		this.filters = new ArrayList<>(1);
		display.setPresenter(this);
		display.getDocumentFilterPanel().setOpen(false);
		bind();
	}

	private void bind() {
		if (posterId != null) {
			rpcService.getUser(posterId, new RpcCall<UserAccountDTO>() {
				@Override
				public void execute(UserAccountDTO result) {
					display.getPosterInfoPanel().setVisible(true);
					display.getPosterInfoPanel().loadPosterInfo(result);
				}
			});
			filters.add(new NumberFilter(AppConstants.POSTER_ID,
					FilterOperator.EQUAL, posterId));
		} else {
			display.getPosterInfoPanel().setVisible(false);
		}
	}

	private void createFilters() {
		filters.clear();
		if (!display.getDocumentFilterPanel().getIsbnField().getValue().trim()
				.isEmpty()) {
			filters.add(new StringFilter(AppConstants.ISBN, FilterOperator.EQUAL,
					display.getDocumentFilterPanel().getIsbnField().getValue()
							.trim()));
		}
		if (!display.getDocumentFilterPanel().getTitleField().getValue().trim()
				.isEmpty()) {
			filters.add(new StringFilter(AppConstants.TITLE, FilterOperator.EQUAL,
					display.getDocumentFilterPanel().getTitleField().getValue()
							.trim()));
		}
		if (!display.getDocumentFilterPanel().getAuthorsField().getValue()
				.trim().isEmpty()) {
			filters.add(new StringFilter(AppConstants.AUTHOR,
					FilterOperator.EQUAL, display.getDocumentFilterPanel()
							.getAuthorsField().getValue().trim()));
		}
		if (!display.getDocumentFilterPanel().getKeywordsField().getValue()
				.trim().isEmpty()) {
			filters.add(new StringFilter(AppConstants.KEYWORDS,
					FilterOperator.EQUAL, display.getDocumentFilterPanel()
							.getKeywordsField().getValue().trim()));
		}
		if (!display.getDocumentFilterPanel().getYearField().getValue().trim()
				.isEmpty()) {
			try {
				int year = Integer.parseInt(display.getDocumentFilterPanel()
						.getYearField().getValue().trim());
				filters.add(new NumberFilter(AppConstants.YEAR,
						FilterOperator.EQUAL, year));

			} catch (Exception e) {
				GWT.log("Error parsing value (year) -> "
						+ display.getDocumentFilterPanel().getYearField()
								.getValue());
			}
		}
		if (!display.getDocumentFilterPanel().getEditionField().getValue()
				.trim().isEmpty()) {
			try {
				int edition = Integer.parseInt(display.getDocumentFilterPanel()
						.getEditionField().getValue().trim());
				filters.add(new NumberFilter(AppConstants.EDITION,
						FilterOperator.EQUAL, edition));

			} catch (Exception e) {
				GWT.log("Error parsing value (edition) -> "
						+ display.getDocumentFilterPanel().getEditionField()
								.getValue());
			}
		}
		if (!display.getDocumentFilterPanel().getPublisherField().getValue()
				.trim().isEmpty()) {
			filters.add(new StringFilter(AppConstants.PUBLISHER,
					FilterOperator.EQUAL, display.getDocumentFilterPanel()
							.getPublisherField().getValue().trim()));
		}
		if (!display.getDocumentFilterPanel().getFileType().equals("---")) {
			filters.add(new EnumFilter(AppConstants.FILE_TYPE,
					FilterOperator.EQUAL, FileType.valueOf(display
							.getDocumentFilterPanel().getFileType())));
		}
		if (posterId != null) {
			filters.add(new NumberFilter(AppConstants.POSTER_ID,
					FilterOperator.EQUAL, posterId));
		}
	}

	private void createFilters(String keywords) {
		filters.clear();
		if (keywords != null && !keywords.trim().isEmpty()) {
			filters.add(new StringFilter(AppConstants.KEYWORDS,
					FilterOperator.EQUAL, keywords.trim()));
		}
		if (posterId != null) {
			filters.add(new NumberFilter(AppConstants.POSTER_ID,
					FilterOperator.EQUAL, posterId));
		}
	}

	public void doAdvancedSearch() {
		History.newItem(AppConstants.HOME_TOKEN, false);
		createFilters();
		goPage(AppConstants.DEFAULT_PAGE);
	}

	public void doBasicSearch(String keywords) {
		createFilters(keywords);
		goPage(AppConstants.DEFAULT_PAGE);
	}

	public void doClear() {
		display.getDocumentFilterPanel().getIsbnField().setValue("");
		display.getDocumentFilterPanel().getTitleField().setValue("");
		display.getDocumentFilterPanel().getAuthorsField().setValue("");
		display.getDocumentFilterPanel().getKeywordsField().setValue("");
		display.getDocumentFilterPanel().getEditionField().setValue("");
		display.getDocumentFilterPanel().getYearField().setValue("");
		display.getDocumentFilterPanel().getPublisherField().setValue("");
		display.getDocumentFilterPanel().selectFileType(0);
		filters.clear();
	}

	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
	}

	public void goPage(int page) {
		Window.scrollTo(0, 0);

		if (page <= 0) {
			page = 1;
		}
		RpcCall<PageResult<BeanArray>> callback = new RpcCall<PageResult<BeanArray>>() {
			@Override
			public void execute(PageResult<BeanArray> result) {
				display.setData(result, editable);
			}
		};
		rpcService.searchDocuments(filters, page, callback);
	}

}
