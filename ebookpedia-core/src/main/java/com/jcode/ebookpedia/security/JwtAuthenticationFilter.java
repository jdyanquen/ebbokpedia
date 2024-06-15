package com.jcode.ebookpedia.security;

import static com.jcode.ebookpedia.security.Constants.AUTHORITIES_KEY;
import static com.jcode.ebookpedia.security.Constants.TOKEN_BEARER_PREFIX;
import static com.jcode.ebookpedia.security.Constants.TOKEN_EXPIRATION_TIME;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.RuntimeJsonMappingException;
import com.jcode.ebookpedia.user.model.UserAccount;


public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
        throws AuthenticationException {
        try {
            UserAccount userAccount = null;

            if (request.getParameter("username") != null && request.getParameter("password") != null) {
                userAccount = new UserAccount();
                userAccount.setUsername(request.getParameter("username"));
                userAccount.setPassword(request.getParameter("password"));
            } else {
                userAccount = new ObjectMapper().readValue(request.getInputStream(), UserAccount.class);
            }

            return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(
                userAccount.getUsername(), userAccount.getPassword(), Collections.emptyList()));
        } catch (IOException ex) {
            throw new RuntimeJsonMappingException(ex.getMessage());
        } catch (AuthenticationException ex) {
            throw ex;
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth) throws IOException, ServletException {
    	ObjectMapper mapper = new ObjectMapper();
    	Map<String, Object> claims = Map.of(AUTHORITIES_KEY, auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(",")));
    	String subject = ((User) auth.getPrincipal()).getUsername();
    	String token = TokenProvider.generateToken(subject, claims);
    	String refreshToken = TokenProvider.generateRefreshToken(subject, claims);
    	
    	AuthenticationResponse authenticationResponse = new AuthenticationResponse();
    	authenticationResponse.setAccessToken(token);
    	authenticationResponse.setRefreshToken(refreshToken);
    	authenticationResponse.setTokenType(TOKEN_BEARER_PREFIX.trim());
    	authenticationResponse.setExpiresIn(TOKEN_EXPIRATION_TIME / 1000);
    	
    	response.addHeader(HttpHeaders.AUTHORIZATION, TOKEN_BEARER_PREFIX + authenticationResponse.getAccessToken());
    	response.addHeader(HttpHeaders.PROXY_AUTHORIZATION, TOKEN_BEARER_PREFIX + authenticationResponse.getRefreshToken());
    	response.addHeader(HttpHeaders.EXPIRES, String.valueOf(authenticationResponse.getExpiresIn()));
    	response.setContentType("application/json");
    	mapper.writeValue(response.getOutputStream(), authenticationResponse);
    }
}
