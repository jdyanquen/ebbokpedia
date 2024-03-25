package com.jcode.ebookpedia.client.mvp.view;

import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.jcode.ebookpedia.client.AppConstants;
import com.jcode.ebookpedia.client.EbookpediaApp;
import com.jcode.ebookpedia.client.mvp.model.DocumentDTO.Category;
import com.jcode.ebookpedia.client.mvp.model.DocumentDTO.FileType;
import com.jcode.ebookpedia.client.mvp.model.DocumentDTO.Language;
import com.jcode.ebookpedia.client.mvp.model.DocumentDTO.Quality;
import com.jcode.ebookpedia.client.mvp.presenter.PostViewPresenter;
import com.jcode.ebookpedia.client.mvp.presenter.PostViewPresenter.PostViewDisplay;
import com.jcode.ebookpedia.client.mvp.presenter.Presenter;
import com.jcode.ebookpedia.client.util.QualifyPopup;

public class PostView extends Composite implements PostViewDisplay {

	interface PostViewUiBinder extends UiBinder<Widget, PostView> {
	}

	private static PostViewUiBinder binder = GWT.create(PostViewUiBinder.class);

	private PostViewPresenter presenter;

	@UiField
	Image coverImage;

	@UiField
	HTML titleHtml, authorsHtml, publisherHtml, isbnHtml, languageHtml,
			yearHtml, fileTypeHtml, fileSizeHtml, descriptionHtml,
			fileLinkHtml, editionHtml, qualityHtml, pagesHtml, categoriesHtml,
			qualifyHtml;

	@UiField
	Button deleteButton;

	@UiField
	HTMLPanel commentsPanel;

	Long postId;

	boolean qualified;

	// Constructor

	public PostView() {
		initWidget(binder.createAndBindUi(this));
		qualified = false;
	}

	@UiHandler("backButton")
	void onBackButtonClick(ClickEvent event) {
		presenter.goBack();
	}

	@UiHandler("deleteButton")
	void onDeleteButtonClick(ClickEvent event) {
		presenter.doDelete();
	}

	@UiHandler("qualifyHtml")
	void onQualifyClick(ClickEvent event) {
		if (!qualified && postId != null) {
			new QualifyPopup(postId);
			qualified = true;

		} else {
			Window.alert("Thanks, you have already rated this post");
		}
	}

	@Override
	public void setAuthors(String authors) {
		authorsHtml.setHTML(validate(authors, "unknown"));
	}

	@Override
	public void setCategories(Set<Category> categories) {

		String html = "";

		for (Category category : categories) {
			html += "<a href='#" + AppConstants.SEARCH_TOKEN + category + "'>"
					+ category + "</a>   ";
		}

		categoriesHtml.setHTML("<b>Categories: </b> " + html);
	}

	@Override
	public void setCoverId(String coverId) {
		String url;

		if (coverId == null)
			url = AppConstants.NO_COVER_URL;
		else
			url = EbookpediaApp.DOWNLOAD_URL + coverId;

		coverImage.setSize("110px", "150px");
		coverImage.setUrl(url);
		coverImage.addErrorHandler(new ErrorHandler() {
			@Override
			public void onError(ErrorEvent event) {
				coverImage.setUrl(AppConstants.NO_COVER_URL);
			}
		});
	}

	@Override
	public void setDescription(String description) {
		String header = "<b>Description</b><br><p>";
		descriptionHtml.setHTML(header + description);
	}

	@Override
	public void setDocumentTitle(String title) {
		titleHtml.setHTML(title);
	}

	@Override
	public void setEdition(Integer edition) {
		editionHtml.setHTML("<b>Edition</b>: "
				+ validate(edition + "", "unknown"));
	}

	@Override
	public void setFileSize(String fileSize) {
		fileSizeHtml.setHTML(validate(fileSize, "unknown"));
	}

	@Override
	public void setFileType(String fileType) {
		FileType type;

		try {
			type = FileType.valueOf(fileType);
		} catch (Exception e) {
			type = FileType.unknown;
		}
		fileTypeHtml.setTitle(fileType);
		showDocumentFileTypeIcon(type);
	}

	@Override
	public void setFileUrl(String url) {
		fileLinkHtml.setHTML("<b>Link: </b> <a href='" + url
				+ "' target='_blank'>download from iFile.it</a>");
	}

	@Override
	public void setIsbn(String isbn) {
		isbnHtml.setHTML("ISBN " + validate(isbn, "unknown"));
	}

	@Override
	public void setLanguage(Language language) {
		languageHtml.setHTML(validate(language + "", "unknown"));
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = (PostViewPresenter) presenter;
	}

	@Override
	public void setPublisher(String publisher) {
		publisherHtml.setHTML("<b>Publisher: </b>"
				+ validate(publisher, "unknown"));
	}

	@Override
	public void setQuality(Quality quality) {
		qualityHtml.setHTML("<b>Quality: </b>"
				+ validate(quality + "", "unknown"));
	}

	@Override
	public void setVisibleDeleteButton(boolean isAdmin) {
		deleteButton.setVisible(isAdmin);
	}

	@Override
	public void setPages(Integer pages) {
		pagesHtml.setHTML("<b>Pages: </b>" + validate(pages + "", "unknown"));
	}

	@Override
	public void setYear(Integer year) {
		yearHtml.setHTML("Year " + validate(year + "", "unknown"));
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

	private String validate(String str, String alt) {
		return str == null || str.isEmpty() || str.equals("null") ? alt : str;
	}

	@Override
	public HasWidgets getCommentsPanel() {
		return commentsPanel;
	}

	@Override
	public void setPostId(Long postId) {
		this.postId = postId;
	}
}
