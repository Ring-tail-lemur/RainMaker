package com.ringtaillemur.rainmaker.util.jwt;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Component
public class JwtUtils {
    public String getJwtFromRequest(HttpServletRequest request) {
        Enumeration<String> headers = request.getHeaders("authorization");


        Enumeration eHeader = request.getHeaderNames();
        while(eHeader.hasMoreElements()) {
            String request_name = (String) eHeader.nextElement();
            String request_value = request.getHeader(request_name);
            System.out.println(request_name + " ," + request_value);
        }
        Enumeration<String> headerNames = request.getHeaderNames();
        String bearerToken = request.getHeader("authorization");
        if (StringUtils.isNotEmpty(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring("Bearer ".length());
        }
        return null;
    }
}
