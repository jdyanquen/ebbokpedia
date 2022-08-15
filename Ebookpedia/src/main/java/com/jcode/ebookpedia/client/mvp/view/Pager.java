package com.jcode.ebookpedia.client.mvp.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.jcode.ebookpedia.client.AppConstants;
import com.jcode.ebookpedia.client.util.Paginable;

public class Pager extends Composite {

	interface PagerUiBinder extends UiBinder<Widget, Pager> {
	}

	private static PagerUiBinder binder = GWT.create(PagerUiBinder.class);

	@UiField
	Image first, back, next, last;

	@UiField
	Label info;

	private int currentPage;

	private int totalPages;

	private Paginable paginable;

	public Pager() {
		initWidget(binder.createAndBindUi(this));
		first.setTitle("First page");
		back.setTitle("Back");
		next.setTitle("Next");
		last.setTitle("Last page");
	}

	@UiHandler("back")
	void onBackClick(ClickEvent event) {
		if (currentPage > 1) {
			paginable.goPage(--currentPage);
		}
	}

	@UiHandler("first")
	void onFirstClick(ClickEvent event) {
		if (currentPage > 1) {
			paginable.goPage(1);
		}
	}

	@UiHandler("last")
	void onLastClick(ClickEvent event) {
		if (currentPage < totalPages) {
			paginable.goPage(totalPages);
		}
	}

	@UiHandler("next")
	void onNextClick(ClickEvent event) {
		if (currentPage < totalPages) {
			paginable.goPage(++currentPage);
		}
	}

	public void setPaginable(Paginable paginable) {
		this.paginable = paginable;
	}

	public void update(int offset, int limit, int recordsCount) {
		if (limit > 0) {
			currentPage = (int) Math.ceil((float) (offset += 1)
					/ AppConstants.RECORDS_PER_PAGE);
			totalPages = (int) Math.ceil((float) recordsCount
					/ AppConstants.RECORDS_PER_PAGE);
			info.setText(offset + " - " + (offset + limit - 1) + " / "
					+ recordsCount);
		} else {
			currentPage = 1;
			totalPages = 1;
			info.setText("No records found");
		}

		if (currentPage == 1) {
			first.setUrl(AppConstants.PAGER_FIRST_OFF);
			back.setUrl(AppConstants.PAGER_BACK_OFF);

		} else {
			first.setUrl(AppConstants.PAGER_FIRST);
			back.setUrl(AppConstants.PAGER_BACK);
		}

		if (currentPage == totalPages) {
			next.setUrl(AppConstants.PAGER_NEXT_OFF);
			last.setUrl(AppConstants.PAGER_LAST_OFF);

		} else {
			next.setUrl(AppConstants.PAGER_NEXT);
			last.setUrl(AppConstants.PAGER_LAST);
		}

	}

}
