package com.jcode.ebookpedia.security;

public class Constants {

    // Spring Security

    public static final String REGISTER_URL = "/register";

    public static final String PROCESSES_URL = "/api/auth/login";

    public static final String TOKEN_BEARER_PREFIX = "Bearer ";

    // JWT
    public static final String AUTHORITIES_KEY = "authorities";
    
    public static final String ISSUER_INFO = "https://www.ebookpedia.co/";

    public static final String SECRET_KEY = "dGhpc19zdHJpbmdfX3MzY3IzdF9pc19mb3JfdGVzdGluZ19vbmx5";

    public static final long TOKEN_EXPIRATION_TIME =  3_600_000; // 24 hours // 20 minutes
        
    public static final long REFRESH_TOKEN_EXPIRATION_TIME = 3_600_000L * 24L; // 24 hours

    private Constants() {
        // Hide constructor
    }

}
