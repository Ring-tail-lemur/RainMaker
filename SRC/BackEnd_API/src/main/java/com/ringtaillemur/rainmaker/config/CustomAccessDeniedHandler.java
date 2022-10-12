package com.ringtaillemur.rainmaker.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.ringtaillemur.rainmaker.domain.enumtype.OauthUserLevel;
import com.ringtaillemur.rainmaker.dto.securitydto.LoginUser;
import com.ringtaillemur.rainmaker.dto.securitydto.SessionMemory;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Autowired
    SessionMemory sessionMemory;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        String requestSessionId = request.getHeader("SessionId");
        if(sessionMemory.loginUserHashMap.containsKey(requestSessionId)) {
            LoginUser nowLoginUser = sessionMemory.loginUserHashMap.get(requestSessionId);
            System.out.println(nowLoginUser);
            if(nowLoginUser.getUserLevel().equals(OauthUserLevel.FIRST_AUTH_USER)){
                response.setStatus(442);
            } else if(nowLoginUser.getUserLevel().equals(OauthUserLevel.AUTH_NOT_REPOSITORY_SELECT)) {
                response.setStatus(443);
            } else if(nowLoginUser.getUserLevel().equals(OauthUserLevel.AUTHED_HISTORY_COLLECT_NOT_ENDED_USER)) {
                response.setStatus(444);
            } else if (nowLoginUser.getUserLevel().equals(OauthUserLevel.FAILED)) {
                response.setStatus(445);
            } else {
                response.setStatus(401);
            }
        }
    }
}
