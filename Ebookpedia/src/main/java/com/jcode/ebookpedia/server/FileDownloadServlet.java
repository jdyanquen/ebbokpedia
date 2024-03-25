package com.jcode.ebookpedia.server;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcode.ebookpedia.server.dao.BlobFileDAO;
import com.jcode.ebookpedia.server.model.BlobFile;


@SuppressWarnings("serial")
public class FileDownloadServlet extends HttpServlet {

	/** Logger tool */
	public static final Logger log = LoggerFactory.getLogger(FileDownloadServlet.class);

	public void destroy() {
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) {
		processRequest(request, response);
	}

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) {

		Long id = null;

		try {
			id = Long.parseLong(request.getParameter("id"));
			BlobFile file = new BlobFileDAO().get(id);
			byte[] bytes = file.getContent();
			String contentType = file.getContentType();
			if (contentType == null || contentType.isEmpty())
				contentType = "application/octet-stream";

			response.reset();
			response.setContentType(contentType);
			response.setHeader("Content-Disposition", "inline; filename="
					+ file.getName());
			response.getOutputStream().write(bytes);
			response.getOutputStream().close();

		} catch (Exception e) {
			log.warn("File not found. ID: {}", id);
			try {
				response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
			} catch (IOException ex) {
				log.error(ex.getMessage(), ex);
			} 
		}
	}

}
