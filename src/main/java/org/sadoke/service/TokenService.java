package org.sadoke.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.sadoke.ApplicationProperties;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class TokenService {
	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60 * 2L;

	private static final Integer THREE_SECONDS = 3000;
	private final ApplicationProperties applicationProperties;

	private String doGenerateToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
				.signWith(SignatureAlgorithm.HS512, applicationProperties.getSecret()).compact();
	}

	public String generateToken(String name) {
		Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, name);
	}

	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(applicationProperties.getSecret()).parseClaimsJws(token).getBody();
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	public Boolean validateToken(String token) {
		if (token == null)
			return false;
		try {
			final String username = getUsernameFromToken(token);
			log.info("username = {}", username);
			boolean isValid = true && !isTokenExpired(token);
			return (isValid);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			log.error("Tried login with token {}", token);
			return false;

		}
	}

}