package com.example.hotel_back.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

	//	private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	private final long validityInMs = 1000 * 60 * 60; // 1 hour
	private Key key;

	@Value("${jwt.secret}")
	private String secret;

	@PostConstruct
	public void init() {
		key = Keys.hmacShaKeyFor(secret.getBytes());
	}

	public String createToken(String email) {
		Date now = new Date();
		Date validity = new Date(now.getTime() + validityInMs);

		return Jwts.builder()
						.setSubject(email)
						.setIssuedAt(now)
						.setExpiration(validity)
						.signWith(key, SignatureAlgorithm.HS256)
						.compact();
	}

	public String createRefreshToken(String email) {
		Date now = new Date();
		Date expiry = new Date(now.getTime() + 1000L * 60 * 60 * 24 * 7); // 7 days
		return Jwts.builder()
						.setSubject(email)
						.setIssuedAt(now)
						.setExpiration(expiry)
						.signWith(key, SignatureAlgorithm.HS256)
						.compact();
	}

	public String getSubject(String token) {
		return Jwts.parserBuilder()
						.setSigningKey(key)
						.build()
						.parseClaimsJws(token)
						.getBody()
						.getSubject();
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Claims getClaims(String token) {
		return Jwts.parserBuilder()
						.setSigningKey(key)
						.build()
						.parseClaimsJws(token)
						.getBody();
	}

}
