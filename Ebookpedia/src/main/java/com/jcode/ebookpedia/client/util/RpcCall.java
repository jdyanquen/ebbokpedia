package com.jcode.ebookpedia.client.util;

import java.util.logging.Logger;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class RpcCall<T> implements AsyncCallback<T> {

	/** Logger tool */
	public static final Logger logger = Logger.getLogger("RpcCall");

	private static int counter;

	public RpcCall() {
		rpcIn();
	}

	public abstract void execute(T result);

	@Override
	public void onFailure(Throwable caught) {
		logger.severe(caught.getMessage());
		rpcOut();
		Window.alert("Internal server error.");
	};

	public void onSuccess(T result) {
		execute(result);
		rpcOut();
	}

	public void rpcIn() {
		++counter;
		// show
		LoadingPopup.get().show();
	}

	public void rpcOut() {
		--counter;

		if (counter < 0)
			counter = 0;

		if (counter == 0)
			LoadingPopup.get().hide();
	}
}
