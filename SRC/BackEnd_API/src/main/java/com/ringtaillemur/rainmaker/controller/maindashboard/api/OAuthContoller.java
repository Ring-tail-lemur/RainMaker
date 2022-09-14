package com.ringtaillemur.rainmaker.controller.maindashboard.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ringtaillemur.rainmaker.config.JwtTokenProvider;
import com.ringtaillemur.rainmaker.config.UserAuthentication;
import com.ringtaillemur.rainmaker.domain.OAuthUser;
import com.ringtaillemur.rainmaker.domain.enumtype.OauthUserLevel;
import com.ringtaillemur.rainmaker.repository.OAuthRepository;
import com.ringtaillemur.rainmaker.service.oauth2.SecurityUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

import static com.ringtaillemur.rainmaker.config.WebClientConfig.*;

@Controller
public class OAuthContoller {

    @Autowired
    private SecurityUserService securityUserService;

    @GetMapping("/login/oauth2/code/github")
    @RolesAllowed("FIRST_AUTH_USER")
    public String testMap2(@RequestParam(value = "code", required = false, defaultValue = "test")String code,
                           @RequestParam(value = "state", required = false, defaultValue = "test")String state,  RedirectAttributes redirectAttributes
    , HttpServletResponse res) throws IOException, URISyntaxException {

        String userGithubToken = securityUserService.getUserGitHubOAuthToken(code);

        //http GET을 통하여 Github 고유 JWT 가져오는 메서드
        String userInfoLine = securityUserService.getUserInfoWithToken(userGithubToken);
        // userInfoLine -> OauthUser로 변환(OauthUserLevel == FIRST_AUTH_USER)
        OAuthUser nowLoginUser = securityUserService.stringToUserFirstAuthUserEntity(userInfoLine, userGithubToken.replace("\"",""));
        //Repository 에 들어가 있는 상태의 OauthUser Entity 리턴
        Optional<OAuthUser> nowUser = securityUserService.checkDuplicationAndCommitUser(nowLoginUser);

        if(nowUser.isPresent()){
            //Authentication 생성, JwtTokenProvider 생성하여 jwt Token 생성함, "sub" == remoteId, "ROLE" == UserLevel
            String token = securityUserService.setJwtTokenWithUserInfo(nowUser.get());
            //Bearer이라는 토큰 생성(jwt 토큰이 담김)
            Cookie cookie = new Cookie("Bearer", token);
            cookie.setPath("/");
            res.addCookie(cookie);
        }
        return "redirect:"+frontEndBaseUrl+"/RepositorySelect";
    }
}
