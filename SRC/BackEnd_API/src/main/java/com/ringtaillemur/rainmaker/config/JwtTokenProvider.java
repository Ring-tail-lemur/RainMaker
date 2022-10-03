package com.ringtaillemur.rainmaker.config;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.ringtaillemur.rainmaker.domain.enumtype.OauthUserLevel;
import com.ringtaillemur.rainmaker.dto.configdto.JwtTokenConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtTokenProvider {
	@Autowired
	private static JwtTokenConfig jwtTokenConfig;

	// private static final String JWT_SECRET = jwtTokenConfig.jwtSecret;
	// private static final String AUTHORITIES_KEY = jwtTokenConfig.jwtAuthoritiesKey;

	private static final String JWT_SECRET = "secretKey";
	private static final String AUTHORITIES_KEY = "ring-tail-lemur";
	// 토큰 유효시간
	private static final int JWT_EXPIRATION_MS = 604800000;

	// jwt 토큰 생성
	public static String generateToken(Authentication authentication) {

		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION_MS);
		String refreshToken = generateRefreshToken(authentication);

		return Jwts.builder()
			.setSubject((String)authentication.getPrincipal()) // 사용자
			.setIssuedAt(new Date()) // 현재 시간 기반으로 생성
			.setExpiration(expiryDate) // 만료 시간 세팅
			.signWith(SignatureAlgorithm.HS512, JWT_SECRET) // 사용할 암호화 알고리즘, signature에 들어갈 secret 값 세팅
			.compact();
	}
	public static String generateRefreshToken(Authentication authentication){
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + Integer.MAX_VALUE);
		return Jwts.builder().setIssuedAt(now).setExpiration(expiryDate)
			.signWith(SignatureAlgorithm.HS512, JWT_SECRET).compact();
	}
	public static String generateToken(Authentication authentication, OauthUserLevel oauthUserLevel) {

		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION_MS);
		return Jwts.builder()
			.setSubject((String)authentication.getPrincipal()) // 사용자
			.claim("ROLE", oauthUserLevel.toString())
			.setIssuedAt(new Date()) // 현재 시간 기반으로 생성
			.setExpiration(expiryDate) // 만료 시간 세팅
			.signWith(SignatureAlgorithm.HS512, JWT_SECRET) // 사용할 암호화 알고리즘, signature에 들어갈 secret 값 세팅
			.compact();
	}

	// Jwt 토큰에서 아이디 추출
	public static String getUserIdFromJWT(String token) {
		Claims claims = Jwts.parser()
			.setSigningKey(JWT_SECRET)
			.parseClaimsJws(token)
			.getBody();

		return claims.getSubject();
	}

	public static String getUserLevelFromJWT(String token) {
		Claims claims = Jwts.parser()
			.setSigningKey(JWT_SECRET)
			.parseClaimsJws(token)
			.getBody();
		Object a = claims.get("ROLE");
		return a.toString();
	}

	// Jwt 토큰 유효성 검사
	public static boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token);
			return true;
		} catch (SignatureException ex) {
			log.error("Invalid JWT signature");
		} catch (MalformedJwtException ex) {
			log.error("Invalid JWT token");
		} catch (ExpiredJwtException ex) {
			log.error("Expired JWT token");
		} catch (UnsupportedJwtException ex) {
			log.error("Unsupported JWT token");
		} catch (IllegalArgumentException ex) {
			log.error("JWT claims string is empty.");
		}
		return false;
	}

	public static boolean expiredCheck(String token){
		try{
			Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token);
			return false;
		}catch(ExpiredJwtException ex){
			return true;
		}finally {
			return false;
		}

	}

}
