package com.jcode.ebookpedia.server;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.server.rpc.RPC;
import com.google.gwt.user.server.rpc.RPCRequest;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class RemoteServiceManager extends RemoteServiceServlet {
	
	/** Serial Version UID */
	private static final long serialVersionUID = 1L;
	
	private static final Logger log = LoggerFactory.getLogger(RemoteServiceManager.class);

	
	/**
	 * Don't touch, it is magic...
	 */
	@Override
	public String processCall(String payload) throws SerializationException {
		try {
			Object handler = getBean(getThreadLocalRequest());
			RPCRequest request = RPC.decodeRequest(payload, handler.getClass(), this);
			onAfterRequestDeserialized(request);
			
			return RPC.invokeAndEncodeResponse(handler, request.getMethod(), request.getParameters(),
					request.getSerializationPolicy());


		} catch (IncompatibleRemoteServiceException ex) {
			log("Incompatible Remote Service Exception...", ex);
			return RPC.encodeResponseForFailure(null, ex);
		}
	}
	
	protected Object getBean(HttpServletRequest request) {
		String service = getService(request);
		Object bean = getBean(service);
		if (!(bean instanceof com.google.gwt.user.client.rpc.RemoteService))
			throw new IllegalArgumentException(
					"Spring bean is not a GWT RemoteService: " + service + " (" + bean + ")");
		if (log.isDebugEnabled())
			log.debug("Bean for service {} is {} ", service, bean);
		return bean;
	}

	protected String getService(HttpServletRequest request) {
		String url = request.getRequestURI();
		String service = url.substring(url.lastIndexOf("/") + 1);
		if (log.isDebugEnabled())
			log.debug("Service for URL {} is {}", url, service);
		return service;
	}

	protected Object getBean(String name) {
		WebApplicationContext applicationContext = WebApplicationContextUtils
				.getWebApplicationContext(getServletContext());
		if (applicationContext == null)
			throw new IllegalStateException("No Spring web application context found");
		if (!applicationContext.containsBean(name))
			throw new IllegalArgumentException("Spring bean not found: " + name);
		return applicationContext.getBean(name);
	}
}
