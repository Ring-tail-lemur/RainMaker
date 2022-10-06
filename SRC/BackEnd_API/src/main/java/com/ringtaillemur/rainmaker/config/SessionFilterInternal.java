package com.ringtaillemur.rainmaker.config;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ringtaillemur.rainmaker.dto.securitydto.LoginUser;
import com.ringtaillemur.rainmaker.dto.securitydto.SessionMemory;

@Component
public class SessionFilterInternal extends OncePerRequestFilter {

	@Autowired
	SessionMemory sessionMemory;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		try{
			String requestSessionId = request.getHeader("SessionId");
			HttpSession httpSession = request.getSession();
			if(sessionMemory.loginUserHashMap.containsKey(requestSessionId)){
				LoginUser nowLoginUser = sessionMemory.loginUserHashMap.get(requestSessionId);
				Long oAuthUserRemoteId = nowLoginUser.getUserRemoteId();

				Set<SimpleGrantedAuthority> grantedAuthorities = new HashSet<>();
				SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(String.valueOf(nowLoginUser.getUserLevel()));
				grantedAuthorities.add(simpleGrantedAuthority);
				UserAuthentication authentication = new UserAuthentication(String.valueOf(nowLoginUser.getUserRemoteId()), null, grantedAuthorities);

				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
			else{
				request.setAttribute("unauthorization", "401 인증키 없음.");
			}
		}catch (Exception exception){
			logger.error(exception);
		}
		filterChain.doFilter(request,response);
	}


}
