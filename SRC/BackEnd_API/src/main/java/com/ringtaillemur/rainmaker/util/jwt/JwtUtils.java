package com.ringtaillemur.rainmaker.util.jwt;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {
	public String getJwtFromRequest(HttpServletRequest request) {
		Enumeration<String> headers = request.getHeaders("authorization");

		Enumeration<String> eHeader = request.getHeaderNames();
		while (eHeader.hasMoreElements()) {
			String requestName = eHeader.nextElement();
			String requestValue = request.getHeader(requestName);
		}
		Enumeration<String> headerNames = request.getHeaderNames();
		String bearerToken = request.getHeader("authorization");
		if (StringUtils.isNotEmpty(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring("Bearer ".length());
		}
		return null;
	}
}
