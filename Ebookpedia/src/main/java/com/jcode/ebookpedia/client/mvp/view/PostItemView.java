package com.jcode.ebookpedia.client.mvp.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.jcode.ebookpedia.client.AppConstants;
import com.jcode.ebookpedia.client.EbookpediaApp;
import com.jcode.ebookpedia.client.events.DeletePostEvent;
import com.jcode.ebookpedia.client.events.EditPostEvent;
import com.jcode.ebookpedia.client.events.ViewPostEvent;
import com.jcode.ebookpedia.client.mvp.model.DocumentDTO;
import com.jcode.ebookpedia.client.mvp.model.DocumentDTO.FileType;
import com.jcode.ebookpedia.client.mvp.model.PostDTO;

public class PostItemView extends Composite {

	interface PostItemViewUiBinder extends UiBinder<Widget, PostItemView> {
	}

	private static PostItemViewUiBinder binder = GWT
			.create(PostItemViewUiBinder.class);

	Long documentId;

	@UiField
	Image coverImage;

	@UiField
	HTML titleHtml, authorsHtml, publisherHtml, isbnHtml, languageHtml,
			yearHtml, fileTypeHtml, fileSizeHtml, postedByHtml, createdAtHtml,
			viewsCountHtml, ratingHtml, editHtml, deteleHtml;

	public PostItemView() {
		initWidget(binder.createAndBindUi(this));
	}

	public PostItemView(PostDTO post, DocumentDTO document) {
		this(post, document, false);
	}

	public PostItemView(PostDTO post, DocumentDTO document, boolean editable) {
		this();
		String url = EbookpediaApp.DOWNLOAD_URL + document.getCoverId();

		if (document.getCoverId() == null)
			url = AppConstants.NO_COVER_URL;
		else
			url = EbookpediaApp.DOWNLOAD_URL + document.getCoverId();

		String createdAt = DateTimeFormat.getFormat("MMMM d, yyyy").format(
				post.getCreatedAt());

		String nickname = validate(post.getPosterNickname(), "anonymous");

		String posterCatalog = "#" + AppConstants.CATALOG_TOKEN
				+ post.getPosterKey().getId();

		documentId = document.getId();

		coverImage.setUrl(url);
		coverImage.setSize("110px", "150px");
		coverImage.addErrorHandler(new ErrorHandler() {
			@Override
			public void onError(ErrorEvent event) {
				coverImage.setUrl(AppConstants.NO_COVER_URL);
			}
		});

		titleHtml.setHTML(document.getTitle());

		authorsHtml.setHTML("<b>Author(s): </b> " + document.getAuthors());

		publisherHtml.setHTML("<b>Publisher: </b> "
				+ validate(document.getPublisher(), "unknown"));
		isbnHtml.setHTML("<b>ISBN: </b>"
				+ validate(document.getIsbn(), "unknown"));
		languageHtml.setHTML("<b>Language:</b> "
				+ validate(document.getLanguage() + "", "unknown"));
		yearHtml.setHTML("<b>Year:</b> "
				+ validate(document.getYear() + "", "unknown"));
		fileSizeHtml.setHTML(validate(document.getFileSize(),
				"file size unknown"));

		fileTypeHtml.setTitle(document.getFileType() + "");

		createdAtHtml.setHTML("<i>Created at " + createdAt + "</i>");

		postedByHtml.setHTML("<i>Posted by <a href='" + posterCatalog + "'>"
				+ nickname + "</a></i>");
		viewsCountHtml.setHTML("<i>" + post.getViews() + " views</i>");

		editHtml.setHTML("edit");
		editHtml.setVisible(editable);

		deteleHtml.setHTML("delete");
		deteleHtml.setVisible(editable);

		showDocumentFileTypeIcon(document.getFileType());

		float score = 0;
		if (post.getVotersCounter() > 0) {
			score = post.getScore() / (float) post.getVotersCounter();
			String formatted = NumberFormat.getFormat("##.##").format(score);

			ratingHtml.setTitle(post.getVotersCounter() + " votes recorded ("
					+ formatted + " rating)");
		}
		showPostRating(Math.round(score));
	}

	@UiHandler("deteleHtml")
	void onDeleteClick(ClickEvent event) {
		EbookpediaApp.get().getEventBus()
				.fireEvent(new DeletePostEvent(documentId));
	}

	@UiHandler("editHtml")
	void onEditClick(ClickEvent event) {
		EbookpediaApp.get().getEventBus()
				.fireEvent(new EditPostEvent(documentId));
	}

	@UiHandler("titleHtml")
	void onTitleClick(ClickEvent event) {
		EbookpediaApp.get().getEventBus()
				.fireEvent(new ViewPostEvent(documentId));
	}

	private void showDocumentFileTypeIcon(FileType type) {
		String style = "";
		switch (type) {
		case chm:
			style = "fileTypeChm";
			break;
		case djvu:
			style = "fileTypeDjvu";
			break;
		case epub:
			style = "fileTypeEpub";
			break;
		case pdf:
			style = "fileTypePdf";
			break;
		case mobi:
			style = "fileTypeMobi";
			break;
		default:
			style = "fileTypeNone";
			break;
		}

		if (!style.isEmpty())
			fileTypeHtml.setStyleName(style);
	}

	private void showPostRating(int score) {
		String style;
		switch (score) {
		case 1:
			style = "one-star";
			break;
		case 2:
			style = "two-stars";
			break;
		case 3:
			style = "three-stars";
			break;
		case 4:
			style = "four-stars";
			break;
		case 5:
			style = "five-stars";
			break;
		default:
			style = "no-stars";
			break;
		}
		ratingHtml.setStyleName(style);
	}

	private String validate(String str, String alt) {
		return str == null || str.isEmpty() || str.equals("null") ? alt : str;
	}

}
