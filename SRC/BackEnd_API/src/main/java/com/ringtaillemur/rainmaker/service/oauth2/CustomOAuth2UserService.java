package com.ringtaillemur.rainmaker.service.oauth2;

import com.ringtaillemur.rainmaker.domain.OAuthUser;
import com.ringtaillemur.rainmaker.repository.OAuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class CustomOAuth2UserService {
    @Autowired
    private OAuthRepository oAuthRepository;

    public void insertUser(OAuthUser oAuthUser){
        System.out.println(oAuthUser.getId());
        OAuthUser oAuthUser1 = oAuthRepository.saveAndFlush(oAuthUser);
    }
}
