package com.jcode.ebookpedia.client.mvp.presenter;

import java.util.Set;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;
import com.jcode.ebookpedia.client.AppConstants;
import com.jcode.ebookpedia.client.events.DeletePostEvent;
import com.jcode.ebookpedia.client.mvp.model.DocumentDTO;
import com.jcode.ebookpedia.client.mvp.model.DocumentDTO.Category;
import com.jcode.ebookpedia.client.mvp.model.DocumentDTO.Language;
import com.jcode.ebookpedia.client.mvp.model.DocumentDTO.Quality;
import com.jcode.ebookpedia.client.mvp.model.DocumentDetailsDTO;
import com.jcode.ebookpedia.client.mvp.model.PostDTO;
import com.jcode.ebookpedia.client.rpc.DatastoreServiceAsync;
import com.jcode.ebookpedia.client.util.BeanArray;
import com.jcode.ebookpedia.client.util.RpcCall;

public class PostViewPresenter implements Presenter {

	public interface PostViewDisplay extends Display {

		HasWidgets getCommentsPanel();

		void setAuthors(String authors);

		void setCategories(Set<Category> categories);

		void setCoverId(String coverId);

		void setDescription(String description);

		void setDocumentTitle(String title);

		void setEdition(Integer edition);

		void setFileSize(String fileSize);

		void setFileType(String fileType);

		void setFileUrl(String url);

		void setIsbn(String isbn);

		void setLanguage(Language language);

		void setPublisher(String publisher);

		void setQuality(Quality quality);

		void setVisibleDeleteButton(boolean isAdmin);

		void setPages(Integer pages);

		void setPostId(Long postId);

		void setYear(Integer year);
	}

	private EventBus eventBus;

	private PostViewDisplay display;

	private DatastoreServiceAsync rpcService;

	private Long entryId;

	private CommentsPresenter commentsPresenter;

	// Constructor

	public PostViewPresenter(EventBus eventBus, PostViewDisplay display,
			DatastoreServiceAsync rpcService, Long entryId,
			CommentsPresenter commentsPresenter, boolean isAdmin) {

		this.eventBus = eventBus;
		this.display = display;
		this.rpcService = rpcService;
		this.entryId = entryId;
		this.commentsPresenter = commentsPresenter;
		display.setPresenter(this);
		load(entryId, isAdmin);
	}

	public void doDelete() {
		eventBus.fireEvent(new DeletePostEvent(entryId));
	}

	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
		commentsPresenter.go(display.getCommentsPanel());
	}

	public void goBack() {
		History.back();
	}

	private void load(Long entryId, boolean isAdmin) {
		if (entryId == null)
			return;

		display.setVisibleDeleteButton(isAdmin);
		rpcService.getPost(entryId, true, new RpcCall<BeanArray>() {

			@Override
			public void execute(BeanArray result) {
				if (result == null)
					return;

				if (result.getBean(0) instanceof PostDTO
						&& result.getBean(1) instanceof DocumentDTO
						&& result.getBean(2) instanceof DocumentDetailsDTO) {

					PostDTO post = (PostDTO) result.getBean(0);
					DocumentDTO document = (DocumentDTO) result.getBean(1);
					DocumentDetailsDTO documentDetails = (DocumentDetailsDTO) result
							.getBean(2);

					display.setCoverId(document.getCoverId());
					display.setIsbn(document.getIsbn());
					display.setDocumentTitle(document.getTitle());
					display.setAuthors(document.getAuthors().toString());
					display.setDescription(documentDetails.getDescription());
					display.setQuality(document.getQuality());
					display.setLanguage(document.getLanguage());
					display.setFileType(document.getFileType().toString());
					display.setFileSize(document.getFileSize());

					display.setEdition(document.getEdition());
					display.setPages(document.getPages());
					display.setPublisher(document.getPublisher());
					display.setYear(document.getYear());
					display.setCategories(document.getCategories());

					display.setFileUrl(documentDetails.getLink());
					display.setPostId(post.getId());

					loadComments(post.getId());
				}
			}
		});
	}

	public void loadComments(Long postId) {
		commentsPresenter.setPostId(postId);
		commentsPresenter.goPage(AppConstants.DEFAULT_PAGE);
	}

}
