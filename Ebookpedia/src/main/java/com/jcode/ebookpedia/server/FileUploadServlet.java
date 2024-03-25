package com.jcode.ebookpedia.server;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcode.ebookpedia.client.AppConstants;
import com.jcode.ebookpedia.server.dao.BlobFileDAO;
import com.jcode.ebookpedia.server.model.BlobFile;

@SuppressWarnings("serial")
public class FileUploadServlet extends HttpServlet {

	public static final Logger log = LoggerFactory.getLogger(FileUploadServlet.class);

	public void doPost(HttpServletRequest request, HttpServletResponse response) {

		FileItemIterator iterator = null;
		ServletFileUpload upload = new ServletFileUpload();
		upload.setSizeMax(AppConstants.MAX_FILE_SIZE);

		try {
			iterator = upload.getItemIterator(request);
			while (iterator.hasNext()) {
				FileItemStream item = iterator.next();
				InputStream stream = item.openStream();
				if (!item.isFormField()) {
					String fileName = item.getName();
					String contentType = item.getContentType();
					byte[] bytes = IOUtils.toByteArray(stream);
					BlobFile file = new BlobFile(null, fileName, contentType, bytes);
					new BlobFileDAO().save(file);
					// print(response, "file uploaded. id=" + key.getId());
				}
			}

		} catch (Exception ex) {
			String msg = String.format("File upload error, %s.", ex.getMessage());
			print(response, msg);
			log.error(ex.getMessage(), ex);
		}
	}

	private void print(HttpServletResponse response, String msg) {
		try {
			msg = (msg == null) ? "" : msg;
			response.reset();
			response.setContentType("text/html");
			response.getWriter().print(msg);
			response.getWriter().close();
			
		} catch (IOException ex) {
			log.error(ex.getMessage(), ex);
		}
	}

}
