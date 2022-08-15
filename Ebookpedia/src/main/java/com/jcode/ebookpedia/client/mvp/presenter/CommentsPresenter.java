package com.jcode.ebookpedia.client.mvp.presenter;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.jcode.ebookpedia.client.AppConstants;
import com.jcode.ebookpedia.client.events.DeleteCommentEvent;
import com.jcode.ebookpedia.client.events.DeleteCommentEvent.DeleteCommentEventHandler;
import com.jcode.ebookpedia.client.filter.Filter;
import com.jcode.ebookpedia.client.filter.FilterOperator;
import com.jcode.ebookpedia.client.filter.NumberFilter;
import com.jcode.ebookpedia.client.mvp.model.CommentDTO;
import com.jcode.ebookpedia.client.mvp.model.UserAccountDTO;
import com.jcode.ebookpedia.client.rpc.DatastoreServiceAsync;
import com.jcode.ebookpedia.client.util.BeanArray;
import com.jcode.ebookpedia.client.util.PageResult;
import com.jcode.ebookpedia.client.util.RpcCall;

public class CommentsPresenter implements Presenter {

	public interface CommentsDisplay extends Display {

		String getComment();

		void setComment(String comment);

		void setData(PageResult<BeanArray> comments, UserAccountDTO currentUser);

		void setVisibleControls(boolean visible);

		boolean validate();
	}

	private EventBus eventBus;

	private CommentsDisplay display;

	private DatastoreServiceAsync rpcService;

	private List<Filter<?>> filters;

	private Long postId;

	private UserAccountDTO currentUser;

	public CommentsPresenter(EventBus eventBus, CommentsDisplay display,
			DatastoreServiceAsync rpcService, UserAccountDTO currentUser) {

		this.eventBus = eventBus;
		this.display = display;
		this.rpcService = rpcService;
		this.currentUser = currentUser;
		this.filters = new ArrayList<Filter<?>>(1);
		bind();

		display.setPresenter(this);
		display.setVisibleControls(currentUser.isSignedIn());
	}

	private void bind() {
		eventBus.addHandler(DeleteCommentEvent.TYPE,
				new DeleteCommentEventHandler() {
					@Override
					public void onDeleteCommentEvent(DeleteCommentEvent event) {
						doDeleteComment(event.getId());
					}
				});
	}

	public void doDeleteComment(Long id) {
		rpcService.deleteComment(id, new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Failed to delete comment.");
			}

			@Override
			public void onSuccess(Void result) {
				goPage(AppConstants.DEFAULT_PAGE);
			}
		});
	}

	public void doSend() {
		if (!display.validate())
			return;

		CommentDTO comment = new CommentDTO();
		comment.setPostId(postId);
		comment.setContent(display.getComment());

		rpcService.saveComment(new BeanArray(comment),
				new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Failed to post a comment");
					}

					@Override
					public void onSuccess(Void result) {
						Window.alert("Comment saved.");
						display.setComment("");
						goPage(AppConstants.DEFAULT_PAGE);
					}
				});
	}

	@Override
	public void go(HasWidgets container) {
		container.add(display.asWidget());
	}

	public void goPage(int page) {
		RpcCall<PageResult<BeanArray>> callback = new RpcCall<PageResult<BeanArray>>() {
			@Override
			public void execute(PageResult<BeanArray> result) {
				display.setData(result, currentUser);
			}
		};
		rpcService.searchComments(filters, page, callback);
	}

	public void setPostId(Long postId) {
		this.postId = postId;
		filters.clear();
		filters.add(new NumberFilter(AppConstants.POST_ID, FilterOperator.EQUAL,
				postId));
	}

}
