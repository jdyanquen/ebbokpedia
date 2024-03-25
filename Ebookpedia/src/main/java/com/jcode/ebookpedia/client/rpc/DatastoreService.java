package com.jcode.ebookpedia.client.rpc;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.jcode.ebookpedia.client.exception.NotLoggedInException;
import com.jcode.ebookpedia.client.filter.Filter;
import com.jcode.ebookpedia.client.mvp.model.UserAccountDTO;
import com.jcode.ebookpedia.client.util.BeanArray;
import com.jcode.ebookpedia.client.util.PageResult;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("GWT.rpc/datastoreService")
public interface DatastoreService extends RemoteService {

	void deleteComment(Long id);

	void deleteMessage(Long id);

	void deletePost(Long id);

	void deleteUser(String email);

	BeanArray getComment(Long id);

	UserAccountDTO getCurrentUser(String destinationURL);

	BeanArray getMessage(Long id);

	BeanArray getPost(Long id, boolean count);

	UserAccountDTO getUser(Long id);

	UserAccountDTO getUser(String email);

	String[] processUrl(String url);

	void saveComment(BeanArray comment) throws NotLoggedInException;

	void saveMessage(BeanArray message) throws NotLoggedInException;

	void savePost(BeanArray post) throws NotLoggedInException;

	void saveUser(UserAccountDTO userAccount) throws NotLoggedInException;

	PageResult<BeanArray> searchComments(List<Filter<?>> filters,
			int pageNumber);

	PageResult<BeanArray> searchDocuments(List<Filter<?>> filters,
			int pageNumber);

	PageResult<BeanArray> searchMessages(List<Filter<?>> filters,
			int pageNumber);

	PageResult<BeanArray> searchUsers(List<Filter<?>> filters, int pageNumber);

	void qualify(Long postId, int score);
}
