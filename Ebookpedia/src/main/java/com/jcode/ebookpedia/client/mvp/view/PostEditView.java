package com.jcode.ebookpedia.client.mvp.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
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
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;
import com.jcode.ebookpedia.client.AppConstants;
import com.jcode.ebookpedia.client.EbookpediaApp;
import com.jcode.ebookpedia.client.mvp.model.DocumentDTO.FileType;
import com.jcode.ebookpedia.client.mvp.model.DocumentDTO.Language;
import com.jcode.ebookpedia.client.mvp.model.DocumentDTO.Quality;
import com.jcode.ebookpedia.client.mvp.presenter.PostEditPresenter;
import com.jcode.ebookpedia.client.mvp.presenter.PostEditPresenter.DocumentEditDisplay;
import com.jcode.ebookpedia.client.mvp.presenter.Presenter;
import com.jcode.ebookpedia.client.util.FieldVerifier;

public class PostEditView extends Composite implements DocumentEditDisplay {

	interface PostEditViewUiBinder extends UiBinder<Widget, PostEditView> {
	}

	private static PostEditViewUiBinder binder = GWT
			.create(PostEditViewUiBinder.class);

	@UiField
	Button cancelButton, saveButton;

	@UiField
	Image coverImage;

	@UiField
	HTML fileTypeHtml, fileSizeHtml;

	@UiField
	ListBox qualityField, languageField;

	@UiField
	TextArea descriptionField;

	@UiField
	TextField isbnField, titleField, authorsField, linkField;

	private String coverId = null;

	private PostEditPresenter presenter;

	public PostEditView() {
		initWidget(binder.createAndBindUi(this));
		coverImage.setUrl(AppConstants.NO_COVER_URL);
		bind();
	}

	private void bind() {
		isbnField.setValidator(AppConstants.ISBN_REGEX);
		isbnField.setErrorMessage(AppConstants.INVALID_ISBN_MSG);
		isbnField.setTitle(AppConstants.ISBN_EXAMPLE);

		titleField.setValidator(AppConstants.TITLE_REGEX);
		titleField.setErrorMessage(AppConstants.INVALID_TITLE_MSG);
		titleField.setTitle("Java for dummies");

		authorsField.setValidator(AppConstants.AUTHORS_REGEX);
		authorsField.setErrorMessage(AppConstants.INVALID_NAME_MSG);
		authorsField.setTitle(AppConstants.AUTHORS_EXAMPLE);

		linkField.setValidator(AppConstants.URL_REGEX);
		linkField.setErrorMessage(AppConstants.INVALID_URL_MSG);
		linkField.setTitle(AppConstants.URL_EXAMPLE);

		coverImage.setTitle("Click to change cover");
		coverImage.addErrorHandler(new ErrorHandler() {
			@Override
			public void onError(ErrorEvent event) {
				coverImage.setUrl(AppConstants.NO_COVER_URL);
				coverId = null;
			}
		});

		qualityField.addItem(Quality.Low + "");
		qualityField.addItem(Quality.Normal + "");
		qualityField.addItem(Quality.High + "");

		languageField.addItem(Language.en + "");
		languageField.addItem(Language.es + "");
		languageField.addItem(Language.fr + "");
		languageField.addItem(Language.it + "");

		linkField.addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				presenter.processUrl(linkField.getValue());
			}
		});
	}

	@Override
	public String getAutors() {
		return FieldVerifier.escapeHtml(authorsField.getValue());
	}

	@Override
	public String getCoverId() {
		return coverId;
	}

	@Override
	public String getDecription() {
		return FieldVerifier.escapeHtml(descriptionField.getValue());
	}

	@Override
	public String getDocumentTitle() {
		return FieldVerifier.escapeHtml(titleField.getValue());
	}

	@Override
	public String getFileSize() {
		return fileSizeHtml.getHTML();
	}

	@Override
	public FileType getFileType() {

		try {
			return FileType.valueOf(fileTypeHtml.getHTML());

		} catch (Exception e) {
			return FileType.unknown;
		}
	}

	@Override
	public String getFileUrl() {
		return FieldVerifier.escapeHtml(linkField.getValue());
	}

	@Override
	public String getIsbn() {
		return FieldVerifier.escapeHtml(isbnField.getValue());
	}

	@Override
	public Language getLanguage() {
		Language result = Language.en;
		switch (languageField.getSelectedIndex()) {
		case 0:
			result = Language.en;
			break;

		case 1:
			result = Language.es;
			break;

		case 2:
			result = Language.fr;
			break;

		case 3:
			result = Language.it;
			break;
		}
		return result;
	}

	@Override
	public Quality getQuality() {
		Quality result = Quality.Normal;
		switch (qualityField.getSelectedIndex()) {
		case 0:
			result = Quality.Low;
			break;

		case 1:
			result = Quality.Normal;
			break;

		case 2:
			result = Quality.High;
			break;

		}
		return result;
	}

	@UiHandler("cancelButton")
	void onCancelButtonClick(ClickEvent event) {
		presenter.doCancel();
	}

	@UiHandler("coverImage")
	void onCoverClick(ClickEvent event) {
		presenter.doChangeCover();
	}

	@UiHandler("editMetadataLabel")
	void onEditMetadataLabelClick(ClickEvent event) {
		presenter.doEditMetadata();
	}

	@UiHandler("saveButton")
	void onSaveButtonClick(ClickEvent event) {
		presenter.doSave();
	}

	@Override
	public void setAuthors(String authors) {
		authorsField.setValue(authors);
	}

	@Override
	public void setCoverId(String coverId) {
		this.coverId = coverId;
		String url;

		if (coverId == null)
			url = AppConstants.NO_COVER_URL;
		else
			url = EbookpediaApp.DOWNLOAD_URL + coverId;

		coverImage.setUrl(url);
		coverImage.setSize("110px", "150px");
	}

	@Override
	public void setDescription(String description) {
		descriptionField.setValue(description);
	}

	@Override
	public void setDocumentTitle(String title) {
		titleField.setValue(title);
	}

	@Override
	public void setFileSize(String fileSize) {
		this.fileSizeHtml.setHTML(fileSize);
	}

	@Override
	public void setFileType(String fileType) {
		this.fileTypeHtml.setHTML(fileType);
	}

	@Override
	public void setFileUrl(String url) {
		this.linkField.setValue(url);
	}

	@Override
	public void setIsbn(String isbn) {
		isbnField.setValue(isbn);
	}

	@Override
	public void setLanguage(Language language) {
		if (language == null)
			return;

		int index = 0;
		switch (language) {
		case en:
			index = 0;
			break;

		case es:
			index = 1;
			break;

		case fr:
			index = 2;
			break;

		case it:
			index = 3;
			break;
		}
		languageField.setSelectedIndex(index);
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = (PostEditPresenter) presenter;
	}

	@Override
	public void setQuality(Quality quality) {
		if (quality == null)
			return;

		int index = 0;
		switch (quality) {
		case Low:
			index = 0;
			break;

		case Normal:
			index = 1;
			break;

		case High:
			index = 2;
			break;
		}
		qualityField.setSelectedIndex(index);
	}

	@Override
	public boolean validate() {

		boolean result = authorsField.isValid() && linkField.isValid();

		if (descriptionField.getValue().trim().isEmpty()) {
			Window.alert("You must enter a description of the book.");
			descriptionField.setFocus(true);
			return false;
		}
		return result;
	}

	@Override
	public void resetLinkField() {
		linkField.setText("Please enter a valid iFile.it file link");
		linkField.selectAll();
		linkField.setFocus(true);
	}

}
