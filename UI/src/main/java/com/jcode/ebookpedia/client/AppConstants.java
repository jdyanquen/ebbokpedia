package com.jcode.ebookpedia.client;

public class AppConstants {

	public static final String ADMIN_EMAIL = "admin@ebookpedia.com";

	public static final String ANONYMOUS_PREFIX = "Anonymous";
	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm";
	public static final String SERVER_DATE_FORMAT = "yyyy-MM-dd HH:mm z";
	public static final String SERVER_TIME_ZONE = "GMT";

	public static final Integer MAX_FILE_SIZE = 1 * 1024 * 1024;
	public static final Integer MAX_WORDS_TO_PUT_IN_INDEX = 200;
	public static final Integer MAX_WORDS_TO_SEARCH = 10;
	public static final Integer RECORDS_PER_PAGE = 10;
	public static final Integer RECORDS_PER_SHORT_PAGE = 5;
	public static final Integer DEFAULT_PAGE = 1;

	public static final Integer HOME_TAB_INDEX = 0;
	public static final Integer MY_CATALOG_TAB_INDEX = 1;
	public static final Integer MESSAGES_TAB_INDEX = 2;
	public static final Integer ADMIN_TAB_INDEX = 3;

	public static final String HOME_TOKEN = "home";
	public static final String CATALOG_TOKEN = "home/catalog/";
	public static final String MY_CATALOG_TOKEN = "mycatalog";
	public static final String MESSAGES_TOKEN = "messages";
	public static final String ADMIN_USERS_TOKEN = "admin/users";
	public static final String ADMIN_REPORTS_TOKEN = "admin/reports";

	public static final String SEARCH_TOKEN = "home/search/";
	public static final String SEARCH_MY_CATALOG_TOKEN = "mycatalog/search/";
	public static final String SEARCH_MESSAGES_TOKEN = "messages/search/";
	public static final String SEARCH_USERS_TOKEN = "admin/users/search/";

	public static final String EDIT_POST_TOKEN = "mycatalog/edit";
	public static final String VIEW_POST_TOKEN = "view";

	public static final String ISBN_REGEX = "^[0-9]+[0-9]*$";
	public static final String TITLE_REGEX = "[\\(\\)\\&\\+-,.'@ a-zA-Z0-9]+"; // edit
	public static final String AUTHORS_REGEX = "^[A-Z]'?[-, a-zA-Z]+$";
	public static final String FIRST_NAME_REGEX = "^[A-Z]'?[- a-zA-Z]+$";
	public static final String LAST_NAME_REGEX = "^[A-Z]'?[- a-zA-Z]+$";

	public static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
	public static final String UNSIGNED_INTEGER_REGEX = "^[1-9]+[0-9]*$";
	public static final String UNSIGNED_FLOAT_REGEX = "\\A(\\d)+([,|\\.](\\d)+)?$";
	public static final String URL_REGEX = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
	public static final String DATE_REGEX = "(0[1-9]|[1-9]|[12][0-9]|3[01])[ \\-/.](0[1-9]|1[012]|[1-9])[ \\-/.](19|20)?\\d{2}";

	public static final String AUTHORS_EXAMPLE = "example: Gerard Blanchet, Maurice Charbit";
	public static final String ISBN_EXAMPLE = "example: 0416348291";
	public static final String NAME_EXAMPLE = "example: James Bond";
	public static final String EMAIL_EXAMPLE = "example: james.bond@domain.com";
	public static final String INTEGER_EXAMPLE = "example: 2012";
	public static final String FLOAT_EXAMPLE = "example: 1.2345";
	public static final String URL_EXAMPLE = "example: http://www.domain.com";
	public static final String DATE_EXAMPLE = "format: dd/mm/yyyy - example: 31/12/2010";

	public static final String INVALID_IMAGE_MSG = "Please select a valid file";
	public static final String INVALID_NAME_MSG = "Enter a valid name";
	public static final String INVALID_EMAIL_MSG = "Enter a valid email address";
	public static final String INVALID_NUMBER_MSG = "Enter a valid number";
	public static final String INVALID_TITLE_MSG = "Enter a valid title";
	public static final String INVALID_URL_MSG = "Enter a valid URL address";
	public static final String INVALID_DATE_MSG = "Enter a valid date";
	public static final String INVALID_ISBN_MSG = "Enter a valid isbn number";
	
	public static final String SUSPENDED_USER_MSG ="You are suspended user, you can't create / edit post. Please contact the system administrator";

	public static final String NO_COVER_URL = "images/no_cover.jpg";
	public static final String NO_PHOTO_URL = "images/no_photo.jpg";

	public static final String PAGER_BACK = "images/pager-back.png";
	public static final String PAGER_BACK_OFF = "images/pager-back-off.png";
	public static final String PAGER_FIRST = "images/pager-first.png";
	public static final String PAGER_FIRST_OFF = "images/pager-first-off.png";
	public static final String PAGER_LAST = "images/pager-last.png";
	public static final String PAGER_LAST_OFF = "images/pager-last-off.png";
	public static final String PAGER_NEXT = "images/pager-next.png";
	public static final String PAGER_NEXT_OFF = "images/pager-next-off.png";

	public static final String POST_ID = "postId";
	public static final String POSTER_ID = "posterId";
	public static final String KEYWORDS = "keywords";
	public static final String TITLE = "title";
	public static final String AUTHOR = "author";
	public static final String ISBN = "isbn";
	public static final String YEAR = "year";
	public static final String EDITION = "edition";
	public static final String PUBLISHER = "publisher";
	public static final String PUBLISHER_KEY = "publisherKey";
	public static final String FILE_TYPE = "fileType";

	private AppConstants() {
		// Hide constructor
	}
}
