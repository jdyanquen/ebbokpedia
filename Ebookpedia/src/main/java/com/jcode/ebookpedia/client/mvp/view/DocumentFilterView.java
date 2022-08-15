package com.jcode.ebookpedia.client.mvp.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.jcode.ebookpedia.client.mvp.presenter.PostListPresenter;
import com.jcode.ebookpedia.client.mvp.presenter.PostListPresenter.DocumentFilterDisplay;
import com.jcode.ebookpedia.client.mvp.presenter.Presenter;

public class DocumentFilterView extends Composite implements
		DocumentFilterDisplay {

	interface DocumentFilterViewUiBinder extends
			UiBinder<Widget, DocumentFilterView> {
	}

	private static DocumentFilterViewUiBinder binder = GWT
			.create(DocumentFilterViewUiBinder.class);

	private PostListPresenter presenter;

	@UiField
	DisclosurePanel advancedSearchPanel;
	@UiField
	TextBox isbnField, titleField, authorField, keywordsField, yearField,
			editionField, publisherField;
	@UiField
	ListBox fileTypeField;
	@UiField
	Button searchButton, clearButton;

	public DocumentFilterView() {
		initWidget(binder.createAndBindUi(this));
	}

	public void clear() {
		advancedSearchPanel.setOpen(false);
		presenter.doClear();
	}

	@Override
	public HasValue<String> getAuthorsField() {
		return authorField;
	}

	@Override
	public HasValue<String> getEditionField() {
		return editionField;
	}

	@Override
	public String getFileType() {
		return fileTypeField.getValue(fileTypeField.getSelectedIndex());
	}

	@Override
	public HasValue<String> getIsbnField() {
		return isbnField;
	}

	@Override
	public HasValue<String> getKeywordsField() {
		return keywordsField;
	}

	@Override
	public HasValue<String> getPublisherField() {
		return publisherField;
	}

	@Override
	public HasValue<String> getTitleField() {
		return titleField;
	}

	@Override
	public HasValue<String> getYearField() {
		return yearField;

	}

	@UiHandler("clearButton")
	void onClearButtonClick(ClickEvent event) {
		clear();
	}

	@UiHandler("searchButton")
	void onSearchButtonClick(ClickEvent event) {
		presenter.doAdvancedSearch();
	}

	@Override
	public void selectFileType(int index) {
		fileTypeField.setSelectedIndex(index);
	}

	@Override
	public void setFileTypes(Enum<?>[] values) {
		fileTypeField.addItem("---");
		for (Enum<?> type : values) {
			fileTypeField.addItem(type.toString());
		}
	}

	@Override
	public void setOpen(boolean isOpen) {
		advancedSearchPanel.setOpen(isOpen);
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = (PostListPresenter) presenter;
	}

}
