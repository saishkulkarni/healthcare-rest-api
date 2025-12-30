package com.hms.healthcare.util;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.hms.healthcare.exception.GlobalExceptionHandler;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	private final Key key;
	@Value("${jwt.expirytime}")
	private long tokenExpirytTime;

	public JwtUtil(@Value("${jwt.secret}") String secretKey, GlobalExceptionHandler globalExceptionHandler) {
		this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
	}

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		String role = userDetails.getAuthorities().iterator().next().getAuthority();
		claims.put("role", role);

		return Jwts.builder().claims(claims).subject(userDetails.getUsername())
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + tokenExpirytTime)).signWith(key).compact();
	}

}