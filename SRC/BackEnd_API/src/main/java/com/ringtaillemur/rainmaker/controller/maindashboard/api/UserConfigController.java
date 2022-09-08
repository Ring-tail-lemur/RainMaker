package com.ringtaillemur.rainmaker.controller.maindashboard.api;

import com.ringtaillemur.rainmaker.dto.webdto.responsedto.UserRepositoryDto;
import com.ringtaillemur.rainmaker.service.UserConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class UserConfigController {


    private final HttpSession session;

    private final UserConfigService userConfigService;

    @ResponseBody
    @GetMapping("/RepositorySelect")
    public ArrayList<UserRepositoryDto> userRepositoryListReturnRestAPI() {

        // todo : UserId의 경우는 세션을 통해 알아올 것이고, token의 경우는 이 유저아이디를 통해 DB에서 빼올것.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication);

        String userId = (String) authentication.getPrincipal();
        String token = userConfigService.getToken(Long.valueOf(userId));
//        String token = "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";

        System.out.println(session.toString());
        return userConfigService.getUserRepositoryDtoByToken(token);
    }

    @PostMapping("/RepositorySelect")
    public String userRepositoryRegisterRestAPI(  //@RequestBody String repoIds) {
            @RequestParam(name="repo_id") List<String> repoIds) {

        for(var repoId : repoIds) {
            System.out.println(repoId);
        }

        // todo: repoId, token, repo Name, owner Name (Organization Name), 이걸로 azure function을 호출.
        // todo: Webhook 등록.
        String token = "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";
        String owner_name = "Ring-tail-lemur";
        String repo_name = "aa";

        userConfigService.setUserWebhookByRepoName(token, owner_name, repo_name);

        return "redirect:http://localhost:3000/";
    }
}

/*
todo 웹훅 등록
* curl -H "Authorization: Bearer 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" -i https://api.github.com/hub -F "hub.mode=subscribe" -F "hub.topic=https://github.com/Ring-tail-lemur/test-for-fake-project/events/push" -F "hub.callback=https://webhook.site/3d6e59ed-e609-434a-bf0c-52cd3c563062"
* curl -H "Authorization: Bearer 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" -i https://api.github.com/hub -F "hub.mode=subscribe" -F "hub.topic=https://github.com/Ring-tail-lemur/test-for-fake-project/events/pull_request" -F "hub.callback=https://webhook.site/3d6e59ed-e609-434a-bf0c-52cd3c563062"
*
* */
