package com.jcode.ebookpedia.client;

import java.util.logging.Logger;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.jcode.ebookpedia.client.rpc.DatastoreService;
import com.jcode.ebookpedia.client.rpc.DatastoreServiceAsync;

public class EbookpediaApp implements EntryPoint {

	/** Logger tool */
	public static final Logger logger = Logger.getLogger("");

	public static final String DOWNLOAD_URL = GWT.getModuleBaseURL()
			+ "download?id=";

	/** Singleton */
	private static EbookpediaApp instance;

	/**
	 * Get instance
	 */
	public static EbookpediaApp get() {
		return instance;
	}

	private final EventBus eventBus;

	private TabPanel tabPanel;

	private TopPanel topPanel;

	private HeaderPanel headerPanel;

	// Default constructor

	private FooterPanel footerPanel;

	private EbookpediaApp() {
		eventBus = new SimpleEventBus();
		tabPanel = new TabPanel();
		topPanel = new TopPanel();
		headerPanel = new HeaderPanel();
		footerPanel = new FooterPanel();

		tabPanel.add(new HTMLPanel(""), "Home");
		tabPanel.add(new HTMLPanel(""), "My Catalog");
		tabPanel.add(new HTMLPanel(""), "Chat");
		tabPanel.add(new HTMLPanel(""), "Users");

		tabPanel.selectTab(0);
		tabPanel.setStylePrimaryName("tab-panel");

		tabPanel.addSelectionHandler(new SelectionHandler<Integer>() {
			@Override
			public void onSelection(SelectionEvent<Integer> event) {
				String token = "";
				switch (event.getSelectedItem()) {

				case 0:
					if (!History.getToken().startsWith(AppConstants.HOME_TOKEN)) {
						token = AppConstants.HOME_TOKEN;
					}
					break;

				case 1:
					if (!History.getToken().startsWith(
							AppConstants.MY_CATALOG_TOKEN)) {
						token = AppConstants.MY_CATALOG_TOKEN;
					}
					break;

				case 2:
					if (!History.getToken()
							.startsWith(AppConstants.MESSAGES_TOKEN)) {
						token = AppConstants.MESSAGES_TOKEN;
					}
					break;

				case 3:
					if (!History.getToken().startsWith(
							AppConstants.ADMIN_USERS_TOKEN)) {
						token = AppConstants.ADMIN_USERS_TOKEN;
					}
					break;
				}

				if (!token.isEmpty()) {
					History.newItem(token);
				}
			}
		});
	}

	public EventBus getEventBus() {
		return eventBus;
	}

	public TabPanel getTabPanel() {
		return tabPanel;
	}

	public TopPanel getTopPanel() {
		return topPanel;
	}

	@Override
	public void onModuleLoad() {
		instance = this;
		RootPanel top = RootPanel.get("top");
		top.clear();
		top.add(topPanel);

		RootPanel header = RootPanel.get("header");
		header.clear();
		header.add(headerPanel);

		RootPanel center = RootPanel.get("content");
		center.clear();
		center.add(tabPanel);

		RootPanel footer = RootPanel.get("footer");
		footer.clear();
		footer.add(footerPanel);

		DatastoreServiceAsync datastoreService = GWT
				.create(DatastoreService.class);

		new AppController(eventBus, datastoreService).go();
	}
}
