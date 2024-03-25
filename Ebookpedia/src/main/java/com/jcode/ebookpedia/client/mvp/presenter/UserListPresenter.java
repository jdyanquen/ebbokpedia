package com.jcode.ebookpedia.client.mvp.presenter;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasWidgets;
import com.jcode.ebookpedia.client.AppConstants;
import com.jcode.ebookpedia.client.filter.Filter;
import com.jcode.ebookpedia.client.filter.FilterOperator;
import com.jcode.ebookpedia.client.filter.StringFilter;
import com.jcode.ebookpedia.client.rpc.DatastoreServiceAsync;
import com.jcode.ebookpedia.client.util.BeanArray;
import com.jcode.ebookpedia.client.util.PageResult;
import com.jcode.ebookpedia.client.util.RpcCall;

public class UserListPresenter implements Presenter {

	public interface UserListDisplay extends Display {

		void clear();

		void setData(PageResult<BeanArray> pagingResult);
	}

	@SuppressWarnings("unused")
	private EventBus eventBus;

	private UserListDisplay display;

	private DatastoreServiceAsync rpcService;

	private List<Filter<?>> filters;

	public UserListPresenter(EventBus eventBus, UserListDisplay display,
			DatastoreServiceAsync rpcService) {
		this.eventBus = eventBus;
		this.display = display;
		this.rpcService = rpcService;
		this.filters = new ArrayList<Filter<?>>(1);
	}

	public void doBasicSearch(String keywords) {
		filters.clear();
		if (keywords != null && !keywords.trim().isEmpty()) {
			filters.add(new StringFilter(AppConstants.KEYWORDS,
					FilterOperator.EQUAL, keywords.trim()));
		}
		goPage(AppConstants.DEFAULT_PAGE);
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
				display.setData(result);
			}
		};
		rpcService.searchUsers(filters, page, callback);
	}

}
