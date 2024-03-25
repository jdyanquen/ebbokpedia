package com.jcode.ebookpedia.client.mvp.presenter;

import java.util.Set;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.HasWidgets;
import com.jcode.ebookpedia.client.AppConstants;
import com.jcode.ebookpedia.client.mvp.model.DocumentDTO;
import com.jcode.ebookpedia.client.mvp.model.DocumentDTO.Category;
import com.jcode.ebookpedia.client.mvp.model.DocumentDTO.FileType;
import com.jcode.ebookpedia.client.mvp.model.DocumentDTO.Language;
import com.jcode.ebookpedia.client.mvp.model.DocumentDTO.Quality;
import com.jcode.ebookpedia.client.mvp.model.DocumentDetailsDTO;
import com.jcode.ebookpedia.client.mvp.model.PostDTO;
import com.jcode.ebookpedia.client.mvp.view.FileUploadDialog;
import com.jcode.ebookpedia.client.rpc.DatastoreServiceAsync;
import com.jcode.ebookpedia.client.util.BeanArray;
import com.jcode.ebookpedia.client.util.RpcCall;

public class PostEditPresenter implements Presenter {

	public interface DocumentEditDisplay extends Display {

		String getAutors();

		String getCoverId();

		String getDecription();

		String getDocumentTitle();

		String getFileSize();

		FileType getFileType();

		String getFileUrl();

		String getIsbn();

		Language getLanguage();

		Quality getQuality();

		void setAuthors(String authors);

		void setCoverId(String coverId);

		void setDescription(String description);

		void setDocumentTitle(String title);

		void setFileSize(String fileSize);

		void setFileType(String fileType);

		void setFileUrl(String url);

		void setIsbn(String isbn);

		void setLanguage(Language language);

		void setQuality(Quality quality);

		void resetLinkField();

		boolean validate();
	}

	public interface MetadataDisplay {

		Set<Category> getCategories();

		Integer getEdition();

		String getPublisher();

		Integer getPages();

		Integer getYear();

		void hide();

		void setCategories(Set<Category> categories);

		void setEdition(Integer edition);

		void setPublisher(String publisher);

		void setPages(Integer pages);

		void setYear(Integer year);

		void show();
	}

	@SuppressWarnings("unused")
	private EventBus eventBus;

	private DocumentEditDisplay display;

	private MetadataDisplay metadataDisplay;

	private DatastoreServiceAsync rpcService;

	private PostDTO post;

	private DocumentDTO document;

	private DocumentDetailsDTO documentDetails;

	boolean validUrl;

	public PostEditPresenter(EventBus eventBus, DocumentEditDisplay display,
			MetadataDisplay metadataDisplay, DatastoreServiceAsync rpcService,
			Long postId, boolean isAdmin) {

		this.eventBus = eventBus;
		this.display = display;
		this.metadataDisplay = metadataDisplay;
		this.rpcService = rpcService;
		display.setPresenter(this);
		loadPost(postId);
	}

	public void doCancel() {
		History.newItem(AppConstants.MY_CATALOG_TOKEN);
	}

	public void doChangeCover() {
		final FileUploadDialog uploadDialog = new FileUploadDialog(
				FileUploadDialog.ACCEPT_IMAGE);

		uploadDialog.addSubmitCompleteHandler(new SubmitCompleteHandler() {
			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				if (event.getResults().startsWith("file uploaded. id=")) {
					uploadDialog.hide();
					String id = event.getResults().substring(18);
					display.setCoverId(id);

				} else {
					display.setCoverId(null);
				}
			}
		});
	}

	public void doEditMetadata() {
		Window.scrollTo(0, 0);
		metadataDisplay.show();
	}

	public void doSave() {
		if (!display.validate() || !validUrl)
			return;

		if (post == null && document == null && documentDetails == null) {
			post = new PostDTO();
			document = new DocumentDTO();
			documentDetails = new DocumentDetailsDTO();
		}

		document.setCoverId(display.getCoverId());
		document.setIsbn(display.getIsbn());
		document.setTitle(display.getDocumentTitle());
		document.setAuthors(display.getAutors());
		document.setQuality(display.getQuality());
		document.setLanguage(display.getLanguage());
		document.setFileType(display.getFileType());
		document.setFileSize(display.getFileSize());

		document.setEdition(metadataDisplay.getEdition());
		document.setPages(metadataDisplay.getPages());
		document.setPublisher(metadataDisplay.getPublisher());
		document.setYear(metadataDisplay.getYear());
		document.setCategories(metadataDisplay.getCategories());

		documentDetails.setDescription(display.getDecription());
		documentDetails.setLink(display.getFileUrl());

		rpcService.savePost(new BeanArray(post, document, documentDetails),
				new RpcCall<Void>() {

					@Override
					public void execute(Void result) {
						Window.alert("Post saved.");
						History.newItem(AppConstants.MY_CATALOG_TOKEN);
					}
				});
	}

	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
	}

	private void loadPost(Long id) {
		if (id == null)
			return;

		rpcService.getPost(id, false, new RpcCall<BeanArray>() {

			@Override
			public void execute(BeanArray result) {
				if (result == null)
					return;

				if (result.getBean(0) instanceof PostDTO
						&& result.getBean(1) instanceof DocumentDTO
						&& result.getBean(2) instanceof DocumentDetailsDTO) {

					post = (PostDTO) result.getBean(0);
					document = (DocumentDTO) result.getBean(1);
					documentDetails = (DocumentDetailsDTO) result.getBean(2);

					display.setCoverId(document.getCoverId());
					display.setIsbn(document.getIsbn());
					display.setDocumentTitle(document.getTitle());
					display.setAuthors(document.getAuthors().toString());
					display.setQuality(document.getQuality());
					display.setLanguage(document.getLanguage());
					display.setFileType(document.getFileType().toString());
					display.setFileSize(document.getFileSize());

					metadataDisplay.setEdition(document.getEdition());
					metadataDisplay.setPages(document.getPages());
					metadataDisplay.setPublisher(document.getPublisher());
					metadataDisplay.setYear(document.getYear());
					metadataDisplay.setCategories(document.getCategories());

					display.setDescription(documentDetails.getDescription());
					display.setFileUrl(documentDetails.getLink());
					validUrl = true;
				}
			}
		});
	}

	public void processUrl(String url) {
		rpcService.processUrl(url, new AsyncCallback<String[]>() {

			@Override
			public void onFailure(Throwable caught) {

			}

			@Override
			public void onSuccess(String[] result) {
				if (result != null && result.length == 2) {
					display.setFileType(result[0]);
					display.setFileSize(result[1]);
					validUrl = true;

				} else {
					display.resetLinkField();
					display.setFileType("unknown");
					display.setFileSize("unknown");
					validUrl = false;
				}
			}
		});
	}

}
