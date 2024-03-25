package com.jcode.ebookpedia.security;

import static com.jcode.ebookpedia.security.Constants.TOKEN_BEARER_PREFIX;
import static com.jcode.ebookpedia.security.Constants.TOKEN_EXPIRATION_TIME;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jcode.ebookpedia.user.model.UserAccount;
import com.jcode.ebookpedia.user.service.UserAccountService;


@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
	
	// Dependencies
	
	private final UserAccountService userAccountService;
	
	
	// Constructor
	
	public AuthenticationController(UserAccountService userAccountService) {
		this.userAccountService = userAccountService;
	}
	

	@PostMapping("/refresh-token")
	public void refreshtoken(HttpServletRequest request, HttpServletResponse response) throws IOException {
		final ObjectMapper mapper = new ObjectMapper();
		final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (authHeader == null || !authHeader.startsWith(Constants.TOKEN_BEARER_PREFIX)) {
			return;
		}
		
		final String refreshToken = authHeader.replace(TOKEN_BEARER_PREFIX, "");
		final String username = TokenProvider.extractUsername(refreshToken);
		
		if (username != null) {
			UserAccount userAccount = userAccountService.findByUsername(username).orElseThrow();
			
			if (TokenProvider.isValidToken(refreshToken)) {
				Map<String, Object> claims = TokenProvider.getClaims(refreshToken);
				String token = TokenProvider.generateToken(userAccount.getUsername(), claims);
				
				AuthenticationResponse authenticationResponse = new AuthenticationResponse();
		    	authenticationResponse.setAccessToken(token);
		    	authenticationResponse.setRefreshToken(refreshToken);
		    	authenticationResponse.setTokenType(TOKEN_BEARER_PREFIX.trim());
		    	authenticationResponse.setExpiresIn(TOKEN_EXPIRATION_TIME / 1000);
				
				response.addHeader(HttpHeaders.AUTHORIZATION, TOKEN_BEARER_PREFIX + authenticationResponse.getAccessToken());
		    	response.addHeader(HttpHeaders.EXPIRES, String.valueOf(authenticationResponse.getExpiresIn()));
		    	response.addHeader(HttpHeaders.PROXY_AUTHORIZATION, TOKEN_BEARER_PREFIX + authenticationResponse.getRefreshToken());
		    	response.setContentType("application/json");
		    	mapper.writeValue(response.getOutputStream(), authenticationResponse);
			}
		}
	}
}
