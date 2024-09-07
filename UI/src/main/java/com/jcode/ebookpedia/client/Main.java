package com.jcode.ebookpedia.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.jcode.ebookpedia.client.modules.layout.FooterPanel;
import com.jcode.ebookpedia.client.modules.layout.HeaderPanel;
import com.jcode.ebookpedia.client.modules.layout.TopPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Main implements EntryPoint {
	
	private static Main instance;
	
	private static Storage localStore;
	
	private String token = null;
	
	private final EventBus eventBus;
	private final TabPanel tabPanel;
	private final TopPanel topPanel;
	private final HeaderPanel headerPanel;
	private final FooterPanel footerPanel;
	
	/**
	 * Private constructor
	 */
	private Main() {
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
	}

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		instance = this;
		localStore = Storage.getLocalStorageIfSupported();
		
		if (localStore != null) {
			token = localStore.getItem("Token");
		}
		if (token == null) {
			Window.Location.assign("login.html?error=expired");
		}

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

		new AppController(eventBus).go();
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
	
	/**
	 * Get instance
	 */
	public static Main getInstance() {
		return instance;
	}
}
