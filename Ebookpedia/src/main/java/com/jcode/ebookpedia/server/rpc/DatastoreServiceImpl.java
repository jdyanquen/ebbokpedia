package com.jcode.ebookpedia.server.rpc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcode.ebookpedia.client.AppConstants;
import com.jcode.ebookpedia.client.exception.NotLoggedInException;
import com.jcode.ebookpedia.client.filter.CollectionFilter;
import com.jcode.ebookpedia.client.filter.Filter;
import com.jcode.ebookpedia.client.filter.FilterOperator;
import com.jcode.ebookpedia.client.filter.StringFilter;
import com.jcode.ebookpedia.client.mvp.model.CommentDTO;
import com.jcode.ebookpedia.client.mvp.model.DocumentDTO;
import com.jcode.ebookpedia.client.mvp.model.DocumentDetailsDTO;
import com.jcode.ebookpedia.client.mvp.model.PostDTO;
import com.jcode.ebookpedia.client.mvp.model.RoleEnum;
import com.jcode.ebookpedia.client.mvp.model.UserAccountDTO;
import com.jcode.ebookpedia.client.rpc.DatastoreService;
import com.jcode.ebookpedia.client.util.BeanArray;
import com.jcode.ebookpedia.client.util.PageResult;
import com.jcode.ebookpedia.server.dao.CommentDAO;
import com.jcode.ebookpedia.server.dao.DocumentDAO;
import com.jcode.ebookpedia.server.dao.DocumentDetailDAO;
import com.jcode.ebookpedia.server.dao.DocumentIndexDAO;
import com.jcode.ebookpedia.server.dao.PostDAO;
import com.jcode.ebookpedia.server.dao.PrivateMessageDAO;
import com.jcode.ebookpedia.server.dao.UserAccountDAO;
import com.jcode.ebookpedia.server.model.Category;
import com.jcode.ebookpedia.server.model.Comment;
import com.jcode.ebookpedia.server.model.Document;
import com.jcode.ebookpedia.server.model.DocumentDetail;
import com.jcode.ebookpedia.server.model.DocumentIndex;
import com.jcode.ebookpedia.server.model.FileType;
import com.jcode.ebookpedia.server.model.Language;
import com.jcode.ebookpedia.server.model.Post;
import com.jcode.ebookpedia.server.model.UserAccount;
import com.jcode.ebookpedia.server.util.SearchUtils;
import com.jcode.ebookpedia.server.util.SecurityManagerService;

@Service("datastoreService")
public class DatastoreServiceImpl implements DatastoreService {

	/** Logger tool */
	public static final Logger logger = Logger.getLogger("DatastoreServiceImpl");

	@Autowired
	private CommentDAO commentDAO;

	@Autowired
	private DocumentDAO documentDAO;

	@Autowired
	private DocumentDetailDAO documentDetailDAO;

	@Autowired
	private DocumentIndexDAO documentIndexDAO;

	@Autowired
	private PostDAO postDAO;

	@Autowired
	private PrivateMessageDAO privateMessageDAO;

	@Autowired
	private UserAccountDAO userAccountDAO;

	@Autowired
	private CatalogService catalogService;
	
	private ModelMapper mapper = new ModelMapper();

	public DatastoreServiceImpl() {
		// Default constructor
	}

	private DocumentIndex createDocumentIndex(Document document, DocumentDetail documentDetail) {

		try {
			DocumentIndex result = documentIndexDAO.get(document.getId());
			result = (result != null) ? result : new DocumentIndex();
			result.setIsbn(document.getIsbn());
			result.setEdition(document.getEdition());
			result.setFileType(document.getFileType());
			result.setYear(document.getYear());

			StringBuilder buffer = new StringBuilder();
			buffer.append(documentDetail.getDescription() + " ");
			buffer.append(document.getPublisher() + " ");
			buffer.append(document.getTitle() + " ");
			buffer.append(document.getAuthors() + " ");

			for (Category category : document.getCategories()) {
				buffer.append(category + " ");
			}

			result.setKeywords(SearchUtils.parseSearch(buffer.toString(), AppConstants.MAX_WORDS_TO_PUT_IN_INDEX));

			return result;

		} catch (Exception e) {
			logger.severe(e.getMessage());
			return null;
		}
	}

	private Set<String> createUserKeywords(UserAccountDTO userAccount) {
		String buffer = userAccount.getFullName() + " ";
		buffer += userAccount.getEmailAddress() + " ";
		buffer += userAccount.getRole() + " ";
		return SearchUtils.parseSearch(buffer, AppConstants.MAX_WORDS_TO_PUT_IN_INDEX);
	}

	@Override
	public void deleteComment(Long id) {
		commentDAO.deleteByPK(id);
	}

	@Override
	public void deleteMessage(Long id) {
		privateMessageDAO.deleteByPK(id);
	}

	@Override
	public void deletePost(Long id) {
		Document document = documentDAO.get(id);
		DocumentDetail documentDetail = documentDetailDAO.get(document.getDocumentDetail().getId());
		DocumentIndex documentIndex = documentIndexDAO.get(document.getId());
		Post post = postDAO.get(document.getPost().getId());
		
		postDAO.delete(post);
		documentDetailDAO.delete(documentDetail);
		documentIndexDAO.delete(documentIndex);
		documentDAO.delete(document);
	}

	@Override
	public void deleteUser(String email) {
		UserAccount account = userAccountDAO.getFromEmail(email);
/*
		if (account != null) {
			List<DocumentIndex> keys = documentIndexDAO.find(new NumberFilter(AppConstants.POSTER_ID, FilterOperator.EQUAL, account.getId()));

			for (Key<DocumentIndexDTO> key : keys) {

				DocumentDTO document = documentDAO.get(key.getId());
				DocumentIndexDTO documentIndex = documentIndexDAO.get(key.getId());
				DocumentDetailsDTO documentDetails = documentDetailDAO.get(document.getDocumentDetailstKey());
				PostDTO post = postDAO.get(document.getPostKey());

				List<Key<CommentDTO>> commentsKeys = commentDAO
						.find(new NumberFilter(AppConstants.POST_ID, FilterOperator.EQUAL, post.getId()));

				for (Key<CommentDTO> key2 : commentsKeys) {
					commentDAO.delete(key2);
				}

				documentIndexDAO.delete(documentIndex);
				documentDetailDAO.delete(documentDetails);
				documentDAO.delete(document);
				postDAO.delete(post);
			}

			userAccountDAO.delete(account);
		}*/
	}

	@Override
	public BeanArray getComment(Long id) {
		Comment comment = commentDAO.get(id);
		return new BeanArray(mapper.map(comment, CommentDTO.class));
	}

	@Override
	public UserAccountDTO getCurrentUser(String destinationURL) {

		UserAccount userAccount = null;

		if (SecurityManagerService.getCurrentUser() != null) {
			String email = SecurityManagerService.getCurrentUser();
			userAccount = userAccountDAO.getFromEmail(email);
			userAccount = (userAccount != null) ? userAccount : new UserAccount();
			userAccount.setEmail(email);
			// TODO user.setLogoutURL(SecurityManagerService.getLogoutURL(destinationURL));
			userAccount.setLastActivity(new Date());
			userAccountDAO.save(userAccount);

		} else {
			userAccount = new UserAccount();
			// TODO user.setLoginURL(SecurityManagerService.getLoginURL(destinationURL));
			//userAccount.setRole(RoleEnum.GUEST);
		}
		
		UserAccountDTO userAccountDTO = mapper.map(userAccount, UserAccountDTO.class);
		userAccountDTO.setSignedIn(userAccount.getId() != null);
		userAccountDTO.setRole(userAccount.getId() != null ? userAccountDTO.getRole() : RoleEnum.GUEST);
		return userAccountDTO;
	}

	@Override
	public BeanArray getMessage(Long id) {
		return null;
	}

	@Override
	public BeanArray getPost(Long id, boolean count) {
		Document document = documentDAO.get(id);
		DocumentDetail documentDetail = documentDetailDAO.get(document.getDocumentDetail().getId());
		Post post = postDAO.get(document.getPost().getId());
		
		if (count) {
			post.increaseViews();
			postDAO.save(post);
		}
		PostDTO postDTO = mapper.map(post, PostDTO.class);
		DocumentDTO documentDTO = mapper.map(document, DocumentDTO.class);
		DocumentDetailsDTO documentDetailsDTO = mapper.map(documentDetail, DocumentDetailsDTO.class);
		
		return new BeanArray(postDTO, documentDTO, documentDetailsDTO);
	}

	@Override
	public UserAccountDTO getUser(Long id) {
		return mapper.map(userAccountDAO.get(id), UserAccountDTO.class);
	}

	@Override
	public UserAccountDTO getUser(String email) {
		return mapper.map(userAccountDAO.getFromEmail(email), UserAccountDTO.class);
	}

	private List<Filter<?>> processDocumentSearchFilters(List<Filter<?>> filters) {

		if (filters == null) {
			filters = Collections.emptyList();
		}
		boolean advancedSearch = false;
		String buffer = "";
		List<Filter<?>> result = new ArrayList<Filter<?>>(1);

		for (Filter<?> filter : filters) {
			if (filter.getFieldName().equals(AppConstants.KEYWORDS)) {
				buffer += filter.getValue() + " ";

			} else if (filter.getFieldName().equals(AppConstants.TITLE)
					|| filter.getFieldName().equals(AppConstants.AUTHOR)
					|| filter.getFieldName().equals(AppConstants.PUBLISHER)) {

				buffer += filter.getValue() + " ";
				advancedSearch = true;

			} else if (filter.getFieldName().equals(AppConstants.ISBN)
					|| filter.getFieldName().equals(AppConstants.YEAR)
					|| filter.getFieldName().equals(AppConstants.EDITION)
					|| filter.getFieldName().equals(AppConstants.FILE_TYPE)) {

				result.add(filter);
				advancedSearch = true;

			} else if (filter.getFieldName().equals(AppConstants.POSTER_ID)) {
				result.add(filter);
			}
		}

		Set<String> keywords = SearchUtils.parseSearch(buffer, AppConstants.MAX_WORDS_TO_SEARCH);

		if (advancedSearch && !keywords.isEmpty()) {
			for (String string : keywords) {
				result.add(new StringFilter(AppConstants.KEYWORDS, FilterOperator.EQUAL, string));
			}

		} else if (!keywords.isEmpty()) {
			result.add(new CollectionFilter(AppConstants.KEYWORDS, FilterOperator.IN, keywords));
		}
		return result;
	}

	@Override
	public String[] processUrl(String url) {

		boolean validUrl = false;
		String fileSize = null;
		String fileType = null;

		try {

			HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();

			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			String line;

			while ((line = in.readLine()) != null) {
				if (line.contains("reqPnl")) {
					in.readLine();
					in.readLine();

					fileType = in.readLine();
					fileType = fileType.substring(fileType.lastIndexOf('.') + 1, fileType.lastIndexOf('&')).trim();

					in.readLine();

					fileSize = in.readLine();
					fileSize = fileSize.substring(0, fileSize.lastIndexOf('<')).trim();
					validUrl = true;
					break;
				}
			}
			in.close();

			if (validUrl)
				return new String[] { fileType, fileSize };

		} catch (IOException e) {
			// logger.severe(e.getMessage());
		}
		return null;
	}

	private List<Filter<?>> processUserSerachFilters(List<Filter<?>> filters) {
		if (filters == null) {
			filters = Collections.emptyList();
		}
		StringBuilder buffer = new StringBuilder();
		List<Filter<?>> result = new ArrayList<>(1);

		for (Filter<?> filter : filters) {
			if (filter.getFieldName().equals(AppConstants.KEYWORDS)) {
				buffer.append(filter.getValue());
				buffer.append(" ");
			}
		}

		Set<String> keywords = SearchUtils.parseSearch(buffer.toString(), AppConstants.MAX_WORDS_TO_SEARCH);

		if (!keywords.isEmpty()) {
			result.add(new CollectionFilter(AppConstants.KEYWORDS, FilterOperator.IN, keywords));
		}
		return result;
	}

	@Override
	public void saveComment(BeanArray data) throws NotLoggedInException {
		if (SecurityManagerService.getCurrentUser() != null && data.getBean(0) instanceof CommentDTO) {

			UserAccount userAccount = userAccountDAO.getFromEmail(SecurityManagerService.getCurrentUser());

			Comment comment = (Comment) data.getBean(0);
			comment.setPublishedBy(userAccount);
			commentDAO.save(comment);

		} else {
			throw new NotLoggedInException();
		}

	}

	@Override
	public void saveMessage(BeanArray message) throws NotLoggedInException {
		if (SecurityManagerService.getCurrentUser() != null) {

		} else {
			throw new NotLoggedInException();
		}
	}

	@Override
	public void savePost(BeanArray data) throws NotLoggedInException {
		if (SecurityManagerService.getCurrentUser() != null && data.getBean(0) instanceof PostDTO
				&& data.getBean(1) instanceof DocumentDTO && data.getBean(2) instanceof DocumentDetailsDTO) {

			try {
				PostDTO copyPost = (PostDTO) data.getBean(0);
				DocumentDTO copyDocument = (DocumentDTO) data.getBean(1);
				DocumentDetailsDTO copyDocumentDetails = (DocumentDetailsDTO) data.getBean(2);

				// post info
				Post post = postDAO.get(copyPost.getId());
				post = (post != null) ? post : new Post();
				String email = SecurityManagerService.getCurrentUser();
				post.setPostedBy(userAccountDAO.getFromEmail(email));

				// document
				Document document = documentDAO.get(copyDocument.getId());
				document = (document != null) ? document : new Document();
				document.setAuthors(copyDocument.getAuthors());
				document.setCategories(this.getCategories(copyDocument));
				document.setCoverId(copyDocument.getCoverId());
				document.setEdition(copyDocument.getEdition());
				document.setFileSize(copyDocument.getFileSize());
				document.setFileType(this.getFileType(copyDocument));
				document.setIsbn(copyDocument.getIsbn());
				document.setLanguage(this.getLanguage(copyDocument));
				document.setPublisher(copyDocument.getPublisher());
				document.setTitle(copyDocument.getTitle());
				document.setPages(copyDocument.getPages());
				document.setYear(copyDocument.getYear());

				// doc details
				DocumentDetail documentDetail = documentDetailDAO.get(copyDocumentDetails.getId());
				documentDetail = (documentDetail != null) ? documentDetail : new DocumentDetail();
				documentDetail.setDescription(copyDocumentDetails.getDescription());
				// TODO documentDetail.setLink(copyDocumentDetails.getLink());

				// doc index
				DocumentIndex documentIndex = this.createDocumentIndex(document, documentDetail);
				documentIndex.setPosterId(post.getPostedBy().getId());

				document.setPost(postDAO.save(post));
				document.setDocumentDetail(documentDetailDAO.save(documentDetail));
				documentIndex.setDocument(documentDAO.save(document));
				documentIndexDAO.save(documentIndex);

			} catch (Exception e) {
				logger.severe("Failed to save post... " + e.getMessage());
			}

		} else {
			throw new NotLoggedInException();
		}
	}

	private Set<Category> getCategories(DocumentDTO documentDTO) {
		Set<Category> categories = new HashSet();
		documentDTO.getCategories().forEach(c1 -> categories.addAll(catalogService.getCategories().stream()
				.filter(c2 -> c2.getName().equalsIgnoreCase(c1.name())).collect(Collectors.toSet())));
		return categories;
	}

	private Language getLanguage(DocumentDTO documentDTO) {
		return catalogService.getLanguages().stream()
				.filter(l -> l.getIsoCode().equalsIgnoreCase(documentDTO.getLanguage().name())).findFirst()
				.orElseThrow();
	}
	
	private FileType getFileType(DocumentDTO documentDTO) {
		return catalogService.getFileTypes().stream()
				.filter(ft -> ft.getName().equalsIgnoreCase(documentDTO.getFileType().name())).findFirst()
				.orElseThrow();
	}

	@Override
	public void saveUser(UserAccountDTO userAccountDTO) throws NotLoggedInException {
		if (SecurityManagerService.getCurrentUser() != null) {
			try {
				UserAccount userAccount = userAccountDAO.get(userAccountDTO.getId());
				userAccount = (userAccount != null) ? userAccount : new UserAccount();
				userAccount.setBirthDate(userAccountDTO.getBirthDate());
				userAccount.setEmail(userAccountDTO.getEmailAddress());
				userAccount.setFirstName(userAccountDTO.getFirstName());
				userAccount.setLastName(userAccountDTO.getLastName());
				userAccount.setMemberFrom(userAccountDTO.getMemberFrom());
				userAccount.setSsoId(userAccountDTO.getNickname());
				userAccount.setAvatar(userAccountDTO.getPhotoId());
				//save.setRole(userAccount.getRole());
				//save.setUserStatus(userAccount.getUserStatus());
				//save.setWebsiteURL(userAccount.getWebsiteURL());
				//userAccount.setKeywords(createUserKeywords(userAccount));
				userAccountDAO.save(userAccount);

			} catch (Exception e) {
				logger.warning("Failed to save user... " + e.getMessage());
			}

		} else {
			throw new NotLoggedInException();
		}
	}

	@Override
	public PageResult<BeanArray> searchComments(List<Filter<?>> filters, int pageNumber) {

		try {
			pageNumber = (pageNumber <= 0) ? 1 : pageNumber;
			int offset = AppConstants.RECORDS_PER_SHORT_PAGE * (pageNumber - 1);
			int limit = AppConstants.RECORDS_PER_SHORT_PAGE + offset;

			List<BeanArray> data = new ArrayList<>();
			List<Comment> comments = commentDAO.find(offset, limit, "createdAt DESC", filters);

			for (Comment comment : comments) {
				UserAccount user = comment.getPublishedBy();

				String nickname = "Anonymous";
				String photoId = null;

//				if (user != null && user.getNickname() != null) {
//					nickname = user.getNickname();
//					photoId = user.getPhotoId();
//				}
//				comment.setNickname(nickname);
//				comment.setPublisherPhotoId(photoId);
				data.add(new BeanArray(mapper.map(comment, CommentDTO.class)));
			}
			int totalRecords = 10; // TODO
			return new PageResult<>(data, offset, totalRecords);

		} catch (Exception e) {
			logger.severe("Invalid query. <Search Comments> " + e.getMessage());
		}
		return new PageResult<>();
	}

	@Override
	public PageResult<BeanArray> searchDocuments(List<Filter<?>> filters, int pageNumber) {

		try {
			pageNumber = (pageNumber <= 0) ? 1 : pageNumber;
			int offset = AppConstants.RECORDS_PER_PAGE * (pageNumber - 1);
			int limit = AppConstants.RECORDS_PER_PAGE + offset;

			List<BeanArray> data = new ArrayList<>();
			List<DocumentIndex> docsKeys = documentIndexDAO.find(offset, limit,
					processDocumentSearchFilters(filters));

			for (DocumentIndex documentIndex : docsKeys) {
				Document document = documentIndex.getDocument();
				Post post = document.getPost();
				UserAccount user = post.getPostedBy();
				//post.setPosterNickname(user.getNickname());
				data.add(new BeanArray(mapper.map(post, PostDTO.class), mapper.map(document, DocumentDTO.class)));
			}
			int totalRecords = 10; // TODO
			return new PageResult<>(data, offset, totalRecords);

		} catch (Exception e) {
			logger.severe("Invalid query. <Search Docs> " + e.getMessage());
		}
		return new PageResult<>();
	}

	@Override
	public PageResult<BeanArray> searchMessages(List<Filter<?>> filters, int pageNumber) {
		return null;
	}

	@Override
	public PageResult<BeanArray> searchUsers(List<Filter<?>> filters, int pageNumber) {
		try {
			pageNumber = (pageNumber <= 0) ? 1 : pageNumber;
			int offset = AppConstants.RECORDS_PER_PAGE * (pageNumber - 1);
			int limit = AppConstants.RECORDS_PER_PAGE + offset;

			List<BeanArray> data = new ArrayList<>();
			List<UserAccount> userAccounts = userAccountDAO.find(offset, limit, processUserSerachFilters(filters));

			for (UserAccount userAccount : userAccounts) {
				data.add(new BeanArray(mapper.map(userAccount, UserAccountDTO.class)));
			}
			int totalRecords = 100; // TODO
			return new PageResult<>(data, offset, totalRecords);

		} catch (Exception e) {
			logger.severe("Invalid query. <Search Users> " + e.getMessage());
		}
		return new PageResult<>();
	}

	public void qualify(Long postId, int score) {
		if (postId == null)
			return;

		Post post = postDAO.get(postId);
		post.increaseVotersCounter();
		post.increaseScore(score);
		postDAO.save(post);
	}

}
