package com.jcode.ebookpedia.client.rpc;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jcode.ebookpedia.client.filter.Filter;
import com.jcode.ebookpedia.client.mvp.model.UserAccountDTO;
import com.jcode.ebookpedia.client.util.BeanArray;
import com.jcode.ebookpedia.client.util.PageResult;

public interface DatastoreServiceAsync {

	void deleteComment(Long id, AsyncCallback<Void> callback);

	void deleteMessage(Long id, AsyncCallback<Void> callback);

	void deletePost(Long id, AsyncCallback<Void> callback);

	void deleteUser(String email, AsyncCallback<Void> callback);

	void getComment(Long id, AsyncCallback<BeanArray> callback);

	void getCurrentUser(String destinationURL,
			AsyncCallback<UserAccountDTO> callback);

	void getMessage(Long id, AsyncCallback<BeanArray> callback);

	void getPost(Long id, boolean count, AsyncCallback<BeanArray> callback);

	void getUser(Long id, AsyncCallback<UserAccountDTO> callback);

	void getUser(String email, AsyncCallback<UserAccountDTO> callback);

	void processUrl(String url, AsyncCallback<String[]> callback);

	void saveComment(BeanArray comment, AsyncCallback<Void> callback);

	void saveMessage(BeanArray message, AsyncCallback<Void> callback);

	void savePost(BeanArray post, AsyncCallback<Void> callback);

	void saveUser(UserAccountDTO userAccount, AsyncCallback<Void> callback);

	void searchComments(List<Filter<?>> filters, int pageNumber,
			AsyncCallback<PageResult<BeanArray>> callback);

	void searchDocuments(List<Filter<?>> filters, int pageNumber,
			AsyncCallback<PageResult<BeanArray>> callback);

	void searchMessages(List<Filter<?>> filters, int pageNumber,
			AsyncCallback<PageResult<BeanArray>> callback);

	void searchUsers(List<Filter<?>> filters, int pageNumber,
			AsyncCallback<PageResult<BeanArray>> callback);

	void qualify(Long postId, int score, AsyncCallback<Void> callback);

}
