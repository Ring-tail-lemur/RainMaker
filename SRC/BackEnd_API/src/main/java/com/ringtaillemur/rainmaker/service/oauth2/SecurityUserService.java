package com.ringtaillemur.rainmaker.service.oauth2;

import com.ringtaillemur.rainmaker.config.JwtTokenProvider;
import com.ringtaillemur.rainmaker.domain.OAuthUser;
import com.ringtaillemur.rainmaker.domain.enumtype.OauthUserLevel;
import com.ringtaillemur.rainmaker.repository.OAuthRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class SecurityUserService {

    private OAuthRepository oAuthRepository;
    public OAuthUser changeUserAuth(OAuthUser oAuthUser, OauthUserLevel oauthUserLevel){
        oAuthUser.setUser_level(oauthUserLevel);
        return oAuthUser;
    }

    public Set<SimpleGrantedAuthority> setAuthorities(OauthUserLevel oauthUserLevel){
        Set<SimpleGrantedAuthority> grantedAuthorities = new HashSet<>();
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(oauthUserLevel.toString());
        grantedAuthorities.add(simpleGrantedAuthority);
        return grantedAuthorities;
    }

    public void changeUserAuthByRemoteId(Long remoteId, OauthUserLevel newOauthUserLevel){
        if(oAuthRepository.findByUserRemoteId(remoteId).isPresent()){
            oAuthRepository.findByUserRemoteId(remoteId).get().setUser_level(newOauthUserLevel);

        }else{
            System.out.println("changeUserAuthByRomete Failed! We can't find User who have RemoteId : "+remoteId+"");
        }
    }
    public String changeUserAuthByJwt(Authentication authentication, String jwt, OauthUserLevel oauthUserLevel){
        String newToken = JwtTokenProvider.generateToken(authentication, oauthUserLevel);
        return newToken;
    }
}
