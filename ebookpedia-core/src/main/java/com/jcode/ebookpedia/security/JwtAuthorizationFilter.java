package com.jcode.ebookpedia.security;

import static com.jcode.ebookpedia.security.Constants.TOKEN_BEARER_PREFIX;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        try {
            String authenticationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (authenticationHeader != null && authenticationHeader.startsWith(TOKEN_BEARER_PREFIX)) {
                String token = authenticationHeader.replace(TOKEN_BEARER_PREFIX, "");
                if (!TokenProvider.extractAuthorities(token).isEmpty()) {
                    SecurityContextHolder.getContext().setAuthentication(TokenProvider.getAuthentication(token));
                } else {
                    SecurityContextHolder.clearContext();
                }
            } else {
                SecurityContextHolder.clearContext();
            }
            chain.doFilter(request, response);

        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        }
    }

}
