package com.jcode.ebookpedia.security;

import static com.jcode.ebookpedia.security.Constants.AUTHORITIES_KEY;
import static com.jcode.ebookpedia.security.Constants.REFRESH_TOKEN_EXPIRATION_TIME;
import static com.jcode.ebookpedia.security.Constants.SECRET_KEY;
import static com.jcode.ebookpedia.security.Constants.TOKEN_EXPIRATION_TIME;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

public class TokenProvider {

    private TokenProvider() {
        // Hide constructor
    }
    
    public static String generateToken(String subject, Map<String, Object> claims) {
		return buildToken(claims, subject, TOKEN_EXPIRATION_TIME);
	}
    
    public static String generateRefreshToken(String subject, Map<String, Object> claims) {
		return buildToken(claims, subject, REFRESH_TOKEN_EXPIRATION_TIME);
	}
    
    private static String buildToken(Map<String, Object> claims, String subject, long expiration) {
    	return Jwts.builder()
				.setClaims(claims)
				.setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY)))
				.compact();
    }

    public static UsernamePasswordAuthenticationToken getAuthentication(final String token) {
    	final String username = extractUsername(token);
        final Collection<SimpleGrantedAuthority> authorities = extractAuthorities(token);
        return new UsernamePasswordAuthenticationToken(username, null, authorities);
    }
    
    public static boolean isTokenExpired(String token) {
    	 try {
    		 Date expiration = extractClaim(token, Claims::getExpiration);
    		 return expiration != null && expiration.before(new Date());
	    } catch (ExpiredJwtException e) {
	        return true;
	    }
    }
    
    public static boolean isValidToken(String token) {
    	return !isTokenExpired(token);
    }
    
    
    public static Claims getClaims(final String token) {
        return Jwts.parserBuilder().setSigningKey(Decoders.BASE64.decode(SECRET_KEY))
        		.build().parseClaimsJws(token).getBody();
    }
    
    public static <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = getClaims(token);
        return claimResolver.apply(claims);
    }
    
    public static String extractUsername(String token) {
    	return extractClaim(token, Claims::getSubject);
    }
    
    public static Collection<SimpleGrantedAuthority> extractAuthorities(String token) {
    	final Claims claims = getClaims(token);
    	final String authorities = claims.containsKey(AUTHORITIES_KEY) ? (String) claims.get(AUTHORITIES_KEY) : ""; 
    	return Arrays.stream(authorities.split(","))
         		.map(SimpleGrantedAuthority::new)
         		.collect(Collectors.toList());
    }

}
