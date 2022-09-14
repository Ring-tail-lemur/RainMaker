package com.ringtaillemur.rainmaker.config;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import groovy.util.logging.Slf4j;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
		throws ServletException, IOException {
		try {
			String jwt = getJwtFromRequest(request); //request에서 jwt 토큰을 꺼낸다.
			if (StringUtils.isNotEmpty(jwt) && JwtTokenProvider.validateToken(jwt)) {
				String userId = JwtTokenProvider.getUserIdFromJWT(jwt); //jwt에서 사용자 id를 꺼낸다.
				String userLevel = JwtTokenProvider.getUserLevelFromJWT(jwt);//jwt에서 사용자 level을 꺼낸다.

				Set<SimpleGrantedAuthority> grantedAuthorities = new HashSet<>();
				SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(userLevel);
				grantedAuthorities.add(simpleGrantedAuthority);
				UserAuthentication authentication = new UserAuthentication(userId, userLevel,
					grantedAuthorities); //id를 인증한다.
				authentication.setDetails(
					new WebAuthenticationDetailsSource().buildDetails(request)); //기본적으로 제공한 details 세팅
				SecurityContextHolder.getContext()
					.setAuthentication(authentication); //세션에서 계속 사용하기 위해 securityContext에 Authentication 등록
			} else {
				if (StringUtils.isEmpty(jwt)) {
					request.setAttribute("unauthorization", "401 인증키 없음.");
				}

				if (JwtTokenProvider.validateToken(jwt)) {
					request.setAttribute("unauthorization", "401-001 인증키 만료.");
				}
			}
		} catch (Exception ex) {
			logger.error("Could not set user authentication in security context", ex);
		}

		filterChain.doFilter(request, response);
	}

	private String getJwtFromRequest(HttpServletRequest request) {
		Enumeration<String> headers = request.getHeaders("authorization");

		Enumeration<String> eHeader = request.getHeaderNames();
		while (eHeader.hasMoreElements()) {
			String requestName = eHeader.nextElement();
			String requestValue = request.getHeader(requestName);
		}

		Enumeration<String> headerNames = request.getHeaderNames();
		String bearerToken = request.getHeader("authorization");
		//        if(bearerToken != null) {
		//            bearerToken.substring(0,44);
		//        }
		if (StringUtils.isNotEmpty(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring("Bearer ".length());
		}
		return null;
	}
}
