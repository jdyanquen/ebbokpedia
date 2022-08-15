package com.jcode.ebookpedia.client.mvp.view;

import java.util.HashSet;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;
import com.jcode.ebookpedia.client.AppConstants;
import com.jcode.ebookpedia.client.mvp.model.DocumentDTO.Category;
import com.jcode.ebookpedia.client.mvp.presenter.PostEditPresenter.MetadataDisplay;
import com.jcode.ebookpedia.client.util.FieldVerifier;

public class MetadataView extends DialogBox implements MetadataDisplay {

	interface MetadataViewUiBinder extends UiBinder<Widget, MetadataView> {
	}

	private static MetadataViewUiBinder binder = GWT
			.create(MetadataViewUiBinder.class);

	@UiField
	TextField publisherField, yearField, editionField, pagesField;

	@UiField
	FlexTable categoriesPanel;

	@UiField
	Button closeButton;

	public MetadataView() {
		add(binder.createAndBindUi(this));

		setAnimationEnabled(true);
		setGlassEnabled(true);
		setModal(true);
		setText("Edit metadata");
		bind();
		hide();
		init();
	}

	private void bind() {
		publisherField.setValidator(AppConstants.LAST_NAME_REGEX);
		publisherField.setTitle(AppConstants.NAME_EXAMPLE);
		publisherField.setErrorMessage(AppConstants.INVALID_NAME_MSG);

		yearField.setValidator(AppConstants.UNSIGNED_INTEGER_REGEX);
		yearField.setErrorMessage(AppConstants.INVALID_NUMBER_MSG);

		editionField.setValidator(AppConstants.UNSIGNED_INTEGER_REGEX);
		editionField.setErrorMessage(AppConstants.INVALID_NUMBER_MSG);

		pagesField.setValidator(AppConstants.UNSIGNED_INTEGER_REGEX);
		pagesField.setErrorMessage(AppConstants.INVALID_NUMBER_MSG);
	}

	@Override
	public Set<Category> getCategories() {
		Set<Category> result = new HashSet<Category>();

		int rows = categoriesPanel.getRowCount();

		for (int row = 0; row < rows; row++) {
			int columns = categoriesPanel.getCellCount(row);

			for (int column = 0; column < columns; column++) {
				Widget widget = categoriesPanel.getWidget(row, column);

				if (widget instanceof CheckBox) {
					CheckBox checkBox = (CheckBox) widget;

					if (checkBox.getValue() == true) {
						result.add(Category.valueOf(checkBox.getName()));
					}
				}
			}
		}
		return result;
	}

	@Override
	public Integer getEdition() {
		try {
			return Integer.parseInt(editionField.getValue());

		} catch (NumberFormatException e) {
			return null;
		}
	}

	@Override
	public String getPublisher() {
		return FieldVerifier.escapeHtml(publisherField.getValue());
	}

	@Override
	public Integer getPages() {
		try {
			return Integer.parseInt(pagesField.getValue());

		} catch (NumberFormatException e) {
			return null;
		}
	}

	@Override
	public Integer getYear() {
		try {
			return Integer.parseInt(yearField.getValue());

		} catch (NumberFormatException e) {
			return null;
		}
	}

	private void init() {
		categoriesPanel.addStyleName("categories");
		categoriesPanel.clear();

		int row = 0;
		int column = 0;
		for (Category category : Category.values()) {
			CheckBox checkBox = new CheckBox(category + "");
			checkBox.setName(category + "");
			categoriesPanel.setWidget(row, column, checkBox);

			if (column == 1) {
				column = 0;
				row += 1;

			} else {
				column += 1;
			}
		}
	}

	@UiHandler("closeButton")
	void onCloseButtonClick(ClickEvent event) {
		hide();
	}

	@Override
	public void setCategories(Set<Category> categories) {
		int rows = categoriesPanel.getRowCount();

		for (int row = 0; row < rows; row++) {
			int columns = categoriesPanel.getCellCount(row);

			for (int column = 0; column < columns; column++) {
				Widget widget = categoriesPanel.getWidget(row, column);

				if (widget instanceof CheckBox) {
					CheckBox checkBox = (CheckBox) widget;

					for (Category category : categories) {
						if (checkBox.getName().equals(category + "")) {
							checkBox.setValue(true);
						}
					}
				}
			}
		}
	}

	@Override
	public void setEdition(Integer edition) {
		if (edition == null)
			return;

		this.editionField.setValue(edition + "");
	}

	@Override
	public void setPublisher(String publisher) {
		this.publisherField.setValue(publisher);
	}

	@Override
	public void setPages(Integer pages) {
		if (pages == null)
			return;

		this.pagesField.setValue(pages + "");
	}

	@Override
	public void setYear(Integer year) {
		if (year == null)
			return;

		this.yearField.setValue(year + "");
	}

	@Override
	public void show() {
		super.show();
		center();
	}
}
