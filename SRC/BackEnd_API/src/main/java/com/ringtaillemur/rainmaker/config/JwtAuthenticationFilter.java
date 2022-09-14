package com.ringtaillemur.rainmaker.config;

import groovy.util.logging.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

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


                System.out.println("Jwt Token now in filter : "+ userLevel);

                Set<SimpleGrantedAuthority> grantedAuthorities = new HashSet<>();
                SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(userLevel);
                grantedAuthorities.add(simpleGrantedAuthority);
                UserAuthentication authentication = new UserAuthentication(userId, userLevel, grantedAuthorities); //id를 인증한다.
                System.out.println("grantedAuthorities : "+grantedAuthorities.toString());
                System.out.println("authentication : "+authentication.toString());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); //기본적으로 제공한 details 세팅
                SecurityContextHolder.getContext().setAuthentication(authentication); //세션에서 계속 사용하기 위해 securityContext에 Authentication 등록
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


        Enumeration eHeader = request.getHeaderNames();
        while(eHeader.hasMoreElements()) {
            String request_name = (String) eHeader.nextElement();
            String request_value = request.getHeader(request_name);
            System.out.println(request_name + " ," + request_value);
            if(request_name == "authorization") {
                System.out.println("====Author ==== : " + request_name + " ," + request_value);
            }
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
